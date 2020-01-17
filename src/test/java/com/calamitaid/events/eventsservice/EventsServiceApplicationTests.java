package com.calamitaid.events.eventsservice;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.calamitaid.events.eventsservice.model.Event;
import com.calamitaid.events.eventsservice.repository.EventRepository;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
properties = {"spring.cloud.discovery.enabled = false"})
@RunWith(SpringRunner.class)
class EventsServiceApplicationTests {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	WebTestClient webTestClient;

	@Test
	void testAddEvent() {
		
		Event evt1 = new Event(UUID.randomUUID(), "Heavy Thunderstroming", LocalDate.now(), "duminda", "Badullaa");
		
		webTestClient.post().uri("/events").accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.body(Mono.just(evt1), Event.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$.eventId").isNotEmpty()
		.jsonPath("$.eventName").isEqualTo("Heavy Thunderstroming");
	}
	
	@Test
	void testGetAllEvents() {
		
		webTestClient.get().uri("/events").accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(Event.class);
		
	}
	
	@Test
	void testDeleteEvent() {
		
		Event evtToDelete = eventRepository
				.save(new Event(UUID.randomUUID(),"Tsunami", LocalDate.of(2012, Month.APRIL, 12),"duminda", "Kandy"))
				.block();
		
		webTestClient.delete().uri("/events/{id}", Collections.singletonMap("id", evtToDelete.getEventId()))
		.exchange()
		.expectStatus().isOk();
	}

}
