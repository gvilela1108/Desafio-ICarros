package br.com.icarros.desafio.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.icarros.desafio.dto.FiltroBusca;
import br.com.icarros.desafio.dto.FiltroCarregaBase;
import br.com.icarros.desafio.dto.InfoFiltroBusca;
import br.com.icarros.desafio.facade.DadosCorridaFacade;
import br.com.icarros.desafio.model.Driver;
import br.com.icarros.desafio.model.RaceTable;
import br.com.icarros.desafio.model.Races;
import br.com.icarros.desafio.model.Results;

@RunWith(SpringRunner.class)
@WebMvcTest(DadosCorridaRest.class)
public class DadosCorridaRestTest {
	
    @Autowired
    private MockMvc mvc;
 
    @MockBean
    private RestTemplate restTemplate;
    
    @MockBean
    private DadosCorridaFacade dadosCorridasFacade;
    
    
    @Test
    public void getDadosCorridaTest() throws Exception {
    	
        RaceTable raceTable = new RaceTable();
        raceTable.setId("testeController");
        
        given(dadosCorridasFacade.getDadosCorrida(2018, "1")).willReturn(raceTable);
        
        mvc.perform(get("/rest/getDadosCorrida?temporada=2018&rodada=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$.id", is("testeController")));
    }

    
    @Test
    public void getDadosCorridaByFilterTest() throws Exception {
        RaceTable raceTable = new RaceTable();

        
        raceTable.setId("testeController");
    	Races race = new Races();
    	Driver driver = new Driver();
    	Results result = new Results();
    	
    	ArrayList<Races> races = new ArrayList<>();
    	ArrayList<Results> results = new ArrayList<>();
		driver.setNationality("brazilian");
		result.setDriver(driver);
		results.add(result);
		race.setResults(results);
		races.add(race);
		raceTable.setRaces(races);
        List<RaceTable> lista = Arrays.asList(raceTable);
        FiltroBusca f = new FiltroBusca();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("1");
        f.setTipoBusca("0");
        f.setParametro("brazilian");

        given(dadosCorridasFacade.getDadosCorridaByFilter(any(FiltroBusca.class))).willReturn(lista);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(f );
        
        mvc.perform(post("/rest/getDadosCorridaByFilter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$.[0].id", is("testeController")));
        
    }
    
    @Test
    public void getDadosCarregaBaseTest() throws Exception {
        RaceTable raceTable = new RaceTable();

        
        raceTable.setId("testeController");
    	Races race = new Races();
    	Driver driver = new Driver();
    	Results result = new Results();
    	
    	ArrayList<Races> races = new ArrayList<>();
    	ArrayList<Results> results = new ArrayList<>();
		driver.setNationality("brazilian");
		result.setDriver(driver);
		results.add(result);
		race.setResults(results);
		races.add(race);
		raceTable.setRaces(races);
		
        List<RaceTable> lista = Arrays.asList(raceTable);
        FiltroCarregaBase f = new FiltroCarregaBase();
        f.setTemporadaInicial("2018");
        f.setTemporadaFinal("2018");
        f.setRodadaInicial("1");
        f.setRodadaFinal("1");


        given(dadosCorridasFacade.buscaDadosCorridaIntervalo(any(FiltroBusca.class))).willReturn(lista);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(f );
        
        mvc.perform(post("/rest/getDadosCarregaBase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$.[0].id", is("testeController")));
        
    }
    
    @Test
    public void getInfoTest() throws Exception {
    	
    	InfoFiltroBusca infoFiltroBusca = new InfoFiltroBusca();
    	infoFiltroBusca.setIdTipoBusca("testeController");
        List<InfoFiltroBusca> lista = Arrays.asList(infoFiltroBusca);
        given(dadosCorridasFacade.getInfo()).willReturn(lista);
        
        mvc.perform(get("/rest/getInfo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                	.andExpect(jsonPath("$[0].idTipoBusca", is("testeController")));
    }
}
