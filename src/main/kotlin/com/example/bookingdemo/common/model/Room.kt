package com.example.bookingdemo.common.model

import com.example.bookingdemo.command.CreateRoom
import com.example.bookingdemo.common.model.event.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
open class Room(
    @Id
    var roomId: String? = null,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) : AggregateRoot<RoomEvent>() {
    constructor(command: CreateRoom) : this(
        number = command.number,
        hasWhiteboard = command.hasWhiteboard,
        hasProjector = command.hasProjector
    )

    var bookings = mutableMapOf<String, Booking>()

    override fun handle(event: RoomEvent): Room {
        return when (event) {
            is BookingCancelled -> throw IllegalStateException()
            is BookingUpdated -> throw  IllegalStateException()
            is BookingCreated -> RoomPartlyBooked(this, event)
        }
    }
}

class RoomPartlyBooked private constructor(
    roomId: String? = null,
    number: String,
    hasWhiteboard: Boolean,
    hasProjector: Boolean
) : Room(roomId, number, hasWhiteboard, hasProjector) {

    companion object {
        operator fun invoke(room: Room, event: BookingCreated): RoomPartlyBooked {
            return RoomPartlyBooked(room.roomId, room.number, room.hasWhiteboard, room.hasProjector)
                .apply {
                    val booking =
                        Booking(id = event.bookingId, roomId = event.roomId, start = event.start, end = event.end)
                    bookings[booking.id] = booking
                }
        }
    }

    override fun handle(event: RoomEvent): Room {
        return when (event) {
            is BookingCancelled -> handle(event)
            is BookingUpdated -> handle(event)
            is BookingCreated -> handle(event)
        }
    }

    private fun handle(event: BookingCreated): RoomPartlyBooked {
        return this.apply {
            val booking = Booking(id = event.bookingId, roomId = event.roomId, start = event.start, end = event.end)
            bookings[booking.id] = booking
        }
    }

    private fun handle(event: BookingUpdated): RoomPartlyBooked {
        return this.apply {
            val booking = Booking(id = event.bookingId, roomId = event.roomId, start = event.start, end = event.end)
            bookings[booking.id] = booking
        }
    }

    private fun handle(event: BookingCancelled): RoomPartlyBooked {
        return this.apply {
            bookings.remove(event.bookingId)
        }
    }
}