package com.example.bookingdemo.controller

import com.example.bookingdemo.infrastructure.AbstractBookingTest
import com.example.bookingdemo.infrastructure.Mapper
import com.example.bookingdemo.model.Booking
import com.example.bookingdemo.service.BookingService
import com.example.bookingdemo.stubs.BookingStub
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotlintest.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@SpringBootTest
final class BookingControllerIntegrationTest(
    applicationContext: WebApplicationContext,
    bookingService: BookingService
) : AbstractBookingTest(bookingService) {

    lateinit var mvc: MockMvc

    init {
        beforeSpec {
            mvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build()
        }

        Given("parameters as time period from 8 a.m. to 1 p.m.") {
            val url = "http://localhost/bookings"
            val fromDateTime = LocalDateTime.of(2020, 8, 29, 8, 0)
            val toDateTime = LocalDateTime.of(2020, 8, 29, 13, 0)

            When("get all bookings") {
                val result = mvc.perform(
                    MockMvcRequestBuilders.get(url)
                        .param("fromDate", fromDateTime.toString())
                        .param("toDate", toDateTime.toString())
                )
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andReturn()

                Then("should return one booking at 12 a.m.") {

                    val actual: List<Booking> = Mapper.readValue(result.response.contentAsString)

                    actual.size shouldBe 1
                    actual[0] shouldBe BookingStub.wednesdayNoonBooking.copy(id = actual[0].id)
                }
            }
        }
    }
}
