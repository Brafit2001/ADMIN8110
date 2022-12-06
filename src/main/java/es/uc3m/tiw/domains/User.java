package es.uc3m.tiw.domains;


import java.io.Serializable;
import java.util.Set;



/**
 * The persistent class for the users database table.
 * 
 */

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long iduser;
	private String username;
	private String name;
	private String password;
	private String address;
	private String phone;


	public User() {
	}
	public Long getIduser(){
		return this.iduser;
	}

	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}

	public String getUsername(){
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword(){
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress(){
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}