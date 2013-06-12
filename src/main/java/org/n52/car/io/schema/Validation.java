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
package org.n52.car.io.schema;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.cfg.LoadingConfiguration;
import com.github.fge.jsonschema.cfg.LoadingConfigurationBuilder;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;

public class Validation {
	
	private static final Logger logger = LoggerFactory.getLogger(Validation.class);

	private static Validation instance;

	private JsonSchemaFactory schemaFactory;

	public static synchronized Validation getInstance() {
		if (instance == null)
			instance = new Validation();
		
		return instance;
	}
	
	private Validation() {
		try {
			schemaFactory = createFactory();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	private JsonSchemaFactory createFactory() throws Exception {
		return JsonSchemaFactory.newBuilder()
				.setLoadingConfiguration(loadingConfiguration()).freeze();
	}

	private LoadingConfiguration loadingConfiguration() throws Exception {
		LoadingConfigurationBuilder cfgb = LoadingConfiguration.newBuilder();
		for (String schema : findSchemas()) {
			cfgb.preloadSchema(JsonLoader.fromResource(schema));
		}
		return cfgb.freeze();
	}

	private List<String> findSchemas() throws URISyntaxException {
		URL url = getClass().getResource("/schema");
		File baseDir = new File(url.toURI());
		File[] files = baseDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".json");
			}
		});
		
		List<String> result = new ArrayList<String>(files.length);
		String path;
		for (File f : files) {
			path = f.getAbsolutePath();
			result.add(path.substring(path.lastIndexOf("/schema"), path.length()));
		}
		return result;
	}

	public List<ValidationError> validate(Reader json, String schema) {
		try {
			return validate(createNode(json), schemaFactory.getJsonSchema(schema));
		} catch (ProcessingException e) {
			return Collections.singletonList(new ValidationError(e.getMessage()));
		} catch (JsonProcessingException e) {
			return Collections.singletonList(new ValidationError(e.getMessage()));
		} catch (IOException e) {
			return Collections.singletonList(new ValidationError(e.getMessage()));
		}
	}

	protected List<ValidationError> validate(JsonNode t, JsonSchema schema) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ProcessingReport report;
		try {
			report = schema.validate(t);
			if (!report.isSuccess()) {
				for (ProcessingMessage message : report) {
					errors.add(new ValidationError(message.getMessage()));
				}
			}
		} catch (ProcessingException e) {
			errors.add(new ValidationError(e.getMessage()));
		}
		
		return errors;
	}

	private JsonNode createNode(Reader json) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(json);
	}
	
}
