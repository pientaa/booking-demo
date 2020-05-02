package com.example.bookingdemo.persistence

import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.model.Room
import com.github.mongobee.changeset.ChangeLog
import com.github.mongobee.changeset.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate
import java.time.*

@ChangeLog(order = "001")
class DataMigrations {
    @ChangeSet(order = "001", author = "lukasz.pieta", id = "rooms created")
    fun createRooms(mongoTemplate: MongoTemplate) {
        val room1A = Room(
            id = "5eada3967f3a1726878aeacf",
            number = "1A",
            hasProjector = false,
            hasWhiteboard = true
        )
        val room1B = Room(
            id = "5eada3967f3a1726878aead0",
            number = "1B",
            hasProjector = true,
            hasWhiteboard = false
        )
        val room2A = Room(
            id = "5eada3967f3a1726878aead1",
            number = "2A",
            hasProjector = false,
            hasWhiteboard = false
        )
        val room2B = Room(
            id = "5eada3967f3a1726878aead2",
            number = "2B",
            hasProjector = true,
            hasWhiteboard = true
        )

        mongoTemplate.insert(room1A)
        mongoTemplate.insert(room1B)
        mongoTemplate.insert(room2A)
        mongoTemplate.insert(room2B)
    }

    @ChangeSet(order = "002", author = "lukasz.pieta", id = "bookings created")
    fun createBookings(mongoTemplate: MongoTemplate) {
        val firstBooking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aeacf",
            start = LocalDateTime.of(2020, 5, 13, 8, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
        )
        val secondBooking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead0",
            start = LocalDateTime.of(2020, 5, 13, 9, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 13, 0).toInstant(ZoneOffset.UTC)
        )
        val thirdBooking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead1",
            start = LocalDateTime.of(2020, 5, 13, 9, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 11, 0).toInstant(ZoneOffset.UTC)
        )
        val fourthBooking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead2",
            start = LocalDateTime.of(2020, 5, 13, 10, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
        )

        mongoTemplate.insert(firstBooking)
        mongoTemplate.insert(secondBooking)
        mongoTemplate.insert(thirdBooking)
        mongoTemplate.insert(fourthBooking)
    }
}