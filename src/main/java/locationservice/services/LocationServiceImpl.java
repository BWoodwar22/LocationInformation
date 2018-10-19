package locationservice.services;

import java.time.Instant;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service implementations extend this
 */
public abstract class LocationServiceImpl {
	
	/**
	 * Makes a connection and gets the response from the supplied url
	 * 
	 * @param urlString
	 * @return
	 */
	protected JsonNode getServiceResponseJson(String url) {
		try {
			Client client = ClientBuilder.newClient();
			Response response = client.target(url).request().get();
			String responseString = response.readEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(responseString);
		}
		catch(Exception e) {
		}
		
		return null;
	}
	
	/**
	 * Returns the supplied url with tokens for zip code and lat/lon replaced with the actual values
	 * 
	 * @param url
	 * @return
	 */
	protected String replaceLocationTokens(String url, LocationData location) {
		
		if (url == null || location == null)
			return null;
		
		url = url.replaceAll("\\{ZIPCODE\\}", location.getZipCode());
		url = url.replaceAll("\\{LAT\\}", String.valueOf(location.getLatitude()));
		url = url.replaceAll("\\{LON\\}", String.valueOf(location.getLongitude()));
		url = url.replaceAll("\\{TIMESTAMP\\}", String.valueOf(Instant.now().getEpochSecond()));
		
		return url;
	}
}
