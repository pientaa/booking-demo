package com.example.bookingdemo.command.api

import com.example.bookingdemo.command.domain.RoomCommandService
import com.example.bookingdemo.common.model.Room
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomCommandController(
    private val roomCommandService: RoomCommandService
) {
    @PostMapping
    fun saveRoom(@RequestBody room: Room): Room = roomCommandService.save(room)
}