package com.example.bookingdemo.controller

import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.service.BookingService
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
    fun create(@RequestBody booking: Booking): Booking = bookingService.save(booking)

    @GetMapping("/{bookingId}")
    fun getBooking(@PathVariable bookingId: String): Booking = bookingService.getById(bookingId)

    @PutMapping("/{bookingId}")
    fun updateBooking(@PathVariable bookingId: String, @RequestBody booking: Booking): Booking =
        bookingService.updateBooking(bookingId, booking)

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

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable bookingId: String) = bookingService.deleteById(bookingId)
}