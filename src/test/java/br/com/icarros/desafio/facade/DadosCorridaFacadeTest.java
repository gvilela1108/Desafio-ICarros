package br.com.icarros.desafio.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.icarros.desafio.dto.FiltroBusca;
import br.com.icarros.desafio.dto.InfoFiltroBusca;
import br.com.icarros.desafio.model.AverageSpeed;
import br.com.icarros.desafio.model.Constructor;
import br.com.icarros.desafio.model.Driver;
import br.com.icarros.desafio.model.FastestLap;
import br.com.icarros.desafio.model.RaceTable;
import br.com.icarros.desafio.model.Races;
import br.com.icarros.desafio.model.Results;
import br.com.icarros.desafio.repository.RaceTableRepository;

@RunWith(SpringRunner.class)
public class DadosCorridaFacadeTest {

	@TestConfiguration
    static class dadosCorridaFacadeTestContextConfiguration {
		          
        @Bean
        public DadosCorridaFacade dadosCorridaFacade() {
        	return new DadosCorridaFacade();
        }
    }
    @MockBean
	private RestTemplate restTemplate;
	
	@Autowired
	DadosCorridaFacade dadosCorridaFacade;
	
    @MockBean
    private RaceTableRepository raceTableRepository;
    
	@Value("${urlJsonUltimasCorridas}")
	private String urlJsonUltimasCorridas;
	
    @Before
    public void initTest() {
    	RaceTable raceTable = new RaceTable();
    	Races race = new Races();
    	Driver driver = new Driver();
    	Constructor constructor = new Constructor();
    	FastestLap fastestLap = new FastestLap();
    	AverageSpeed averageSpeed = new AverageSpeed();
    	Results result = new Results();
    	
    	ArrayList<Races> races = new ArrayList<>();
    	ArrayList<Results> results = new ArrayList<>();
    	
    	raceTable.setId("testeId");
		raceTable.setSeason("2018");
		raceTable.setRound("1");
		
		driver.setNationality("brazilian");
		driver.setGivenName("Felipe");
		driver.setFamilyName("Massa");
		driver.setDateOfBirth("1980-03-23");
		
		constructor.setNationality("italian");
		constructor.setName("ferrari");
		
		result.setConstructor(constructor);
		result.setDriver(driver);
		result.setPosition("1");
		result.setGrid("1");
		result.setPoints("10");
		result.setLaps("75");
		
		averageSpeed.setSpeed("300");
		fastestLap.setAverageSpeed(averageSpeed);
		
		result.setFastestLap(fastestLap);
		results.add(result);
		
		race.setResults(results);
		race.setRaceName("Interlagos");
		races.add(race);
		raceTable.setRaces(races);
		
		Mockito.when(raceTableRepository.findBySeasonAndRound(raceTable.getSeason(),raceTable.getRound())).thenReturn(raceTable);
    }
    
	@Test
	public void getDadosCorridaTest() throws Exception {
	
		RaceTable r = dadosCorridaFacade.getDadosCorrida(2018, "1");
		assertEquals("testeId",r.getId());
	
	}
	
	@Test
	public void getDadosCorridaByFilterDriverNationalityTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("0");
		filtroBusca.setParametro("brazilian");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("brazilian",z.getDriver().getNationality());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterDriverGivenNameTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("1");
		filtroBusca.setParametro("Felipe");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("Felipe",z.getDriver().getGivenName());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterDriverFamilyNameTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("2");
		filtroBusca.setParametro("Massa");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("Massa",z.getDriver().getFamilyName());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterConstructorNationalityTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("3");
		filtroBusca.setParametro("italian");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("italian",z.getConstructor().getNationality());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterConstructorNameTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("4");
		filtroBusca.setParametro("ferrari");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("ferrari",z.getConstructor().getName());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterRaceNameTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("5");
		filtroBusca.setParametro("Interlagos");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
					assertEquals("Interlagos",y.getRaceName());
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterPositionTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("6");
		filtroBusca.setParametro("1");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("1",z.getPosition());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterGridTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setTipoBusca("7");
		filtroBusca.setParametro("1");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("1",z.getGrid());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterMaiorPontosTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("8");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("10",z.getPoints());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterMenorPontosTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("9");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("10",z.getPoints());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterMaiorLapsTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("10");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("75",z.getLaps());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterMenorLapsTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("11");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("75",z.getLaps());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterMaiorVelocidadeTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("12");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("300",z.getFastestLap().getAverageSpeed().getSpeed());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterMenorVelocidadeTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("13");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("300",z.getFastestLap().getAverageSpeed().getSpeed());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterPilotoMaisVelhoTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("14");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("1980-03-23",z.getDriver().getDateOfBirth());
				});
			});
		});
	}
	
	@Test
	public void getDadosCorridaByFilterPilotoMaisNovoTest() throws Exception {
		FiltroBusca filtroBusca = new FiltroBusca();
		filtroBusca.setTemporadaInicial("2018");
		filtroBusca.setTemporadaFinal("2018");
		filtroBusca.setRodadaInicial("1");
		filtroBusca.setRodadaFinal("1");
		filtroBusca.setTipoBusca("15");
		List<RaceTable> r = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
		r.forEach(x -> {
			x.getRaces().forEach(y -> {
				y.getResults().forEach(z -> {
					assertEquals("1980-03-23",z.getDriver().getDateOfBirth());
				});
			});
		});
	}
	
	@Test
	public void getInfo() throws Exception {
		List<InfoFiltroBusca> infoFiltroBusca = dadosCorridaFacade.getInfo();
		assertNotNull(infoFiltroBusca);
	}
}
