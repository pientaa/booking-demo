package com.example.bookingdemo.command.domain.room.value

import java.time.Instant

class Booking(
    val id: String,
    val roomId: String,
    var start: Instant,
    var end: Instant
)
