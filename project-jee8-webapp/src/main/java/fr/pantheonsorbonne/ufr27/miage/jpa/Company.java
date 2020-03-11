package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Company
 *
 */
@Entity

public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	String name;

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
   
}
