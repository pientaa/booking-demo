package com.example.bookingdemo.common.model.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class EventService(
    private val store: EventStore
) {

    @EventListener(DomainEvent::class)
    fun handle(event: DomainEvent) {
        val eventStream = store.findByAggregateId(event.aggregateId) ?: EventStream(event.aggregateId)

        eventStream.add(event)

        store.save(eventStream)
    }
}