package br.inatel.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.model.WebPageEntity;
import br.inatel.persistence.WebPageRepository;

@Service
public class WebPageService implements IWebPageService {

    @Autowired
    private WebPageRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Set<WebPageEntity> findAll() {
        return (Set<WebPageEntity>) repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public WebPageEntity findById(Long pId) {
    	Object obj = repository.findById(pId);
        return obj != null && ((Optional<WebPageEntity>)obj).isPresent() ? ((Optional<WebPageEntity>)obj).get() : null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public WebPageEntity findByName(String pName) {
        return repository.findByName(pName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<WebPageEntity> findByUniversity(Long pIdUniversity){
    	return repository.findByUniversity(pIdUniversity);
    }
    
    @Override
    @Transactional
	public WebPageEntity save(WebPageEntity webPageEntity) {
    	return repository.save(webPageEntity);
	}

	@Override
	@Transactional
	public void delete(WebPageEntity webPageEntity) {
		repository.delete(webPageEntity);
	}

	@Override
	@Transactional
	public void deleteFromUniversity(Long pIdUniversity) {
		repository.deleteFromUniversity(pIdUniversity);
	}
    

}
