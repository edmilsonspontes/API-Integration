package br.inatel.persistence;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.inatel.model.WebPageEntity;

public interface WebPageRepository extends CrudRepository<WebPageEntity, Long> {
	@Query("select wp from WebPageEntity wp where wp.url = :pUrl")
	WebPageEntity findByName(@Param("pUrl") String pUrl);
	
	@Query("select wp from WebPageEntity wp where wp.university.id = :pIdUniversity")
	Set<WebPageEntity> findByUniversity(@Param("pIdUniversity") Long pIdUniversity);
	
	@Modifying
	@Query("delete from WebPageEntity wp where wp.university.id = :pIdUniversity")
	void deleteFromUniversity(@Param("pIdUniversity") Long pIdUniversity);
}
