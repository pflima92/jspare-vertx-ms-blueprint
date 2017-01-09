/*
 *
 */
package org.jspare.spareco.gateway.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "gateway")
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Gateway extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String serialKey;
	@Column
	private String privateKey;
	@Column
	private String owner;
	@Column
	private String version;
	@Column
	private String build;
	@Column
	private LocalDateTime lastStarted;
}