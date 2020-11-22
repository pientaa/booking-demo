package com.example.bookingdemo.common.model.event

import com.example.bookingdemo.common.model.Booking
import java.time.Instant

sealed class RoomEvent : DomainEvent()

data class RoomCreated(
    var roomId: String,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) : RoomEvent()

data class BookingCreated(
    val bookingId: String,
    val roomId: String,
    val start: Instant,
    val end: Instant
) : RoomEvent() {
    constructor(booking: Booking) : this(
        bookingId = booking.id,
        roomId = booking.roomId,
        start = booking.start,
        end = booking.end
    )
}

data class BookingUpdated(
    val bookingId: String,
    val roomId: String,
    val start: Instant,
    val end: Instant
) : RoomEvent() {
    constructor(booking: Booking) : this(
        bookingId = booking.id,
        roomId = booking.roomId,
        start = booking.start,
        end = booking.end
    )
}

data class BookingCancelled(
    val bookingId: String
) : RoomEvent()