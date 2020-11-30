package com.example.bookingdemo.common.model

import org.springframework.data.mongodb.repository.MongoRepository

interface EventStore : MongoRepository<EventStream, String> {
    fun findByAggregateId(aggregateId: String): EventStream?

    @JvmDefault
    fun saveEvent(event: DomainEvent): EventStream {
        return save((findByAggregateId(event.aggregateId) ?: EventStream(event.aggregateId)).addEvent(event))
    }
}