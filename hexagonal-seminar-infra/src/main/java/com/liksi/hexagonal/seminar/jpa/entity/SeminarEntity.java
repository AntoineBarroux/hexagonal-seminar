package com.liksi.hexagonal.seminar.jpa.entity;

import com.liksi.hexagonal.seminar.model.Airport;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "seminar")
public class SeminarEntity {
	   @Id
	   private UUID id;
	   private AirportValueObject departure;
	   private AirportValueObject arrival;
	   private LocalDate startDate;
	   private int attendees;
	   private int carbon;

	   public UUID getId() {
			  return id;
	   }

	   public void setId(UUID id) {
			  this.id = id;
	   }

	   public AirportValueObject getDeparture() {
			  return departure;
	   }

	   public void setDeparture(AirportValueObject departure) {
			  this.departure = departure;
	   }

	   public AirportValueObject getArrival() {
			  return arrival;
	   }

	   public void setArrival(AirportValueObject arrival) {
			  this.arrival = arrival;
	   }

	   public LocalDate getStartDate() {
			  return startDate;
	   }

	   public void setStartDate(LocalDate startDate) {
			  this.startDate = startDate;
	   }

	   public int getAttendees() {
			  return attendees;
	   }

	   public void setAttendees(int attendees) {
			  this.attendees = attendees;
	   }

	   public int getCarbon() {
			  return carbon;
	   }

	   public void setCarbon(int carbon) {
			  this.carbon = carbon;
	   }
}
