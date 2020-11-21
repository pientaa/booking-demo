package com.example.bookingdemo.common.model.event

abstract class AggregateRoot<EventType> {

    private var domainEvents = mutableListOf<EventType>()

    fun domainEvents(): List<EventType> = domainEvents

    protected fun registerEvent(event: EventType): EventType {
        domainEvents.add(event)
        return event
    }

    fun getAggregate() = domainEvents().fold(this) { acc, eventType ->
        acc.handle(eventType, false)
    }

    fun handle(event: EventType, isNew: Boolean = true) = handle(event)
        .also {
            if (isNew) {
                registerEvent(event)
            }
            it.domainEvents = domainEvents
        }

    protected abstract fun handle(event: EventType): AggregateRoot<EventType>

    fun addAll(events: List<EventType>) {
        domainEvents.addAll(events)
    }

    protected fun clearDomainEvents() {
        this.domainEvents.clear()
    }
}