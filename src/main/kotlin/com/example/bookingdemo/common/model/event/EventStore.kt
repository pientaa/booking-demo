package com.example.bookingdemo.common.model.event

import org.springframework.data.mongodb.repository.MongoRepository

interface EventStore : MongoRepository<EventStream, String> {
    fun findByAggregateId(aggregateId: String): EventStream?
}