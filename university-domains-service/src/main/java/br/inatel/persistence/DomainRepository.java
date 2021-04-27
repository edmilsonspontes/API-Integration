package br.inatel.persistence;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.inatel.model.DomainEntity;

public interface DomainRepository extends CrudRepository<DomainEntity, Long>{
	@Query("select d from DomainEntity d where d.name = :pName")
	DomainEntity findByName(@Param("pName") String pName);
	
	@Query("select d from DomainEntity d where d.university.id = :pIdUniversity")
	Set<DomainEntity> findByUniversity(@Param("pIdUniversity") Long pIdUniversity);
	
	@Modifying
	@Query("delete from DomainEntity d where d.university.id = :pIdUniversity")
	void deleteFromUniversity(@Param("pIdUniversity") Long pIdUniversity);
}
