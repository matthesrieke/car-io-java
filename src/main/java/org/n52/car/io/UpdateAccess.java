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
package org.n52.car.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.n52.car.io.types.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAccess {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateAccess.class);
	
	private Map<Class<?>, List<Resource>> cached = new HashMap<Class<?>, List<Resource>>();

	@SuppressWarnings("unchecked")
	public List<?> getNewResources(List<?> resources) {
		if (resources == null || resources.size() == 0)
			return resources;
		
		Class<?> type = resolveListType(resources);
		if (this.cached.containsKey(type)) {
			List<Resource> previous = this.cached.get(type);
			List<Resource> result = findNewResources((List<Resource>) resources, previous);
			if (result.size() > 0) {
				for (Resource object : result) {
					previous.add(object);
				}
			}
			return result;
		}
		
		this.cached.put(type, (List<Resource>) resources);
		return resources;
	}

	private Class<?> resolveListType(List<?> resources) {
		Class<?>[] interfaces = resources.get(0).getClass().getInterfaces();
		
		if (interfaces == null || interfaces.length == 0) {
			logger.warn("Could not find an interface! Using class '{}' instead", resources.get(0).getClass());
			return resources.get(0).getClass();
		}
		
		if (interfaces.length != 1) {
			logger.warn("Found multiple interfaces! Using first: '{}'", interfaces[0]);
		}

		return interfaces[0];
	}

	private List<Resource> findNewResources(List<Resource> current, List<Resource> cached) {
		List<Resource> result = new ArrayList<Resource>();
		
		for (Resource object : current) {
			if (!cached.contains(object)) {
				result.add(object);
			}
		}
		
		return result;
	}
	
	

}
