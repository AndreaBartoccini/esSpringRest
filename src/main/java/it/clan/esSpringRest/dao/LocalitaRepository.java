package it.clan.esSpringRest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.clan.esSpringRest.model.Localita;


public interface LocalitaRepository extends CrudRepository<Localita, Integer> {
	
	@Query("select l from Localita l where l.id = :i")
	public List<Localita> findByMyId(@Param("i") Integer id);
	
	public List<Localita> findByNome(String nome);
	
	@Query("select l from Localita l where l.temperatura < :t")
	public List<Localita> findByTempLessThan(@Param("t") Integer temperatura);
	
	@Query("select l from Localita l where l.temperatura > :t")
	public List<Localita> findByTempMoreThan(@Param("t") Integer temperatura);

}
