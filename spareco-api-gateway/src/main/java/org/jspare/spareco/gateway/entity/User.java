/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jspare.spareco.gateway.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;

@NamedQueries({ @NamedQuery(name = "User.findByLoginOrMailAndPassword",
		query = "SELECT u from User u WHERE (u.username = :username OR u.mail = :mail) AND (u.encryptedPassword = :encryptedPassword)") })
@Entity
@Table(name = "users")
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

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	/**
	 * @return the encryptedPassword
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * @param encryptedPassword
	 *            the encryptedPassword to set
	 */
	public User setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public User setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public User setMail(String mail) {
		this.mail = mail;
		return this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
}