package com.example.bookingdemo.command.api

import com.example.bookingdemo.command.CreateBooking
import com.example.bookingdemo.command.CreateRoom
import com.example.bookingdemo.command.RoomBookingCommandService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CommandController(
    private val commandService: RoomBookingCommandService
) {

    @PostMapping("/rooms")
    fun createRoom(@RequestBody command: CreateRoom): String = commandService.createRoom(command)

    @PostMapping("/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBooking(@RequestBody command: CreateBooking): String =
        commandService.createBooking(command)
}