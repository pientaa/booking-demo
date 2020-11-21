package com.example.bookingdemo.common.model.event

import org.springframework.data.domain.AfterDomainEventPublication
import org.springframework.data.domain.DomainEvents

abstract class AggregateRoot<EventType> {

    private var domainEvents = mutableListOf<EventType>()

    @DomainEvents
    fun domainEvents(): List<EventType> = domainEvents

    protected fun registerEvent(event: EventType): EventType {
        domainEvents.add(event)
        return event
    }

    @AfterDomainEventPublication
    protected fun clearDomainEvents() {
        this.domainEvents.clear()
    }
}