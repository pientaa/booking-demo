package com.example.bookingdemo.common.model

import com.example.bookingdemo.common.model.event.RoomEvent
import org.springframework.data.mongodb.repository.MongoRepository

interface EventStore : MongoRepository<EventStream, String> {
    fun findByAggregateId(aggregateId: String): EventStream?

    //TODO: Put this in some service layer
    @JvmDefault
    fun getRoom(aggregateId: String): Room {
        val events = findByAggregateId(aggregateId)?.events ?: mutableListOf()
        return (UnInitializedRoom(aggregateId)
            .addAll(events)
            .getAggregate() as Room)
    }

    @JvmDefault
    fun saveEvent(aggregateId: String, event: RoomEvent): EventStream {
        //TODO: Throw exception
        return save((findByAggregateId(aggregateId) ?: EventStream(aggregateId)).addEvent(event))
    }
}