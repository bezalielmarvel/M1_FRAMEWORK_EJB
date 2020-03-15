package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Embeddable;

@Embeddable
public class ContactInformation {
	
	
	public ContactInformation() {
		super();
	}

	public ContactInformation(String phoneNumber, String email, Address address) {
		super();
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}

	String phoneNumber;
	
	String email;
	
	Address address;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ContactInformation [phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

	
	
}
