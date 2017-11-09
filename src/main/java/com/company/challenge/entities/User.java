package com.company.challenge.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.company.challenge.helper.UUIDGen;

@Entity
public class User {

	@Id
	@Column(name = "UUID",unique=true, nullable = false)
	private String id;
	
	@Column(unique=true)
	private String email;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<Phone> phones;
	
	User() {
		generateId();
	}
	
	public User(String email) {
		this.email = email;
	}

    @PrePersist
    public void generateId() {
    	this.id = UUIDGen.getUUID();
    }
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId() {}
	
	public String getId() {
		return id;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}
	
	public Set<Phone> addPhone(Phone phone) {
		if(this.phones == null)
			this.phones = new HashSet<Phone>();
		this.phones.add(phone);
		return getPhones();
	}
	
	public Set<Phone> removePhone(Phone phone) {
		this.phones.remove(phone);
		return getPhones();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.phones!=null)
			for(Phone phone: this.phones) {
				sb.append(String.format("[phone=(%s) %s],", phone.getDdd(), phone.getNumber()));
			}
		String phones = sb.toString();
		return String.format("[uuid=%s] [email=%s] [phones=%s]", this.id, this.email, phones);
	}
	

}
