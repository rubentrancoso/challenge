package com.company.challenge.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.company.challenge.helper.UUIDGen;
import com.company.challenge.repositories.Auditable;

@Entity
public class User extends Auditable<String> {

	@Id
	@Column(name = "UUID",unique=true, nullable = false)
	private String id;
	
	@Column(unique=true)
	private String email;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<Phone> phones;

	private Date last_login;
	private String token;
	
	User() {
		generateId();
	}
	
	public User(String email) {
		this.email = email;
	}

	private void generateId() {
		this.id = UUIDGen.getUUID();
	}
	
    @PrePersist
    public void onPersist() {
    	generateId();
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

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login() {
		this.last_login = new Date();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.phones!=null)
			for(Phone phone: this.phones) {
				sb.append(String.format("[phone=(%s) %s],", phone.getDdd(), phone.getNumber()));
			}
		String phones = sb.toString();
		return String.format("[uuid=%s] [email=%s] [phones=%s] [created=%tc] [modified=%tc] [last_login=%tcL] [token=%s]", this.id, this.email, phones, this.created, this.modified, this.last_login, this.token);
	}
	

}
