package br.inatel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import br.inatel.service.IUniversityService;
import br.inatel.service.UniversityService;
import br.inatel.util.Utils;

@SpringBootTest
class UniversityDomainsServiceApplicationTests {
	final String URL_UNIVERSITY = "http://localhost:8090/university";
	final String COUNTRY_UNIVERSITY = "Brazil";

	IUniversityService universityService = new UniversityService();

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testUniversityListSuccess() throws URISyntaxException 	{
	    RestTemplate restTemplate = new RestTemplate();
	     
	    HttpEntity<String> httpEntity = Utils.createHttpRequestFromCountry(COUNTRY_UNIVERSITY);
	    Object response = (Object) restTemplate.postForObject(new URI(URL_UNIVERSITY), httpEntity, Object.class);
	    assertEquals(true, ((Map)response).get("universities") != null);
	}

}
