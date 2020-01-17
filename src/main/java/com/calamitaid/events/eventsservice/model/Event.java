package com.calamitaid.events.eventsservice.model;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	
	@Id
	private UUID eventId;
	private String eventName;
	private LocalDate occuredDate;
	private String createdUser;
	private String locationDistrict;

}
