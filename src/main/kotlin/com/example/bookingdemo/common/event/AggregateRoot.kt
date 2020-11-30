package com.example.bookingdemo.common.event

abstract class AggregateRoot<EventType>(var aggregateId: String) {

    var domainEvents = mutableListOf<EventType>()

    protected abstract fun domainEvents(): List<EventType>

    private fun registerEvent(event: EventType): EventType {
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

    fun addAll(events: List<EventType>): AggregateRoot<EventType> {
        domainEvents.addAll(events)
        return this
    }

    protected fun clearDomainEvents() {
        this.domainEvents.clear()
    }
}