package br.com.icarros.desafio;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.icarros.desafio.dto.FiltroBusca;
import br.com.icarros.desafio.dto.FiltroCarregaBase;
import br.com.icarros.desafio.dto.InfoFiltroBusca;
import br.com.icarros.desafio.model.RaceTable;
import br.com.icarros.desafio.repository.RaceTableRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DesafioApplicationTest {

    private RestTemplate restTemplate;  

    final String BASE_PATH = "http://localhost:9090/desafio-icarros/rest";
     
    @Before
    public void testInit() throws Exception {  
        restTemplate = new RestTemplate();
    }
     
    @Test
    public void getDadosCorridaTest() throws JsonProcessingException{
        RaceTable raceTable = restTemplate.getForObject(BASE_PATH + "/getDadosCorrida?temporada=2018&rodada=1", RaceTable.class);
        Assert.assertNotNull(raceTable);
    }
     
    @Test
    public void getDadosCorridaByFilterTestNacionalidadePiloto() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("1");
        f.setTipoBusca("0");
        f.setParametro("brasilian");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterTestGivenNamePiloto() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2016");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("5");
        f.setTipoBusca("1");
        f.setParametro("Lewis");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterDriverFamilyNameTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2008");
        f.setTemporadaFinal("2015");
        f.setRodadaInicial("4");
        f.setRodadaFinal("4");
        f.setTipoBusca("2");
        f.setParametro("Massa");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterConstructorNationalityTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2016");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("15");
        f.setTipoBusca("3");
        f.setParametro("italian");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterConstructorNameTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("1");
        f.setTipoBusca("4");
        f.setParametro("ferrari");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterRaceNameTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2014");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("20");
        f.setTipoBusca("5");
        f.setParametro("British Grand Prix");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterPositionTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("5");
        f.setTipoBusca("6");
        f.setParametro("1");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterGridTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("6");
        f.setTipoBusca("7");
        f.setParametro("10");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterMaiorPontosTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2017");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("10");
        f.setTipoBusca("8");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterMenorPontosTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2017");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("10");
        f.setTipoBusca("9");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterMaiorLapsTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2017");
        f.setTemporadaFinal("2017");
        f.setRodadaInicial("1");
        f.setRodadaFinal("20");
        f.setTipoBusca("10");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterMenorLapsTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2017");
        f.setTemporadaFinal("2017");
        f.setRodadaInicial("1");
        f.setRodadaFinal("20");
        f.setTipoBusca("11");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterMaiorVelocidadeTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("6");
        f.setTipoBusca("12");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterMenorVelocidadeTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("6");
        f.setTipoBusca("13");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterPilotoMaisVelhoTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("5");
        f.setTipoBusca("14");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getDadosCorridaByFilterPilotoMaisNovoTest() throws JsonProcessingException{
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2016");
        f.setRodadaInicial("6");
        f.setTipoBusca("15");
        f.setParametro("");
    	
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCorridaByFilter",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    
    @Test
    public void getDadosCarregaBaseTest() throws JsonProcessingException{
        FiltroCarregaBase f = new FiltroCarregaBase();
        f.setTemporadaInicial("2000");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("20");
        
    	List<RaceTable> raceTable = restTemplate.postForObject(BASE_PATH + "/getDadosCarregaBase",f, List.class);
        Assert.assertNotNull(raceTable);
    }
    
    @Test
    public void getInfoTest() throws JsonProcessingException{
    	List<InfoFiltroBusca> infoFiltroBusca = restTemplate.getForObject(BASE_PATH + "/getInfo",List.class);
        Assert.assertNotNull(infoFiltroBusca);
    }
}
