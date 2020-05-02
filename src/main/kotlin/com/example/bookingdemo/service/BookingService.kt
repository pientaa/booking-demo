package com.example.bookingdemo.service

import com.example.bookingdemo.infastructure.BookingNotFoundException
import com.example.bookingdemo.infastructure.RoomConflictException
import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.repository.BookingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val roomService: RoomService
) {
    fun add(booking: Booking): Booking? = roomService.getById(booking.roomId).run {
        if (getAllByRoomIdAndBetween(booking.roomId, booking.start, booking.end).isEmpty())
            bookingRepository.save(booking)
        else throw RoomConflictException(booking.roomId, booking.start, booking.end)
    }

    fun getAll(): List<Booking> = bookingRepository.findAll()

    fun getById(bookingId: UUID): Booking =
        bookingRepository.findByIdOrNull(bookingId) ?: throw BookingNotFoundException(bookingId)

    fun getAllByRoomIdAndBetween(roomId: UUID?, fromDate: Instant?, toDate: Instant?): List<Booking> =
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

    fun deleteById(bookingId: UUID) = bookingRepository.deleteById(bookingId)
}