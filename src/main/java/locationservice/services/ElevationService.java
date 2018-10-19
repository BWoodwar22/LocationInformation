package locationservice.services;

public interface ElevationService {

	/**
	 * Get the city name for the supplied location
	 * 
	 * @param location A LocationData object
	 * @return The same LocationData object with the elevation field filled 
	 */
	public LocationData getElevation(LocationData location);
}
