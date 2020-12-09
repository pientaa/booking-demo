package com.example.bookingdemo.command.domain.room

import com.example.bookingdemo.command.domain.room.value.Booking
import com.example.bookingdemo.common.event.AggregateRoot
import com.example.bookingdemo.common.event.DomainEvent

sealed class Room(val number: String) : AggregateRoot<DomainEvent>(aggregateId = number) {
    var bookings = mutableMapOf<String, Booking>()
    var hasWhiteboard: Boolean = false
    var hasProjector: Boolean = false

    override fun domainEvents() = this.domainEvents.sortedBy { it.occurredAt }
}

open class UnInitializedRoom(number: String) : Room(number) {
    override fun handle(event: DomainEvent): AggregateRoot<DomainEvent> {
        return when (event) {
            is RoomCreated -> CreatedRoom(event)
            else -> throw IllegalStateException()
        }
    }
}

open class CreatedRoom constructor(number: String) : UnInitializedRoom(number) {
    companion object {
        operator fun invoke(roomCreated: RoomCreated): CreatedRoom =
            CreatedRoom(roomCreated.number).apply {
                hasProjector = roomCreated.hasProjector
                hasWhiteboard = roomCreated.hasWhiteboard
            }
        operator fun invoke(number: String, hasWhiteboard: Boolean, hasProjector: Boolean): CreatedRoom =
            CreatedRoom(number).apply {
                this.hasProjector = hasProjector
                this.hasWhiteboard = hasWhiteboard
            }
    }

    override fun handle(event: DomainEvent): Room {
        return when (event) {
            is BookingCreated -> RoomPartlyBooked(this, event)
            is RoomCreated -> this
            else -> throw IllegalAccessException()
        }
    }
}

class RoomPartlyBooked private constructor(number: String) : CreatedRoom(number) {

    companion object {
        operator fun invoke(room: CreatedRoom, event: BookingCreated): RoomPartlyBooked {
            return RoomPartlyBooked(room.number)
                .apply {
                    val booking =
                        Booking(
                            id = event.bookingId,
                            roomId = event.roomNumber,
                            start = event.start,
                            end = event.end
                        )
                    bookings[booking.id] = booking
                }
        }
    }

    override fun handle(event: DomainEvent): Room {
        return when (event) {
            is BookingCancelled -> handle(event)
            is BookingRescheduled -> handle(event)
            is BookingCreated -> handle(event)
            is RoomCreated -> this
            else -> throw IllegalStateException()
        }
    }

    private fun handle(event: BookingCreated): RoomPartlyBooked {
        return this.apply {
            val booking =
                Booking(id = event.bookingId, roomId = event.roomNumber, start = event.start, end = event.end)
            bookings[booking.id] = booking
        }
    }

    private fun handle(event: BookingRescheduled): RoomPartlyBooked {
        return this.apply {
            val booking =
                Booking(id = event.bookingId, roomId = event.roomNumber, start = event.start, end = event.end)
            bookings[booking.id] = booking
        }
    }

    private fun handle(event: BookingCancelled): RoomPartlyBooked {
        return this.apply {
            bookings.remove(event.bookingId)
        }
    }
}