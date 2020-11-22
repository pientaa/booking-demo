package com.example.bookingdemo.common.model

import com.example.bookingdemo.common.model.event.RoomEvent
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class EventStream(
    val aggregateId: String,
    @Id val id: String = UUID.randomUUID().toString(),
    val events: MutableList<RoomEvent> = mutableListOf()
) {
    fun addEvent(event: RoomEvent): EventStream {
        events.add(event)
        return this
    }
}