package com.example.bookingdemo.controller

import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.service.BookingService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.awt.print.Book
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@RestController
@RequestMapping("/bookings")
class BookingController(
    val bookingService: BookingService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody booking: Booking): Booking {
        return bookingService.add(booking) ?: throw  ResponseStatusException(
            HttpStatus.CONFLICT,
            "Room with id: ${booking.roomId} is already booked in period: ${booking.start} - ${booking.end}"
        )
    }

    @GetMapping("/{bookingId}")
    fun getBooking(@PathVariable bookingId: UUID): Booking = bookingService.getById(bookingId)

    //TODO
//    @PutMapping("/{bookingId}")
//    fun updateBooking(@PathVariable bookingId: UUID, @RequestBody booking: Booking): Booking =
//        bookingService.updateBooking(booking.copy(id = bookingId))

    @GetMapping
    fun getAllByRoomIdAndBetween(
        @RequestParam roomId: UUID?,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        fromDate: LocalDateTime?,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        toDate: LocalDateTime?
    ): List<Booking> =
        bookingService.getAllByRoomIdAndBetween(
            roomId,
            fromDate?.toInstant(ZoneOffset.UTC),
            toDate?.toInstant(ZoneOffset.UTC)
        )

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable bookingId: UUID) = bookingService.deleteById(bookingId)
}