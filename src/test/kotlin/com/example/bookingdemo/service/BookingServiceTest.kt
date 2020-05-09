package com.example.bookingdemo.service

import com.example.bookingdemo.infastructure.RoomConflictException
import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.persistence.DataMigrations
import com.example.bookingdemo.repository.BookingRepository
import com.example.bookingdemo.repository.RoomRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.ZoneOffset

@DataMongoTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingServiceTest {

    @Autowired
    lateinit var roomRepository: RoomRepository

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    lateinit var roomService: RoomService
    lateinit var bookingService: BookingService


    @BeforeAll
    fun setup() {
        roomService = RoomService(roomRepository, bookingRepository)
        bookingService = BookingService(bookingRepository, roomRepository)
        DataMigrations().createRooms(mongoTemplate)
        DataMigrations().createBookings(mongoTemplate)
    }

    @Test
    fun `create booking, happy path`() {
        // given
        val booking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead2",
            start = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 14, 0).toInstant(ZoneOffset.UTC)
        )

        // when
        val createdBooking = bookingService.save(booking)

        // then
        assertEquals(booking.roomId, createdBooking.roomId)
        assertEquals(booking.start, createdBooking.start)
        assertEquals(booking.end, createdBooking.end)
    }

    @Test
    fun `create booking, conflict`() {
        // given
        val booking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead2",
            start = LocalDateTime.of(2020, 5, 13, 10, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
        )

        // then
        assertThrows<RoomConflictException> { bookingService.save(booking)  }
    }
}