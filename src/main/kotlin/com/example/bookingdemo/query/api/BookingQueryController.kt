package com.example.bookingdemo.query.api

import com.example.bookingdemo.query.domain.booking.Booking
import com.example.bookingdemo.query.domain.booking.BookingService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/bookings")
class BookingQueryController(
    val bookingService: BookingService
) {
    @GetMapping("/{bookingId}")
    fun getBooking(@PathVariable bookingId: String): Booking = bookingService.getById(bookingId)

    @GetMapping
    fun getAllByRoomIdAndBetween(
        @RequestParam roomId: String?,

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
}