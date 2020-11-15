package com.example.bookingdemo.command.api

import com.example.bookingdemo.query.domain.room.Room
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomCommandController {
    @PostMapping
    fun saveRoom(@RequestBody room: Room) {
//    roomService.save(room)
    }
}