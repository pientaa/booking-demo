package com.example.bookingdemo.common.model.event.room

import com.example.bookingdemo.common.model.Room
import com.example.bookingdemo.common.model.event.DomainEvent

data class RoomCreated(
    val roomId: String,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) : DomainEvent(eventType = "RoomCreated", aggregateId = roomId) {
    constructor(room: Room) : this(
        roomId = room.id!!, //TODO: Throw exception
        number = room.number,
        hasWhiteboard = room.hasWhiteboard,
        hasProjector = room.hasProjector
    )
}