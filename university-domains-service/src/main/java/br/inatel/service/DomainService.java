package br.inatel.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.model.DomainEntity;
import br.inatel.persistence.DomainRepository;

@Service
public class DomainService implements IDomainService {

    @Autowired
    private DomainRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Set<DomainEntity> findAll() {
        return (Set<DomainEntity>) repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public DomainEntity findById(Long pId) {
    	Object obj = repository.findById(pId);
        return obj != null && ((Optional<DomainEntity>)obj).isPresent() ? ((Optional<DomainEntity>)obj).get() : null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public DomainEntity findByName(String pName) {
        return repository.findByName(pName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<DomainEntity> findByUniversity(Long pIdUniversity){
    	return repository.findByUniversity(pIdUniversity);
    }
    
    @Override
    @Transactional
	public DomainEntity save(DomainEntity domainEntity) {
    	return repository.save(domainEntity);
	}

	@Override
	@Transactional
	public void delete(DomainEntity domainEntity) {
		repository.delete(domainEntity);
	}

	@Override
	@Transactional
	public void deleteFromUniversity(Long pIdUniversity) {
		repository.deleteFromUniversity(pIdUniversity);
	}

}
