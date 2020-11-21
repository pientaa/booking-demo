package com.example.bookingdemo.query.api

import com.example.bookingdemo.query.domain.room.RoomQueryService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/rooms")
class RoomQueryController(
    private val roomQueryService: RoomQueryService
) {
    @GetMapping
    fun getAll() {
        //TODO
    }

    @GetMapping("/available")
    fun getAllAvailableRoomsBetween(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        start: LocalDateTime,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        end: LocalDateTime
    ) {
        //TODO
    }
}