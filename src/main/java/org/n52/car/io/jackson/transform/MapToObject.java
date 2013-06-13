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

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.n52.car.io.jackson.lazy.AbstractLazyLoadable;
import org.n52.car.io.jackson.lazy.HyperReferableInstantiation;

public abstract class MapToObject<T> {
	
	@SuppressWarnings("unchecked")
	public List<T> fromMap(Map<?, ?> map, String baseName) {
		List<Object> objects = (List<Object>) map.get(baseName);
		
		List<T> result = new ArrayList<T>(objects.size());
		
		Map<?, ?> node;
		for (Object o : objects) {
			if (o instanceof Map<?, ?>) {
				node = (Map<?, ?>) o;
				String href = (String) node.get("href");
				
				if (notNullAndNotEmpty(href) && this instanceof HyperReferableInstantiation) {
					String name = (String) node.get("name");
					result.add(((HyperReferableInstantiation<T>) this).createHyperReferableInstance(href, name));
				}
				else {
					result.add(createObjectFromMap(node));
				}
			}
		}
		
		return result;
	}

	public abstract T createObjectFromMap(Map<?, ?> map);

	protected boolean notNullAndNotEmpty(String s) {
		return s != null && !s.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	protected T createHyperReferable(AbstractLazyLoadable<T> loader,
			Class<? extends T> c) {
		Object proxy = Proxy.newProxyInstance(getClass().getClassLoader(),
				new Class[] {c},
				loader);
		return (T) proxy;
	}
}
