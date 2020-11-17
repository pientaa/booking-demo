package com.example.bookingdemo.common.configuration.migrations

import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.common.model.Room
import com.github.mongobee.changeset.ChangeLog
import com.github.mongobee.changeset.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.LocalDateTime
import java.time.ZoneOffset

@ChangeLog(order = "001")
class DataMigrations {
    @ChangeSet(order = "001", author = "lukasz.pieta", id = "rooms created")
    fun createRooms(mongoTemplate: MongoTemplate) {
        listOf(
            Room(
                id = "5eada3967f3a1726878aeacf",
                number = "1A",
                hasProjector = false,
                hasWhiteboard = true
            ),
            Room(
                id = "5eada3967f3a1726878aead0",
                number = "1B",
                hasProjector = true,
                hasWhiteboard = false
            ),
            Room(
                id = "5eada3967f3a1726878aead1",
                number = "2A",
                hasProjector = false,
                hasWhiteboard = false
            ),
            Room(
                id = "5eada3967f3a1726878aead2",
                number = "2B",
                hasProjector = true,
                hasWhiteboard = true
            )
        )
            .forEach { mongoTemplate.insert(it) }
    }

    @ChangeSet(order = "002", author = "lukasz.pieta", id = "bookings created")
    fun createBookings(mongoTemplate: MongoTemplate) {
        listOf(
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aeacf",
                start = LocalDateTime.of(2020, 5, 13, 8, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aead0",
                start = LocalDateTime.of(2020, 5, 13, 9, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 5, 13, 13, 0).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aead1",
                start = LocalDateTime.of(2020, 5, 13, 9, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 5, 13, 11, 0).toInstant(ZoneOffset.UTC)
            ),
            Booking(
                id = null,
                roomId = "5eada3967f3a1726878aead2",
                start = LocalDateTime.of(2020, 5, 13, 10, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
            )
        )
            .forEach { mongoTemplate.insert(it) }
    }
}