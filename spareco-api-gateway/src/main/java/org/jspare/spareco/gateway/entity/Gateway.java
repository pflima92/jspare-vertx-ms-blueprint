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

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NamedQueries({ @NamedQuery(name = "Gateway.findByProfile", query = "from Gateway g WHERE g.profile = :profile") })
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

	@Column(unique = true, nullable = false)
	private String profile;
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