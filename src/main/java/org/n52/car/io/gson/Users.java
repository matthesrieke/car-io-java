package org.n52.car.io.gson;

import java.io.IOException;
import java.util.ArrayList;

import org.n52.car.io.types.User;

import com.google.gson.stream.JsonReader;

public class Users extends ArrayList<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Adapter extends BasicTypeAdapter<Users> {

		@Override
		public Users read(JsonReader in) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
