package com.example.bookingdemo.common.model.event

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class EventStream(
    val aggregateId: String,

    @Id
    private val id: String = UUID.randomUUID().toString(),

    private val events: MutableList<DomainEvent> = mutableListOf()
) {
    fun add(event: DomainEvent): EventStream {
        this.events.add(event)
        return this
    }

    fun events(): List<DomainEvent> = events.sortedBy { it.createdOn }
}
