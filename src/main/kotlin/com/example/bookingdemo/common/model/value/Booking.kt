package com.example.bookingdemo.common.model.value

import java.time.Instant

class Booking(
    val id: String,
    val roomId: String,
    var start: Instant,
    var end: Instant
)
