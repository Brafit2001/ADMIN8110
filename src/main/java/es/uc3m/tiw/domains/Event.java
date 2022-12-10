package es.uc3m.tiw.domains;


import java.io.Serializable;


/**
 * The persistent class for the users database table.
 * 
 */

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idevent;
	
	private String name;

	private String category;

	private String date;
	
	private String city;
	
	private String hall;
	
	private String image;
		
	public Event() {
	}
	
	public Long getIdevent() {
		return this.idevent;
	}

	public void setIdevent(Long idevent) {
		this.idevent = idevent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getHall() {
		return this.hall;
	}

	public void setHall(String hall) {
		this.hall = hall;
	}
	
}