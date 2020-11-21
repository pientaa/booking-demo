//package com.example.bookingdemo.common.configuration.migrations
//
//import com.example.bookingdemo.common.model.event.room.RoomCreated
//import com.github.mongobee.changeset.ChangeLog
//import com.github.mongobee.changeset.ChangeSet
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.data.mongodb.core.query.Criteria
//import org.springframework.data.mongodb.core.query.Query
//import org.springframework.data.mongodb.core.query.Update
//import java.time.LocalDateTime
//import java.time.ZoneOffset
//
//@ChangeLog(order = "001")
//class DataMigrations {
//    @ChangeSet(order = "001", author = "lukasz.pieta", id = "rooms created")
//    fun createRooms(mongoTemplate: MongoTemplate) {
//        val rooms = listOf(
//            Room(
//                id = "5eada3967f3a1726878aeacf",
//                number = "1A",
//                hasProjector = false,
//                hasWhiteboard = true
//            ),
//            Room(
//                id = "5eada3967f3a1726878aead0",
//                number = "1B",
//                hasProjector = true,
//                hasWhiteboard = false
//            ),
//            Room(
//                id = "5eada3967f3a1726878aead1",
//                number = "2A",
//                hasProjector = false,
//                hasWhiteboard = false
//            ),
//            Room(
//                id = "5eada3967f3a1726878aead2",
//                number = "2B",
//                hasProjector = true,
//                hasWhiteboard = true
//            )
//        )
//        rooms.asSequence().map { mongoTemplate.insert(it) }
//            .map { RoomCreated(it) }
//            .groupBy { it.aggregateId }
//            .map { (aggregateId, events) -> EventStream(aggregateId = aggregateId, events = events.toMutableList()) }
//            .map { mongoTemplate.insert(it) }.toList()
//    }
//
//    @ChangeSet(order = "002", author = "lukasz.pieta", id = "bookings created")
//    fun createBookings(mongoTemplate: MongoTemplate) {
//        val bookings = listOf(
//            Booking(
//                id = null,
//                roomId = "5eada3967f3a1726878aeacf",
//                start = LocalDateTime.of(2020, 5, 13, 8, 0).toInstant(ZoneOffset.UTC),
//                end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
//            ),
//            Booking(
//                id = null,
//                roomId = "5eada3967f3a1726878aead0",
//                start = LocalDateTime.of(2020, 5, 13, 9, 0).toInstant(ZoneOffset.UTC),
//                end = LocalDateTime.of(2020, 5, 13, 13, 0).toInstant(ZoneOffset.UTC)
//            ),
//            Booking(
//                id = null,
//                roomId = "5eada3967f3a1726878aead1",
//                start = LocalDateTime.of(2020, 5, 13, 9, 0).toInstant(ZoneOffset.UTC),
//                end = LocalDateTime.of(2020, 5, 13, 11, 0).toInstant(ZoneOffset.UTC)
//            ),
//            Booking(
//                id = null,
//                roomId = "5eada3967f3a1726878aead2",
//                start = LocalDateTime.of(2020, 5, 13, 10, 0).toInstant(ZoneOffset.UTC),
//                end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
//            )
//        )
//        val eventStreams = mongoTemplate.findAll(EventStream::class.java).sortedBy { it.aggregateId }
//
//        bookings.asSequence().map { mongoTemplate.insert(it) }
//            .map { BookingCreated(it) }
//            .groupBy { it.aggregateId }
//            .map { (aggregateId, events) ->
//                val updatedEvents = eventStreams.first { it.aggregateId == aggregateId }.addAll(events).events()
//                val query = Query()
//                query.addCriteria(Criteria.where("aggregateId").`is`(aggregateId))
//                query.fields().include("aggregateId")
//
//                val update = Update()
//                update.set("events", updatedEvents)
//                mongoTemplate.updateFirst(query, update, EventStream::class.java)
//            }
//    }
//}