/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.car.io.jackson.types;

import org.joda.time.DateTime;
import org.n52.car.io.types.User;

public class UserImpl implements User {

	private String name;
	private DateTime created;
	private DateTime modified;
	private String mail;
	
	public String getName() {
		return name;
	}
	
	public DateTime getCreated() {
		return created;
	}
	
	public DateTime getModified() {
		return modified;
	}
	
	public String getMail() {
		return mail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public void setModified(DateTime modified) {
		this.modified = modified;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
