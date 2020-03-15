package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	int number;
	
	LocalDate date;
	
	@OneToOne()
	Airport arrival;

	@OneToOne()
	Airport departure;
	
	@OneToMany(mappedBy = "flight")
	List<Seat> seats = new ArrayList<Seat>();
	
	@OneToOne()
	Company company;
	
	LocalTime arrivalTime;
	
	LocalTime departureTime;
	
	int flightDuration;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getFlightDuration() {
		return flightDuration;
	}

	public void setFlightDuration() {
		//this.flightDuration = (int) ((MINUTES.between(this.arrivalTime, this.departureTime) + 1440) % 1440);
		this.flightDuration = (int) Duration.between(this.departureTime, this.arrivalTime).toMinutes();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	
	
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getArrival() {
		return arrival.IATA;
	}

	public void setArrival(Airport arrival) {
		this.arrival = arrival;
	}

	public String getDeparture() {
		return departure.IATA;
	}

	public void setDeparture(Airport departure) {
		this.departure = departure;
	}


	
	
}
