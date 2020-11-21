package com.example.bookingdemo.command.api

import com.example.bookingdemo.common.model.event.room.CreateBooking
import com.example.bookingdemo.common.model.event.room.RoomBookingService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingCommandController(
    private val roomService: RoomBookingService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBooking(@RequestBody command: CreateBooking): String =
        roomService.createBooking(command)
}