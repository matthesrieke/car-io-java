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
package org.n52.car.io.jackson.transform;

import java.util.Map;

import org.joda.time.DateTime;
import org.n52.car.io.jackson.lazy.HyperReferableInstantiation;
import org.n52.car.io.jackson.lazy.LazyUser;
import org.n52.car.io.jackson.types.UserImpl;
import org.n52.car.io.types.User;

public class MapToUser extends MapToObject<User> implements HyperReferableInstantiation<User> {

	@Override
	public User createObjectFromMap(Map<?, ?> map) {
		UserImpl result = new UserImpl();
		result.setName((String) map.get("name"));
		result.setCreated(new DateTime(map.get("created")));
		result.setModified(new DateTime(map.get("modified")));
		result.setMail((String) map.get("mail"));
		return result;
	}

	@Override
	public User createHyperReferableInstance(String href, String name) {
		return createHyperReferable(new LazyUser(href, name), User.class);
	}

}
