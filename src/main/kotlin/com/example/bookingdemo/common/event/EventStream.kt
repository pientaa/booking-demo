package com.example.bookingdemo.common.event

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class EventStream(
    val aggregateId: String,
    @Id val id: String = UUID.randomUUID().toString(),
    val events: MutableList<DomainEvent> = mutableListOf()
) {
    fun addEvent(event: DomainEvent): EventStream {
        events.add(event)
        return this
    }
}