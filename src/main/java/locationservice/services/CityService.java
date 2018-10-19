package locationservice.services;

public interface CityService {
	
	/**
	 * Get the city name for the supplied location
	 * 
	 * @param location A LocationData object
	 * @return The same LocationData object with the city name field filled 
	 */
	public LocationData getCityName(LocationData location);
}
