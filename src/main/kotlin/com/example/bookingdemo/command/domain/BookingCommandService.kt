package com.example.bookingdemo.command.domain

import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.common.model.event.DomainEventPublisher
import com.example.bookingdemo.common.model.event.EventStore
import com.example.bookingdemo.common.repository.BookingRepository
import com.example.bookingdemo.common.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
class BookingCommandService(
    private val domainEventPublisher: DomainEventPublisher,
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository,
    private val eventStore: EventStore
) {

    fun save(booking: Booking): Booking {
        return booking
//        val roomAggregate = eventStore.findByAggregateIdAndType(booking.roomId, "Room")
//        return roomRepository.findByIdOrNull(booking.roomId)?.run {
////        TODO: Check if room is available and how to do this???
//            bookingRepository.save(booking)
//        } ?: throw RoomNotFoundException(booking.roomId)
    }

    fun deleteById(bookingId: String) {
        bookingRepository.deleteById(bookingId)
    }

//        fun updateBooking(bookingId: String, booking: Booking): Booking =
//        getById(bookingId).run { save(booking.copy(id = bookingId)) }

}