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
package org.n52.car.io.jackson.lazy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.n52.car.io.AccessException;
import org.n52.car.io.Preferences;
import org.n52.car.io.jackson.transform.MapToObject;
import org.n52.car.io.types.Resource;
import org.n52.car.io.types.ref.HyperReferable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLazyLoadable<T> implements InvocationHandler, HyperReferable<T>, Resource {

	protected static final Logger logger = LoggerFactory
			.getLogger(AbstractLazyLoadable.class);
	private T loaded;
	private String href;
	private String name;
	private String id;

	public AbstractLazyLoadable(String href, String name) {
		this.href = href;
		this.name = name;
		this.id = href.substring(href.lastIndexOf("/")+1, href.length());
	}
	
	public String getHref() {
		return href;
	}

	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}

	public synchronized T getReferredResource() {
		if (this.loaded != null)
			return this.loaded;

		logger.debug("Loading lazy resource '{}' at {}", this.name, this.href);
		
		Map<?, ?> map;
		try {
			map = Preferences.getInstance().getAccess().getResource(this.href);
		} catch (AccessException e) {
			logger.warn(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		this.loaded = getTransformator().createObjectFromMap(map);
		return this.loaded;
	}

	@Override
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		try {
			/*
			 * special case: equals (we do it by id)
			 */
			if (m.getName().equals("equals") && args.length == 1
					&& m.getParameterTypes()[0].equals(Object.class)) {
				if (args[0] instanceof Proxy) {
					return compareResourceIds(args[0], this.getId());
				}
				if (args[0] instanceof Resource) {
					return this.getId().equals(((Resource) args[0]).getId());
				}
			}
			
			T wrapped = getReferredResource();
			return m.invoke(wrapped, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (Exception e) {
			throw new RuntimeException("unexpected invocation exception: "
					+ e.getMessage());
		}
	}

	public abstract MapToObject<T> getTransformator();

	public static boolean compareResourceIds(Object obj, String id2) {
		if (obj instanceof Proxy) {
			InvocationHandler h = Proxy.getInvocationHandler(obj);
			if (h instanceof AbstractLazyLoadable) {
				return ((AbstractLazyLoadable<?>) h).getId().equals(id2);
			}
		}
		
		return false;
	}
	
}
