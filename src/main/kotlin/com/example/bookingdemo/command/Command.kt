package com.example.bookingdemo.command

import java.time.Instant

class CreateRoom(
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
)

class CreateBooking(
    val roomId: String,
    var start: Instant,
    var end: Instant
)

class UpdateBooking(
    val roomId: String,
    val bookingId: String,
    var start: Instant,
    var end: Instant
)

class CancelBooking(
    val roomId: String,
    val bookingId: String
)