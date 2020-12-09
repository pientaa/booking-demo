package com.example.bookingdemo.command.api

import com.example.bookingdemo.command.api.dto.BookingDTO
import com.example.bookingdemo.command.api.dto.RoomDTO
import com.example.bookingdemo.command.domain.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rooms")
class CommandController(
    private val commandService: CommandService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addRoom(@RequestBody roomDTO: RoomDTO) =
        commandService.addRoom(AddRoom(roomDTO.number, roomDTO.hasWhiteboard, roomDTO.hasWhiteboard))

    @PostMapping("/{roomNumber}/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBooking(@PathVariable roomNumber: String, @RequestBody booking: BookingDTO) =
        commandService.createBooking(CreateBooking(roomNumber, booking.start, booking.end))

    @PutMapping("/{roomNumber}/bookings/{bookingId}")
    fun rescheduleBooking(
        @PathVariable roomNumber: String,
        @PathVariable bookingId: String,
        @RequestBody booking: BookingDTO
    ) = commandService.rescheduleBooking(RescheduleBooking(roomNumber, bookingId, booking.start, booking.end))

    @DeleteMapping("/{roomNumber}/bookings/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelBooking(@PathVariable roomNumber: String, @PathVariable bookingId: String) {
        commandService.cancelBooking(CancelBooking(roomNumber, bookingId))
    }
}