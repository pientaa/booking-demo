package com.example.bookingdemo.service

import com.example.bookingdemo.infastructure.RoomConflictException
import com.example.bookingdemo.model.Booking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Spy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.event.annotation.AfterTestClass
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingServiceTest {
    @Autowired
    lateinit var roomService: RoomService

    @Autowired
    lateinit var bookingService: BookingService

    @AfterAll
    fun cleanUp() {
        bookingService.getAllBetween(null, null)
            .forEach { bookingService.deleteById(it.id!!) }
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
        bookingService.save(booking)

        // then
        assertThrows<RoomConflictException> { bookingService.save(booking) }
    }
}