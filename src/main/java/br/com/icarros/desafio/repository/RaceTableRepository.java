package br.com.icarros.desafio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.icarros.desafio.model.RaceTable;

@Repository
public interface RaceTableRepository extends MongoRepository<RaceTable, String>{
	
	RaceTable findBySeasonAndRound(String season, String round);
}
