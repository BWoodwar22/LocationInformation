package locationservice.services;

public interface GeoCodeService {

	/**
	 * Get the city name for the supplied location
	 * 
	 * @param location A LocationData object
	 * @return The same LocationData object with the lat and lon fields filled 
	 */
	public LocationData getLatLon(LocationData location);
}
