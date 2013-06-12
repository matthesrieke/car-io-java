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
package org.n52.car.io.jackson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.n52.car.io.schema.Validation;
import org.n52.car.io.schema.ValidationError;

import static org.hamcrest.CoreMatchers.*;

public class ValidationTest {
	
	@Test
	public void shouldPassValidationForTracks() {
		List<ValidationError> errors = Validation.getInstance().validate(readTracks(),
				"http://schema.envirocar.org/tracks.json#");
		
		Assert.assertThat(errors.size(), is(0));
	}
	
	@Test
	public void shouldFailValidationForTracks() {
		List<ValidationError> errors = Validation.getInstance().validate(readInvalidTracks(),
				"http://schema.envirocar.org/tracks.json#");
		
		Assert.assertThat(errors.size(), is(not(0)));
		
		for (ValidationError validationError : errors) {
			Assert.assertThat(validationError.getMessage(), is(anyOf(
					containsString("additional properties are not allowed"),
					containsString("missing required property")
					)));
		}
	}
	
	private Reader readInvalidTracks() {
		InputStream is = getClass().getResourceAsStream("tracks-invalid.json");
		return new BufferedReader(new InputStreamReader(is));
	}

	private Reader readTracks() {
		InputStream is = getClass().getResourceAsStream("tracks.json");
		return new BufferedReader(new InputStreamReader(is));
	}

}
