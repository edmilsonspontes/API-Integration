package br.inatel.service;

import java.util.Set;

import br.inatel.model.DomainEntity;

public interface IDomainService {
	
	/**
	 * 
	 * @param domainEntity
	 * @return
	 */
	DomainEntity save(DomainEntity domainEntity);
	
	/**
	 * 
	 * @return
	 */
	Set<DomainEntity> findAll();

	/**
	 * 
	 * @param pId
	 * @return
	 */
	DomainEntity findById(Long pId);

	/**
	 * 
	 * @param pName
	 * @return
	 */
	DomainEntity findByName(String pName);
	
	/**
	 * 
	 * @param pIdUniversity
	 * @return
	 */
	Set<DomainEntity> findByUniversity(Long pIdUniversity);

	/**
	 * 
	 * @param domainEntity
	 */
	void delete(DomainEntity domainEntity);

	/**
	 * 
	 * @param id
	 */
	void deleteFromUniversity(Long id);
}
