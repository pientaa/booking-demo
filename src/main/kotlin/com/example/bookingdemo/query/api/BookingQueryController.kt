package com.example.bookingdemo.query.api

import com.example.bookingdemo.query.domain.booking.Booking
import com.example.bookingdemo.query.domain.booking.BookingQueryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/bookings")
class BookingController(
    val bookingService: BookingQueryService
) {
    @GetMapping("/{bookingId}")
    fun getBooking(@PathVariable bookingId: String): Booking = bookingService.getById(bookingId)

    @GetMapping
    fun getAllByRoomIdAndBetween(
        @RequestParam roomNumber: String?,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        fromDate: LocalDateTime?,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        toDate: LocalDateTime?
    ): List<Booking> =
        bookingService.getAllByRoomNumberAndBetween(
            roomNumber,
            fromDate?.toInstant(ZoneOffset.UTC),
            toDate?.toInstant(ZoneOffset.UTC)
        )
}