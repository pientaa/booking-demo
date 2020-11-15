package com.example.bookingdemo.domain.booking

import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.infrastructure.AbstractBookingTest
import com.example.bookingdemo.query.domain.booking.Booking
import com.example.bookingdemo.query.domain.booking.BookingService
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneOffset

@SpringBootTest
final class BookingServiceTest(
    private val bookingService: BookingService
) : AbstractBookingTest(bookingService) {
    init {
        Given("new booking") {
            val booking = Booking(
                id = null,
                roomId = "5eada3967f3a1726878aead2",
                start = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC),
                end = LocalDateTime.of(2020, 5, 13, 14, 0).toInstant(ZoneOffset.UTC)
            )

            When("save it") {
                val createdBooking = bookingService.save(booking)

                Then("should be saved") {
                    createdBooking shouldBe booking.copy(id = createdBooking.id)
                }
            }
            And("try to save it again") {
                val result = try {
                    bookingService.save(booking)
                } catch (e: Exception) {
                    e
                }

                Then("Exception should be thrown") {
                    result::class shouldBe RoomConflictException::class
                }
            }
        }
    }
}