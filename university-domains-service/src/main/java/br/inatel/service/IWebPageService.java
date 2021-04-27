package br.inatel.service;

import java.util.Set;

import br.inatel.model.WebPageEntity;

public interface IWebPageService {
	
	/**
	 * 
	 * @param webPageEntity
	 * @return
	 */
	WebPageEntity save(WebPageEntity webPageEntity);
	
	/**
	 * 
	 * @return
	 */
	Set<WebPageEntity> findAll();

	/**
	 * 
	 * @param pId
	 * @return
	 */
	WebPageEntity findById(Long pId);

	/**
	 * 
	 * @param pName
	 * @return
	 */
	WebPageEntity findByName(String pName);
	
	/**
	 * 
	 * @param pIdUniversity
	 * @return
	 */
	Set<WebPageEntity> findByUniversity(Long pIdUniversity);

	/**
	 * 
	 * @param webPageEntity
	 */
	void delete(WebPageEntity webPageEntity);

	/**
	 * 
	 * @param id
	 */
	void deleteFromUniversity(Long id);
}
