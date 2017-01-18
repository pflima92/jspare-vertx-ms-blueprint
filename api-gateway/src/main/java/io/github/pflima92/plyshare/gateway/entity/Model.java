/*
 *
 */
package io.github.pflima92.plyshare.gateway.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class Model implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;

	@Column(name = "dt_criado", updatable = false)
	protected LocalDateTime createdAt;

	@Column(name = "dt_atualizado", updatable = true)
	protected LocalDateTime updatedAt;

	@PrePersist
	public void onCreatedAt() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdatedAt() {
		updatedAt = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public Model setId(int id) {
		this.id = id;
		return this;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Model setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Model setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}
}