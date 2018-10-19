package locationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import locationservice.services.CityService;
import locationservice.services.ElevationService;
import locationservice.services.GeoCodeService;
import locationservice.services.LocationData;
import locationservice.services.TemperatureService;
import locationservice.services.TimeZoneService;

@Component
public class LocationInformationManager {
	
	@Autowired
	@Qualifier("openWeatherMap")
	private GeoCodeService geoCodeService;
	
	@Autowired
	@Qualifier("openWeatherMap")
	private TemperatureService temperatureService;
	
	@Autowired
	@Qualifier("openWeatherMap")
	private CityService cityService;
	
	@Autowired
	@Qualifier("googleMaps")
	private TimeZoneService timeZoneService;
	
	@Autowired
	@Qualifier("googleMaps")
	private ElevationService elevationService;
	
	public LocationInformationManager() {
		
	}
	
	/**
	 * Gets a message with information about a zip code
	 * 
	 * @param zipCode
	 * @return message with info about the zip code or null if the zip code wasn't valid
	 */
	public String getMessage(String zipCode) {
		StringBuilder message = new StringBuilder();
		
		LocationData location = createLocationData(zipCode);

		if (location != null) {
			message.append("At the location: ").append(location.getCityName());
			message.append(", the temperature is: ").append(location.getTemperature());
			
			if (location.getTimeZone() != null)
				message.append(", the timezone is: ").append(location.getTimeZone().getDisplayName());
			else
				message.append(", the timezone is: null");
			
			message.append(", and the elevation is: ").append(location.getElevation());

			return message.toString();
		}
		else
			return null;
	}
	
	/**
	 * Creates and populate a LocationData object from the various services
	 * 
	 * @param zipCode
	 * @return A LocationData object with filled in data fields or null if it was an invalid zip code
	 */
	private LocationData createLocationData(String zipCode)
	{
		if (isZipCodeValid(zipCode)) {	
			LocationData location = new LocationData(zipCode);
			
			location = geoCodeService.getLatLon(location);
			location = cityService.getCityName(location);
			location = temperatureService.getTemperature(location);
			location = timeZoneService.getTimeZone(location);
			location = elevationService.getElevation(location);
			
			return location;
		}
		
		return null;
	}
	
	/**
	 * Checks if a zip code is valid
	 * Ideally this would check against a valid zip code database since not all 5 digits numbers 
	 * are zip codes, but for now will just check that we got 5 digits
	 * 
	 * @param zipCode
	 * @return true if the zip code is valid
	 */
	private boolean isZipCodeValid(String zipCode) {
		if (zipCode.matches("^[0-9]{5}$"))
			return true;
		
		return false;
	}
}
