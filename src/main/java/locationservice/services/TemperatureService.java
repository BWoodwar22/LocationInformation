package locationservice.services;

public interface TemperatureService {

	/**
	 * Get the city name for the supplied location
	 * 
	 * @param location A LocationData object
	 * @return The same LocationData object with the temperature field filled 
	 */
	public LocationData getTemperature(LocationData location);
}
