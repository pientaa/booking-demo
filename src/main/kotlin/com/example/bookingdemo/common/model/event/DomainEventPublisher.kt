package com.example.bookingdemo.common.model.event

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class DomainEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    companion object {
        private val logger = LoggerFactory.getLogger(DomainEventPublisher::class.java)
    }

    fun publishEvent(domainEvent: DomainEvent) {
        logger.info("Domain event: ${domainEvent.eventType} with id: ${domainEvent.id} published", domainEvent)
        applicationEventPublisher.publishEvent(domainEvent)
    }
}