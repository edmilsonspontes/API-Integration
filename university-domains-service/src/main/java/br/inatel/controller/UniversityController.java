package br.inatel.controller;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.inatel.model.DomainEntity;
import br.inatel.model.UniversityEntity;
import br.inatel.model.WebPageEntity;
import br.inatel.service.IDomainService;
import br.inatel.service.IUniversityService;
import br.inatel.service.IWebPageService;
import br.inatel.util.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/university")
public class UniversityController {
	
	@Autowired
	IUniversityService universityService;
	
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IWebPageService webPageService;
	
	@Value("${university-domains-api.url}")
	private String url_api;
	
	@ApiOperation(value = "Retorna uma lista de universidades (apenas do Brasil)")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de universidades"),
	})
	@PostMapping(path= "/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity findAll(@RequestBody Object request) {
		ResponseEntity result = new ResponseEntity(null, HttpStatus.BAD_REQUEST);

		ResponseEntity apiList = findUniversitiesAPI(request);
		if(apiList.getStatusCode().equals(HttpStatus.OK)) {
			if(apiList.getBody() != null && ((Map)apiList.getBody()).get("universities") != null) {
				List universitiesList = (List) ((Map)apiList.getBody()).get("universities");
				Object mergeResult = universityService.mergeInDataBaseLocal(universitiesList);
				
				Map<String, Object> universities = new HashMap<>();
				universities.put("universities", mergeResult);
				result = new ResponseEntity(universities, HttpStatus.OK);
			}
		}
		else {
			result = new ResponseEntity(apiList, HttpStatus.BAD_REQUEST);
		}
		
		return result;
    }
	
	@ApiOperation(value = "Retorna uma lista de universidades (apenas do Brasil) já cadastradas na base de dados local")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de universidades"),
	})
	@PostMapping(path= "/local/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity findunivercitesLocal(@RequestBody Object request) {
		ResponseEntity result = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		
		List<UniversityEntity> list = universityService.findAll();
		try {
			if(list != null) {
				list.forEach((UniversityEntity universityEntity) -> {
					Set domains = domainService.findByUniversity(universityEntity.getId());
					Set webPages = webPageService.findByUniversity(universityEntity.getId());
					universityEntity.setDomains(domains);
					universityEntity.setWeb_pages(webPages);
				});
				
				Map<String, List> universities = new HashMap<>();
				universities.put("universities", list);
				return new ResponseEntity(universities, HttpStatus.OK);
			}
		} catch (Exception e) {
			result = new ResponseEntity("ERROR: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
        return result;
    }
	
	@ApiOperation(value = "Retorna uma lista de universidades (apenas do Brasil) recuperadas da api universities.hipolabs.com")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de universidades"),
	})
	@PostMapping(path= "/api/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity findUniversitiesAPI(@RequestBody Object request) {
		ResponseEntity result = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		
		String country = getCountry(request);
		try {
			if(country != null && url_api != null) {
				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<String> httpEntity = Utils.createHttpRequestFromCountry(country);
				Object objResponse = restTemplate.postForObject(url_api, httpEntity, Object.class);
				
				if(objResponse != null) {
					result = new ResponseEntity(objResponse, HttpStatus.OK);
				}
				else{
					result = new ResponseEntity("Nenhuma universidade encontrada", HttpStatus.NOT_FOUND);
				}
			}
		} catch (Exception e) {
			result = new ResponseEntity("ERROR: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
		}

        return result;
    }
	
	@ApiOperation(value = "Retorna a universidade identificada pelo id")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a universidade"),
	})
    @GetMapping("/{id}")
    public ResponseEntity<UniversityEntity> findById(@PathVariable(value = "id") Long universityId)  throws ResourceNotFoundException {
    	UniversityEntity universityEntity = universityService.findById(universityId);

		if(universityEntity == null) {
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
		
		getUniversityDetails(universityEntity);
		return new ResponseEntity(universityEntity, HttpStatus.OK);
    }
    
	@ApiOperation(value = "Retorna a universidade identificada pelo nome")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a universidade"),
	})
    @GetMapping("/name/{name}")
    public ResponseEntity<UniversityEntity> findByLikeName(@PathVariable(value = "name") String pName)  throws ResourceNotFoundException {
    	List<UniversityEntity> universities = universityService.findByLikeName(pName);

		if(universities == null) {
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
		universities.forEach((UniversityEntity universityEntity)-> {
			getUniversityDetails(universityEntity);
		});
		return new ResponseEntity(universities, HttpStatus.OK);
    }

    /**
     * Recupera os detalhes da universidade
     * @param universityEntity
     */
	private void getUniversityDetails(UniversityEntity universityEntity) {
		Set<DomainEntity> domainEntities = domainService.findByUniversity(universityEntity.getId());
		if(domainEntities != null) {
			universityEntity.setDomains(new HashSet<>(domainEntities));
		}
		
		Set<WebPageEntity> webPageEntities = webPageService.findByUniversity(universityEntity.getId());
		if(webPageEntities != null) {
			universityEntity.setWeb_pages(new HashSet<>(webPageEntities));
		}
	}
	
	@ApiOperation(value = "Adiciona uma univesidade na base de dados local")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a universidade cadastrada"),
	})
	@PostMapping(path="", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UniversityEntity> create(@RequestBody UniversityEntity pUniversityEntity) {
		if(pUniversityEntity != null) {
			
			if(universityService.findByName(pUniversityEntity.getName()) != null) {
				return new ResponseEntity("{ ERROR: Universidade possui cadastro.}", HttpStatus.BAD_REQUEST);
			}
			
			final UniversityEntity createdUniversityEntity = universityService.save(pUniversityEntity);

			pUniversityEntity.getDomains().forEach((DomainEntity domain) -> {
				domainService.save(domain);
			});
			
			pUniversityEntity.getWeb_pages().forEach((WebPageEntity webpage) -> {
				webPageService.save(webpage);
			});

			return new ResponseEntity(createdUniversityEntity, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Alterada dados da univesidade na base de dados local")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a universidade atualizada"),
	})
	@PutMapping("/{id}")
	public ResponseEntity<UniversityEntity> update(@PathVariable(value = "id") Long universityId,
			@RequestBody UniversityEntity universityRequest) throws ResourceNotFoundException {
		
		if(universityService.findById(universityId) == null) {
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
		
		//atualiza university
		universityRequest.setId(universityId);
		UniversityEntity universityUpdate = universityService.save(universityRequest);
		
		//atualiza domains
		universityRequest.getDomains().forEach((DomainEntity domain) -> {
			domain.setUniversity(universityUpdate);
			domainService.save(domain);
		});
		
		//atualiza webpages
		universityRequest.getWeb_pages().forEach((WebPageEntity webpage) -> {
			webpage.setUniversity(universityUpdate);
			webPageService.save(webpage);
		});
		
		return findById(universityId);
	}
	
	@ApiOperation(value = "Exclui a univesidade na base de dados local")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna sucesso se exclusão com sucesso"),
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> delete(@PathVariable(value = "id") Long universityId) throws ResourceNotFoundException {
		UniversityEntity universityEntity = universityService.findById(universityId);
		Map<String, Boolean> response = new HashMap<>();
		if(universityEntity == null) {
			response.put("not deleted", Boolean.FALSE);
			return new ResponseEntity(response, HttpStatus.NOT_FOUND);
		}

		domainService.deleteFromUniversity(universityEntity.getId());
		universityService.delete(universityEntity);
		
		response.put("deleted", Boolean.TRUE);
		return new ResponseEntity(response, HttpStatus.OK);
	}	

	/**
	 * Obtém o país do qual será listado as universidades
	 * @param request
	 * @return
	 */
	private String getCountry(Object request) {
		
		UniversityEntity university = new Gson().fromJson(((Map) request).get("university").toString(), UniversityEntity.class);
		Object country = university != null ? university.getCountry() : ((Map) request).get("country");
		
		return country != null ? country.toString() : null;
	}


}
