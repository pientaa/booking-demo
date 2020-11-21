package com.example.bookingdemo.command.api

import com.example.bookingdemo.common.model.event.room.CreateRoom
import com.example.bookingdemo.common.model.event.room.RoomBookingService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomCommandController(
    private val roomService: RoomBookingService
) {
    @PostMapping
    fun createRoom(@RequestBody command: CreateRoom): String = roomService.createRoom(command)
}