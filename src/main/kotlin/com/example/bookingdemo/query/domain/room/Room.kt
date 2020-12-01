package com.example.bookingdemo.query.domain.room

import com.example.bookingdemo.command.domain.room.RoomCreated
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Room(
    @Id
    val id: String?,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) {
    constructor(event: RoomCreated) : this(
        id = null,
        number = event.number,
        hasWhiteboard = event.hasWhiteboard,
        hasProjector = event.hasProjector
    )
}