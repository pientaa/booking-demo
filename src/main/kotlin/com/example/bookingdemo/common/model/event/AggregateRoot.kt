package com.example.bookingdemo.common.model.event

abstract class AggregateRoot<EventType> {

    private var domainEvents = mutableListOf<EventType>()

    fun domainEvents(): List<EventType> = domainEvents

    protected fun registerEvent(event: EventType): EventType {
        domainEvents.add(event)
        return event
    }

    protected fun clearDomainEvents() {
        this.domainEvents.clear()
    }
}