package com.example.bookingdemo.common.model.event.room

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

class CreateRoom(
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
)

class CreateBooking(
    val roomId: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var start: Instant,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    var end: Instant
)