package br.com.icarros.desafio.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.icarros.desafio.model.RaceTable;

@RunWith(SpringRunner.class)
@DataMongoTest

public class RaceTableRepositoryTest {

	@Autowired
	private RaceTableRepository raceTableRepository;
	
	@Before 
	@Test
	public void initTest() {
		RaceTable raceTable = new RaceTable(); 
		raceTable.setId("testeId");
		raceTable.setSeason("testeSeason");
		raceTable.setRound("testeRound");
		
		raceTableRepository.save(raceTable);
	}
	
	@Test
	public void findBySeasonAndRoundTest() {
		RaceTable r = raceTableRepository.findBySeasonAndRound("testeSeason","testeRound");
		assertNotNull(r.getId());
	}
	
	@Test
	public void findBySeasonAndRoundIdTest() {
		RaceTable raceTableBusca = raceTableRepository.findBySeasonAndRound("testeSeason","testeRound");
		assertEquals("testeId",raceTableBusca.getId());
	}
	
	@Test
	public void findBySeasonAndRoundSeasonTest() {
		RaceTable raceTableBusca = raceTableRepository.findBySeasonAndRound("testeSeason","testeRound");
		assertEquals("testeSeason",raceTableBusca.getSeason());
	}
	
	@Test
	public void findBySeasonAndRoundRoundTest() {
		RaceTable raceTableBusca = raceTableRepository.findBySeasonAndRound("testeSeason","testeRound");
		assertEquals("testeRound",raceTableBusca.getRound());
	}
	
	@After 
	@Test
	public void finishTest() {
		RaceTable raceTable = new RaceTable(); 
		raceTable.setId("testeId");
		raceTable.setSeason("testeSeason");
		raceTable.setRound("testeRound");
		raceTableRepository.delete(raceTable);
	}
}
