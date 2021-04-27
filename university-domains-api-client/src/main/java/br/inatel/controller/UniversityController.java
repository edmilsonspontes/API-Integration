package br.inatel.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.inatel.domain.University;

@RestController
@RequestMapping(path = "/university")
public class UniversityController {
	
	@PostMapping(path= "", consumes = "application/json", produces = "application/json")
    public ResponseEntity univercites(@RequestBody Object request) {
		ResponseEntity result = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		String url_api = getUri(request);
		String country = getCountry(request);

		try {
			if(country != null && url_api != null) {
				url_api += "/search?country=" + country;
				RestTemplate restTemplate = new RestTemplate();
				List<University> universities = (List<University>) restTemplate.getForObject(url_api, Object.class);
				if(universities != null && universities.size() > 0) {
					Map<String, List> universitiesResult = new HashMap<String, List>();
					universitiesResult.put("universities", universities);
					result = new ResponseEntity(universitiesResult, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			result = new ResponseEntity(null, HttpStatus.BAD_GATEWAY);
		}

        return result;
    } 
	
	/**
	 * Obtém o país do qual será listado as universidades
	 * @param request
	 * @return
	 */
	private String getCountry(Object request) {
		
		University university = new Gson().fromJson(((Map) request).get("university").toString(), University.class);
		Object country = university != null ? university.getCountry() : ((Map) request).get("country");
		
		return country != null ? country.toString() : null;
	}

	/**
	 * Obtém URI do request
	 * @param request
	 * @return
	 */
	private String getUri(Object request) {
		final String uriResult = "http://universities.hipolabs.com";

		Object url_api = ((Map) request).get("url_api");
		if(url_api != null && !url_api.toString().trim().equals("")) {
			return url_api.toString().trim();
		}

		return uriResult;
	}


}
