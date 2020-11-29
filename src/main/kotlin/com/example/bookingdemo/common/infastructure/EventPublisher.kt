package com.example.bookingdemo.common.infastructure

import com.example.bookingdemo.common.model.event.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun publish(event: DomainEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}