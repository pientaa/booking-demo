package com.example.bookingdemo.command.api

import com.example.bookingdemo.command.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rooms")
class CommandController(
    private val commandService: RoomBookingCommandService
) {

    @PostMapping
    fun createRoom(@RequestBody command: CreateRoom): String = commandService.createRoom(command)

    @PostMapping("/{roomId}/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBooking(@PathVariable roomId: String, @RequestBody booking: BookingDTO): BookingDTO =
        commandService.createBooking(CreateBooking(roomId, booking.start, booking.end))

    @PutMapping("/{roomId}/bookings/{bookingId}")
    fun updateBooking(
        @PathVariable roomId: String,
        @PathVariable bookingId: String,
        @RequestBody booking: BookingDTO
    ): BookingDTO =
        commandService.updateBooking(UpdateBooking(roomId, bookingId, booking.start, booking.end))

    @DeleteMapping("/{roomId}/bookings/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelBooking(@PathVariable roomId: String, @PathVariable bookingId: String) {
        commandService.cancelBooking(CancelBooking(roomId, bookingId))
    }
}