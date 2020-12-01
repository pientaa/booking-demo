package com.example.bookingdemo.query.api

import com.example.bookingdemo.query.domain.room.Room
import com.example.bookingdemo.query.domain.room.RoomQueryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomQueryService
) {
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