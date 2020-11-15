package com.example.bookingdemo.domain.booking

import com.example.bookingdemo.infastructure.BookingNotFoundException
import com.example.bookingdemo.infastructure.RoomConflictException
import com.example.bookingdemo.infastructure.RoomNotFoundException
import com.example.bookingdemo.domain.room.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository
) {
    fun save(booking: Booking): Booking = roomRepository.findByIdOrNull(booking.roomId)?.run {
        if (getAllByRoomIdAndBetween(booking.roomId, booking.start, booking.end).none { it.id != booking.id })
            bookingRepository.save(booking)
        else throw RoomConflictException(booking.roomId, booking.start, booking.end)
    } ?: throw RoomNotFoundException(booking.roomId)

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

    fun updateBooking(bookingId: String, booking: Booking): Booking =
        getById(bookingId).run { save(booking.copy(id = bookingId)) }
}