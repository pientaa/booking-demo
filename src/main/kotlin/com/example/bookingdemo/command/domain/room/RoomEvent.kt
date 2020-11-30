package com.example.bookingdemo.command.domain.room

import com.example.bookingdemo.common.model.DomainEvent
import java.time.Instant

sealed class RoomEvent(number: String) : DomainEvent(aggregateId = number, aggregateType = "Room")

data class RoomCreated(
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) : RoomEvent(number)

data class BookingCreated(
    val bookingId: String,
    val roomNumber: String,
    val start: Instant,
    val end: Instant
) : RoomEvent(roomNumber)

data class BookingUpdated(
    val bookingId: String,
    val roomNumber: String,
    val start: Instant,
    val end: Instant
) : RoomEvent(roomNumber)

data class BookingCancelled(
    val bookingId: String,
    val roomNumber: String
) : RoomEvent(roomNumber)