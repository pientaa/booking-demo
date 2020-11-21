package com.example.bookingdemo.common.model.event.room

import com.example.bookingdemo.common.model.event.DomainEvent
import com.example.bookingdemo.common.model.event.booking.Booking
import java.time.Instant

sealed class RoomEvent : DomainEvent()

data class BookingCreated private constructor(
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
    val start: Instant,
    val end: Instant
) : RoomEvent() {
    constructor(booking: Booking) : this(
        bookingId = booking.id,
        start = booking.start,
        end = booking.end
    )
}

data class BookingCancelled(
    val bookingId: String
) : RoomEvent()