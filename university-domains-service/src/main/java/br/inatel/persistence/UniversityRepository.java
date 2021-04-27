package br.inatel.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.inatel.model.UniversityEntity;

public interface UniversityRepository extends CrudRepository<UniversityEntity, Long> {
	@Query("select u from UniversityEntity u where u.name = :pName")
	UniversityEntity findByName(@Param("pName") String pName);
	
	@Query(value="select * from tb_university u where u.name_university like %:pName% ", nativeQuery=true)
	List<UniversityEntity> findByLikeName(@Param("pName") String pName);
}
