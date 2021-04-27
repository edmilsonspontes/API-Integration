package br.inatel.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpEntity;

import br.inatel.model.UniversityEntity;

public interface IUniversityService {
	
	/**
	 * 
	 * @param universityEntity
	 * @return
	 */
	UniversityEntity save(UniversityEntity universityEntity);
	
	/**
	 * 
	 * @return
	 */
	List<UniversityEntity> findAll();

	/**
	 * 
	 * @param pId
	 * @return
	 */
	UniversityEntity findById(Long pId);

	/**
	 * 
	 * @param pName
	 * @return
	 */
	UniversityEntity findByName(String pName);
	
	/**
	 * 
	 * @param pName
	 * @return
	 */
	List<UniversityEntity> findByLikeName(String pName);

	/**
	 * 
	 * @param universityEntity
	 */
	void delete(UniversityEntity universityEntity);
	
	/**
	 * Realiza a junção dos dados de "universities" no banco de dados
	 * @param universities
	 * @return
	 */
	List<UniversityEntity> mergeInDataBaseLocal(List<UniversityEntity> universities);
}
