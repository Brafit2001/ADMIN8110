package es.uc3m.tiw.domains;

import java.io.Serializable;

/**
 * The persistent class for the users database table.
 * 
 */

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idticket;

	private Long iduser;

	private Long idevent;
	
	private String type;

	private float price;

	public Ticket(){

	}

	public Long getIdticket(){return this.idticket;}

	public void setIdticket(Long idticket) {
		this.idticket = idticket;
	}

	public Long getIduser(){return this.iduser;}

	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}

	public Long getIdevent(){return this.idevent;}

	public void setIdevent(Long idevent) {
		this.idevent = idevent;
	}

	public String getType(){return this.type;}

	public void setType(String type) {
		this.type = type;
	}

	public float getPrice(){return this.price;}

	public void setPrice(float price) {
		this.price = price;
	}


	
}