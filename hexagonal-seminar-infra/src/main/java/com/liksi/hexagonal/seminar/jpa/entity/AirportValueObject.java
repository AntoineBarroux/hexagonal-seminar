package com.liksi.hexagonal.seminar.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class AirportValueObject {
	   private String iataCode;
	   private String countryCode;

	   public String getIataCode() {
			  return iataCode;
	   }

	   public void setIataCode(String iataCode) {
			  this.iataCode = iataCode;
	   }

	   public String getCountryCode() {
			  return countryCode;
	   }

	   public void setCountryCode(String countryCode) {
			  this.countryCode = countryCode;
	   }
}
