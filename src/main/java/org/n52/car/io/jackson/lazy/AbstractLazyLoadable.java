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
import java.util.Map;

import org.n52.car.io.AccessException;
import org.n52.car.io.Preferences;
import org.n52.car.io.jackson.transform.MapToObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLazyLoadable<T> implements InvocationHandler {

	protected static final Logger logger = LoggerFactory
			.getLogger(AbstractLazyLoadable.class);
	private T loaded;
	private String href;

	public AbstractLazyLoadable(String href) {
		this.href = href;
	}
	
	public synchronized T lazyLoad() {
		if (this.loaded != null)
			return this.loaded;

		Map<?, ?> map;
		try {
			map = Preferences.getInstance().getAccess().getResource(this.href);
		} catch (AccessException e) {
			logger.warn(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return getTransformator().createObjectFromMap(map);
	}

	public abstract MapToObject<T> getTransformator();

	@Override
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		try {
			T wrapped = lazyLoad();
			return m.invoke(wrapped, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (Exception e) {
			throw new RuntimeException("unexpected invocation exception: "
					+ e.getMessage());
		}
	}

}
