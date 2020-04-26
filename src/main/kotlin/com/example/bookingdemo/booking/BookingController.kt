package com.example.bookingdemo.booking

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/bookings")
class BookingController(
    val bookingService: BookingService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody booking: Booking): Booking {
        return bookingService.add(booking)
    }

    @GetMapping("/{bookingId}")
    fun getBooking(@PathVariable(name = "bookingId") bookingId: String): Booking = bookingService.getById(bookingId)

    @GetMapping
    fun getAllByRoomIdAndBetween(
        @RequestParam(name = "roomId", required = false) roomId: String?,

        @RequestParam(name = "fromDate", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        fromDate: LocalDateTime?,

        @RequestParam(name = "toDate", required = false)
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
    fun delete(@PathVariable(name = "bookingId") bookingId: String) = bookingService.deleteById(bookingId)
}