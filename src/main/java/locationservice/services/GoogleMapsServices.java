package locationservice.services;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Service implementation using GoogleMap APIs to supply the data.
 * Each call will only make a request if the field is not already filled to reduce duplicate calls.
 */

@Service("googleMaps")
public class GoogleMapsServices  extends LocationServiceImpl implements TimeZoneService, ElevationService {
	
	@Value("${GoogleMaps.elevation.url}")
	private String elevationUrlBase;
	
	@Value("${GoogleMaps.timezone.url}")
	private String timeZoneUrlBase;
	
	@Value("${GoogleMaps.key}")
	private String key;
	
	@Override
	public LocationData getElevation(LocationData location) {
		try {
			if (location.getLatitude() != null && location.getLongitude() != null) {
				String url = replaceLocationTokens(elevationUrlBase+key, location);
				JsonNode root = getServiceResponseJson(url);
				Double elevation = root.get("results").get(0).get("elevation").asDouble();
				location.setElevation(elevation);
			}
		}
		catch(Exception e) {
			//Silently ignore parse issues for now
		}
		
		return location;
	}

	@Override
	public LocationData getTimeZone(LocationData location) {
		try {
			if (location.getLatitude() != null && location.getLongitude() != null) {
				String url = replaceLocationTokens(timeZoneUrlBase+key, location);
				JsonNode response = getServiceResponseJson(url);
				String timeZoneID = response.get("timeZoneId").asText();
				location.setTimeZone(TimeZone.getTimeZone(timeZoneID));
			}
		}
		catch(Exception e) {
			//Silently ignore parse issues for now
		}
		
		return location;
	}

}
