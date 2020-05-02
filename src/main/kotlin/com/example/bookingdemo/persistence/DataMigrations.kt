package com.example.bookingdemo.persistence

import com.example.bookingdemo.model.Room
import com.github.mongobee.changeset.ChangeLog
import com.github.mongobee.changeset.ChangeSet
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeLog(order = "001")
class DataMigrations {
    @ChangeSet(order = "001", author = "lukasz.pieta", id = "rooms created")
    fun createRooms(mongoTemplate: MongoTemplate) {
        val room1A = Room(
            number = "1A",
            hasProjector = false,
            hasWhiteboard = true
        )
        val room1B = Room(
            number = "1B",
            hasProjector = true,
            hasWhiteboard = false
        )
        val room2A = Room(
            number = "2A",
            hasProjector = false,
            hasWhiteboard = false
        )
        val room2B = Room(
            number = "2B",
            hasProjector = true,
            hasWhiteboard = true
        )
        mongoTemplate.insert(room1A)
        mongoTemplate.insert(room1B)
        mongoTemplate.insert(room2A)
        mongoTemplate.insert(room2B)
    }
}