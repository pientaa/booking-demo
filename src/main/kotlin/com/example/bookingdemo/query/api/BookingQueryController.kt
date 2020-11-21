package com.example.bookingdemo.query.api

import com.example.bookingdemo.query.domain.booking.BookingQueryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/bookings")
class BookingQueryController(
    val bookingQueryService: BookingQueryService
) {
    @GetMapping("/{bookingId}")
    fun getBooking(@PathVariable bookingId: String) {
        //TODO
    }

    @GetMapping
    fun getAllByRoomIdAndBetween(
        @RequestParam roomId: String?,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        fromDate: LocalDateTime?,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        toDate: LocalDateTime?
    ) {
        //TODO
    }
}