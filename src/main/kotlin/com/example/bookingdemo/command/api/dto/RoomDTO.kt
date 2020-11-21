package com.example.bookingdemo.command.api.dto

import com.example.bookingdemo.common.model.event.room.Room

class RoomDTO(
    var roomId: String? = null,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) {
    constructor(room: Room) : this(
        roomId = room.roomId,
        number = room.number,
        hasWhiteboard = room.hasWhiteboard,
        hasProjector = room.hasProjector
    )
}