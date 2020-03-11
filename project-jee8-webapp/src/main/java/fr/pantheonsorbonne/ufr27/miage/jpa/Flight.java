package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.time.temporal.ChronoUnit.MINUTES;

import javax.persistence.CascadeType;
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

	@OneToOne(cascade = CascadeType.ALL)
	Airport arrival;

	@OneToOne(cascade = CascadeType.ALL)
	Airport departure;
	
	@OneToMany(mappedBy = "flight")
	List<Seat> seats = new ArrayList<Seat>();
	
	@OneToOne(cascade = CascadeType.ALL)
	Company company;
	
	Date date;
	
	LocalTime arrivalTime;
	
	LocalTime departureTime;
	
	int flightDuration;

	public int getFlightDuration() {
		return flightDuration;
	}

	public void setFlightDuration() {
		this.flightDuration = (int) ((MINUTES.between(this.arrivalTime, this.departureTime) + 1440) % 1440);
//		this.flightDuration = Duration.between(this.departureTime, this.arrivalTime);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
