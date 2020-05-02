package com.example.bookingdemo.controller

import com.example.bookingdemo.model.Room
import com.example.bookingdemo.service.RoomService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController()
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping
    fun saveRoom(@RequestBody room: Room): Room = roomService.save(room)

    @GetMapping
    fun getAll(): List<Room> = roomService.getAll()

    @GetMapping("/available")
    fun getAllAvailableRoomsBetween(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        start: LocalDateTime,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        end: LocalDateTime
    ) = roomService.getAllAvailableRoomsBetween(start.toInstant(ZoneOffset.UTC), end.toInstant(ZoneOffset.UTC))
        .let { if (it.isEmpty()) ResponseEntity<Unit>(HttpStatus.NO_CONTENT) else it }
}