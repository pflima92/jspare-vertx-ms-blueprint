/*
 *
 */
package org.jspare.spareco.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@NamedQueries({
		@NamedQuery(name = "User.findByLoginOrMailAndPassword", query = "SELECT u from User u WHERE (u.username = :username OR u.mail = :mail) AND (u.encryptedPassword = :encryptedPassword)") })
@Entity
@Table(name = "users")
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class User extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String username;
	@Column(name = "password")
	@JsonIgnore
	private String encryptedPassword;
	@Column
	private String name;
	@Column
	private String mail;
	@Transient
	private String password;
}