package com.example.bookingdemo.common.model

import com.example.bookingdemo.common.model.event.*
import com.example.bookingdemo.common.model.value.Booking
import org.springframework.data.annotation.Id
import java.util.*

sealed class Room(val number: String, var hasWhiteboard: Boolean? = null, var hasProjector: Boolean? = null) :
    AggregateRoot<RoomEvent>(aggregateId = number) {
    @Id
    var roomId: String = UUID.randomUUID().toString()
    var bookings = mutableMapOf<String, Booking>()
    override fun domainEvents() = this.domainEvents.sortedBy { it.occurredAt }
}

open class UnInitializedRoom(number: String, hasWhiteboard: Boolean? = null, hasProjector: Boolean? = null) :
    Room(number, hasWhiteboard, hasProjector) {
    override fun handle(event: RoomEvent): AggregateRoot<RoomEvent> {
        return when (event) {
            is RoomCreated -> CreatedRoom(event)
            else -> throw IllegalStateException()
        }
    }
}

open class CreatedRoom(
    number: String,
    hasWhiteboard: Boolean,
    hasProjector: Boolean
) : UnInitializedRoom(number, hasWhiteboard, hasProjector) {
    constructor(roomCreated: RoomCreated) : this(
        number = roomCreated.number,
        hasWhiteboard = roomCreated.hasWhiteboard,
        hasProjector = roomCreated.hasProjector
    )

    override fun handle(event: RoomEvent): Room {
        return when (event) {
            is BookingCreated -> RoomPartlyBooked(this, event)
            is RoomCreated -> this
            else -> throw IllegalAccessException()
        }
    }
}

class RoomPartlyBooked private constructor(
    number: String,
    hasWhiteboard: Boolean,
    hasProjector: Boolean
) : CreatedRoom(number, hasWhiteboard, hasProjector) {

    companion object {
        operator fun invoke(room: CreatedRoom, event: BookingCreated): RoomPartlyBooked {
            return RoomPartlyBooked(room.number, room.hasWhiteboard ?: false, room.hasProjector ?: false)
                .apply {
                    val booking =
                        Booking(id = event.bookingId, roomId = event.roomNumber, start = event.start, end = event.end)
                    bookings[booking.id] = booking
                }
        }
    }

    override fun handle(event: RoomEvent): Room {
        return when (event) {
            is BookingCancelled -> handle(event)
            is BookingUpdated -> handle(event)
            is BookingCreated -> handle(event)
            is RoomCreated -> this
        }
    }

    private fun handle(event: BookingCreated): RoomPartlyBooked {
        return this.apply {
            val booking = Booking(id = event.bookingId, roomId = event.roomNumber, start = event.start, end = event.end)
            bookings[booking.id] = booking
        }
    }

    private fun handle(event: BookingUpdated): RoomPartlyBooked {
        return this.apply {
            val booking = Booking(id = event.bookingId, roomId = event.roomNumber, start = event.start, end = event.end)
            bookings[booking.id] = booking
        }
    }

    private fun handle(event: BookingCancelled): RoomPartlyBooked {
        return this.apply {
            bookings.remove(event.bookingId)
        }
    }
}