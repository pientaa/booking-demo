package com.example.bookingdemo.service

import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.repository.BookingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Service
class BookingService(
    val bookingRepository: BookingRepository
) {
    fun add(booking: Booking): Booking =
        if (getAllByRoomIdAndBetween(booking.roomId, booking.start, booking.end).isEmpty())
            bookingRepository.save(booking)
        else throw  ResponseStatusException(
            HttpStatus.CONFLICT,
            "Room with id: ${booking.roomId} is already booked in period: ${booking.start} - ${booking.end}"
        )

    fun deleteById(bookingId: String) = bookingRepository.deleteById(bookingId)

    fun getAll(): List<Booking> = bookingRepository.findAll()

    fun getById(bookingId: String): Booking = bookingRepository.findByIdOrNull(bookingId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Booking with id: $bookingId not found")

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
}