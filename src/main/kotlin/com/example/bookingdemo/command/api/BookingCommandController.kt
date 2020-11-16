package com.example.bookingdemo.command.api

import com.example.bookingdemo.command.domain.BookingCommandService
import com.example.bookingdemo.common.model.Booking
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingCommandController(
    private val bookingCommandService: BookingCommandService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBooking(@RequestBody booking: Booking): Booking =
        bookingCommandService.create(booking)

    @PutMapping("/{bookingId}")
    fun updateBooking(@PathVariable bookingId: String, @RequestBody booking: Booking): Booking =
        bookingCommandService.update(booking.copy(id = bookingId))

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable bookingId: String) =
        bookingCommandService.deleteById(bookingId)
}