package br.inatel.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.inatel.model.DomainEntity;
import br.inatel.model.UniversityEntity;
import br.inatel.model.WebPageEntity;
import br.inatel.persistence.UniversityRepository;
import br.inatel.util.Utils;

@Service
public class UniversityService implements IUniversityService {

    @Autowired
    private UniversityRepository repository;
    
	@Autowired
	IDomainService domainService;
	
	@Autowired
	IWebPageService webPageService;

    @Override
    public List<UniversityEntity> findAll() {
        return (List<UniversityEntity>) repository.findAll();
    }
    
    @Override
    public UniversityEntity findById(Long pId) {
    	Object obj = repository.findById(pId);
        return obj != null && ((Optional<UniversityEntity>)obj).isPresent() ? ((Optional<UniversityEntity>)obj).get() : null;
    }
    
    @Override
    public List<UniversityEntity> findByLikeName(String pName) {
        return repository.findByLikeName(pName);
    }
    
    @Override
    public UniversityEntity findByName(String pName) {
        return repository.findByName(pName);
    }

    @Override
	public UniversityEntity save(UniversityEntity universityEntity) {
    	return repository.save(universityEntity);
	}

	@Override
	public void delete(UniversityEntity universityEntity) {
		repository.delete(universityEntity);
	}
	
	@Override
	public List<UniversityEntity> mergeInDataBaseLocal(List<UniversityEntity> universities) {
		if(universities != null && universities.size() > 0) {
			universities.forEach((Object universityObjMap) -> {
				
				String universityJson = Utils.mapToJsonUniversity((Map) universityObjMap);
				UniversityEntity university = null;
				try {
					university = new Gson().fromJson(universityJson, UniversityEntity.class);
					
					if(university != null && findByName(university.getName()) == null) {
						final UniversityEntity createdUniversityEntity = save(university);
						
						if(createdUniversityEntity.getDomains() != null && createdUniversityEntity.getDomains().size() > 0) {
							createdUniversityEntity.getDomains().forEach((DomainEntity domain) -> {
								domain.setUniversity(createdUniversityEntity);
								domainService.save(domain);
							});
						}
						
						if(createdUniversityEntity.getWeb_pages() != null && createdUniversityEntity.getWeb_pages().size() > 0) {
							createdUniversityEntity.getWeb_pages().forEach((WebPageEntity webpage) -> {
								webpage.setUniversity(createdUniversityEntity);
								webPageService.save(webpage);
							});
						}
					}
				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				
			});
		}
		
		List<UniversityEntity> result = findAll();
		result = findDetails(result);
		return result;
	}

	public List<UniversityEntity> findDetails(List<UniversityEntity> universities) {
		universities.forEach((UniversityEntity university) -> {
			Set<DomainEntity> domains = domainService.findByUniversity(university.getId());
			university.setDomains(domains);
			
			Set<WebPageEntity> webPages = webPageService.findByUniversity(university.getId());
			university.setWeb_pages(webPages);
			
		});
		return universities;
	}
	
}
