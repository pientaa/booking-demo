package com.example.bookingdemo.query.domain.booking

import com.example.bookingdemo.common.infastructure.BookingNotFoundException
import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.common.infastructure.RoomNotFoundException
import com.example.bookingdemo.common.model.event.BookingCreated
import com.example.bookingdemo.query.domain.room.RoomRepository
import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository
) {
    @EventListener(BookingCreated::class)
    fun save(event: BookingCreated): Booking = roomRepository.findByIdOrNull(event.roomId)?.run {
        if (getAllByRoomIdAndBetween(event.roomId, event.start, event.end).none { it.id != event.id.toString() })
            bookingRepository.save(Booking(event))
        else throw RoomConflictException(event.roomId, event.start, event.end)
    } ?: throw RoomNotFoundException(event.roomId)

    fun getById(bookingId: String): Booking = bookingRepository.findByIdOrNull(bookingId)
            ?: throw BookingNotFoundException(bookingId)

    fun getAllByRoomIdAndBetween(roomId: String?, fromDate: Instant?, toDate: Instant?): List<Booking> =
        when {
            roomId == null -> getAllBetween(fromDate, toDate)
            fromDate == null && toDate == null -> bookingRepository.findAllByRoomId(roomId)
            fromDate == null -> bookingRepository.findAllByRoomIdAndStartBefore(roomId, toDate!!)
            toDate == null -> bookingRepository.findAllByRoomIdAndEndAfter(roomId, fromDate)
            else -> bookingRepository.let {
                it.findAllByRoomIdAndStartBetween(roomId, fromDate, toDate) +
                        it.findAllByRoomIdAndStartLessThanEqualAndEndGreaterThan(roomId, fromDate, fromDate)
            }.distinctBy { it.id }
        }
            .sortedBy { it.start }

    fun getAllBetween(fromDate: Instant?, toDate: Instant?): List<Booking> =
        when {
            fromDate == null && toDate == null -> bookingRepository.findAll()
            fromDate == null -> bookingRepository.findAllByStartBefore(toDate!!)
            toDate == null -> bookingRepository.findAllByEndAfter(fromDate)
            else -> bookingRepository.let {
                it.findAllByStartBetween(fromDate, toDate) +
                        it.findAllByStartLessThanEqualAndEndGreaterThan(fromDate, fromDate)
            }.distinctBy { it.id }
        }

    fun deleteById(bookingId: String) = bookingRepository.deleteById(bookingId)

//    fun updateBooking(bookingId: String, booking: Booking): Booking =
//        getById(bookingId).run { save(booking.copy(id = bookingId)) }
}