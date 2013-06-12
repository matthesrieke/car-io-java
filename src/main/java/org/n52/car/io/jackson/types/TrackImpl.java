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
import org.n52.car.io.types.Track;

public class TrackImpl implements Track {

	private String id;
	private String href;
	private DateTime modified;
	private String name;
	private DateTime created;

	public void setId(String object) {
		this.id = object;
	}

	public void setModified(String string) {
		this.modified = new DateTime(string);
	}

	public void setName(String string) {
		this.name = string;
	}

	public void setHref(String string) {
		this.href = string;
	}

	public DateTime getModified() {
		return modified;
	}

	public void setModified(DateTime modified) {
		this.modified = modified;
	}

	public String getId() {
		return id;
	}

	public String getHref() {
		return href;
	}

	public String getName() {
		return name;
	}

	public void setCreated(DateTime dateTime) {
		this.created = dateTime;
	}

	@Override
	public DateTime getCreated() {
		return created;
	}
	
	
	

}
