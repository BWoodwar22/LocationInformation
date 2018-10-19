package locationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import locationservice.LocationInformationManager;

@RestController
public class LocationInformationController {

	@Autowired
	private LocationInformationManager manager;

	@CrossOrigin
	@RequestMapping(value = "/zip-code/{zipCode}", method = RequestMethod.GET)
	public ResponseEntity<String> getLocationInformation(@PathVariable("zipCode") String zipCode) {
		String message = manager.getMessage(zipCode);
		
		if (message != null)
			return ResponseEntity.status(HttpStatus.OK).body(message);
		else
			return ResponseEntity.notFound().build();
	}
}
