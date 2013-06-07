package org.n52.car.io.types;

import org.joda.time.DateTime;

public class Track {

	private String id;
	private String href;
	private DateTime modified;
	private String name;

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
	
	

}
