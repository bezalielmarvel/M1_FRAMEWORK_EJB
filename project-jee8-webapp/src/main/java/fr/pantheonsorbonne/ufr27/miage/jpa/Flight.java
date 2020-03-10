package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	String name;
	
	Date date;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Seat> getSeats() {
		return seats;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
