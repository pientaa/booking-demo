package com.example.bookingdemo.controller

import com.example.bookingdemo.model.Room
import com.example.bookingdemo.service.RoomService
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping
    fun saveRoom(@RequestBody room: Room): Room = roomService.save(room)

    @GetMapping
    fun getAll(): List<Room> = roomService.getAll()
}