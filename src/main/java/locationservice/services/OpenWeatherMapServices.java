package locationservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Service implementation using OpenWeatherMap to supply the data.
 * Each call will only make a request if the field is not already filled to reduce duplicate calls.
 */

@Service("openWeatherMap")
public class OpenWeatherMapServices extends LocationServiceImpl implements CityService, TemperatureService, GeoCodeService {

	@Value("${OpenWeatherMap.url}")
	private String urlBase;
	
	@Value("${OpenWeatherMap.key}")
	private String key;
	
	@Override
	public LocationData getTemperature(LocationData location) {
		
		if (location.getZipCode() != null && location.getTemperature() == null) {
			String url = replaceLocationTokens(urlBase+key, location);
			JsonNode response = getServiceResponseJson(url);
			fillLocationDataFields(location, response);
		}
		
		return location;
	}

	@Override
	public LocationData getCityName(LocationData location) {
		
		if (location.getZipCode() != null && location.getCityName() == null) {
			String url = replaceLocationTokens(urlBase+key, location);
			JsonNode response = getServiceResponseJson(url);
			fillLocationDataFields(location, response);
		}
		
		return location;
	}

	@Override
	public LocationData getLatLon(LocationData location) {
		
		if (location.getZipCode() != null && (location.getLatitude() == null || location.getLongitude() == null)) {
			String url = replaceLocationTokens(urlBase+key, location);
			JsonNode response = getServiceResponseJson(url);
			fillLocationDataFields(location, response);
		}
		
		return location;
	}
	
	/**
	 * Will try to fill in as many missing LocationData fields as possible from the response
	 *  
	 * @param location
	 * @param root
	 */
	private void fillLocationDataFields(LocationData location, JsonNode root) {
		try {
			JsonNode node = null;
			
			if (root == null)
				return;
			
			if (location.getLatitude() == null || location.getLongitude() == null) {
				node = root.get("coord");
	
				if (node != null && node.has("lat") && node.has("lon")) {
					location.setLatitude(node.get("lat").asDouble());
					location.setLongitude(node.get("lon").asDouble());
				}
			}
			
			if (location.getTemperature() == null) {
				node = root.get("main");
	
				if (node != null && node.has("temp"))
					location.setTemperature(node.get("temp").asDouble());
			}
			
			if (location.getCityName() == null) {
				node = root.get("name");
				
				if (node != null)
					location.setCityName(node.asText());
			}
		}
		catch(Exception e) {
			//Silently ignore parse issues for now
		}
	}
}
