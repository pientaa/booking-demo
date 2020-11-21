package com.example.bookingdemo.common.configuration.migrations

import com.example.bookingdemo.common.model.event.booking.Booking
import com.example.bookingdemo.common.model.event.room.Room
import com.github.mongobee.changeset.ChangeLog
import com.github.mongobee.changeset.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@ChangeLog(order = "001")
class DataMigrations {
    @ChangeSet(order = "001", author = "lukasz.pieta", id = "Rooms created and booked")
    fun createRooms(mongoTemplate: MongoTemplate) {
        val rooms = listOf(
            Room(
                roomId = "5eada3967f3a1726878aeacf",
                number = "1A",
                hasProjector = false,
                hasWhiteboard = true
            ),
            Room(
                roomId = "5eada3967f3a1726878aeac0",
                number = "1B",
                hasProjector = true,
                hasWhiteboard = false
            ),
            Room(
                roomId = "5eada3967f3a1726878aeac1",
                number = "2A",
                hasProjector = false,
                hasWhiteboard = false
            ),
            Room(
                roomId = "5eada3967f3a1726878aeac2",
                number = "2B",
                hasProjector = true,
                hasWhiteboard = true
            )

        )
        rooms.asSequence()
            .map { room ->
                room.addBooking(
                    Booking(
                        id = UUID.randomUUID().toString(),
                        roomId = room.roomId!!,
                        start = LocalDateTime.of(2020, 12, 15, 8, 0).toInstant(ZoneOffset.UTC),
                        end = LocalDateTime.of(2020, 12, 15, 12, 0).toInstant(ZoneOffset.UTC)
                    )
                )
                room
            }
            .map { mongoTemplate.insert(it) }.toList()
    }
}
