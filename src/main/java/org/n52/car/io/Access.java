package org.n52.car.io;

import java.util.List;

import org.n52.car.io.types.Group;
import org.n52.car.io.types.Measurement;
import org.n52.car.io.types.Phenomenon;
import org.n52.car.io.types.Sensor;
import org.n52.car.io.types.Statistic;
import org.n52.car.io.types.Track;
import org.n52.car.io.types.User;

public interface Access {

	public void initialize(Connection conn);
	
	public List<User> getUsers() throws AccessException;
	
	public List<Group> getGroups() throws AccessException;
	
	public List<Track> getTracks() throws AccessException;
	
	public List<Sensor> getSensors() throws AccessException;
	
	public List<Phenomenon> getPhenomena() throws AccessException;
	
	public List<Measurement> getMeasurements() throws AccessException;
	
	public List<Statistic> getStatistics() throws AccessException;
	
}
