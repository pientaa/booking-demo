package com.example.bookingdemo.command.domain

import com.example.bookingdemo.common.infastructure.BookingNotFoundException
import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.common.infastructure.RoomNotFoundException
import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.common.model.event.DomainEventPublisher
import com.example.bookingdemo.common.model.event.EventStore
import com.example.bookingdemo.common.model.event.booking.BookingCancelled
import com.example.bookingdemo.common.model.event.booking.BookingCreated
import com.example.bookingdemo.common.repository.BookingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookingCommandService(
    private val domainEventPublisher: DomainEventPublisher,
    private val bookingRepository: BookingRepository,
    private val eventStore: EventStore
) {

    fun save(booking: Booking): Booking {
        eventStore.findByAggregateId(booking.roomId) ?: throw RoomNotFoundException(booking.roomId)

        val overlappingBookings = bookingRepository.findAllByRoomIdAndEndGreaterThanAndStartLessThanEqual(
            roomId = booking.roomId,
            fromDate = booking.start,
            toDate = booking.end
        )

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(booking.roomId, booking.start, booking.end)

        return bookingRepository.save(booking)
            .also { domainEventPublisher.publishEvent(BookingCreated(it)) }
    }

    fun deleteById(bookingId: String) {
        val booking = bookingRepository.findByIdOrNull(bookingId) ?: throw  BookingNotFoundException(bookingId)

        bookingRepository.deleteById(bookingId)
            .also {
                domainEventPublisher.publishEvent(
                    BookingCancelled(bookingId = bookingId, roomId = booking.roomId)
                )
            }
    }
}