package locationservice.services;

public interface TimeZoneService {

	/**
	 * Get the city name for the supplied location
	 * 
	 * @param location A LocationData object
	 * @return The same LocationData object with the timezone field filled 
	 */
	public LocationData getTimeZone(LocationData location);
}
