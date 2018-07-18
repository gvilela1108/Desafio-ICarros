package br.com.icarros.desafio.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.icarros.desafio.dto.FiltroBusca;
import br.com.icarros.desafio.dto.FiltroCarregaBase;
import br.com.icarros.desafio.dto.InfoFiltroBusca;
import br.com.icarros.desafio.facade.DadosCorridaFacade;
import br.com.icarros.desafio.model.RaceTable;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/")
public class DadosCorridaRest {

	@Autowired
	private DadosCorridaFacade dadosCorridaFacade;
	
	@ApiOperation(
			value="Realizar a busca específica de uma corrida", 
			notes="Essa operação tem como objetivo consultar os dados especificos de uma corrida de acordo com a temporada e rodada informados .")
	@GetMapping(value = "/getDadosCorrida" )
	public RaceTable getDadosCorrida (@RequestParam("temporada") int temporada, @RequestParam("rodada") String rodada ) throws Exception{
		return dadosCorridaFacade.getDadosCorrida(temporada, rodada);
	}
	
	@ApiOperation(
			value="Realizar a busca específica de uma corrida por filtros", 
			notes="Essa operação tem como objetivo consultar os dados especificos de uma corrida através do filtro informado - Filtros disponíveis no /getInfo")
	@PostMapping(value = "/getDadosCorridaByFilter")
	public List<RaceTable> getDadosCorridaByFilter (@Valid @RequestBody FiltroBusca filtroBusca, Errors errors) throws Exception {
		List<RaceTable> raceTable = new ArrayList<>();
		if (errors.hasErrors()) {
			RaceTable raceTableError = new RaceTable();
			raceTableError.setSeason(errors.getAllErrors().stream()
													 	  .map(x -> x.getDefaultMessage())
													 	  .collect(Collectors.joining(",")));
			
			raceTable.add(raceTableError);
		} else {
			raceTable = dadosCorridaFacade.getDadosCorridaByFilter(filtroBusca);
        }
		return raceTable;
	}
	
	@ApiOperation(
			value="Realizar o carregamento da base atraves de um determinado periodo", 
			notes="Essa operação tem como objetivo carregar a base de dados através do filtro definido com os dados vindos externamente do API JSON")
	@PostMapping(value = "/getDadosCarregaBase")
	public List<RaceTable> getDadosCarregaBase (@Valid @RequestBody FiltroCarregaBase filtroCarregaBase, Errors errors) throws Exception {
		List<RaceTable> raceTable = new ArrayList<>();
		if (errors.hasErrors()) {
			RaceTable raceTableError = new RaceTable();
			raceTableError.setSeason(errors.getAllErrors().stream()
													 	  .map(x -> x.getDefaultMessage())
													 	  .collect(Collectors.joining(",")));
			
			raceTable.add(raceTableError);
		} else {
			FiltroBusca filtroBusca = new FiltroBusca();
			filtroBusca.setTemporadaInicial(filtroCarregaBase.getTemporadaInicial());
			filtroBusca.setTemporadaFinal(filtroCarregaBase.getTemporadaFinal());
			filtroBusca.setRodadaInicial(filtroCarregaBase.getRodadaInicial());
			filtroBusca.setRodadaFinal(filtroCarregaBase.getRodadaFinal());
			
        	raceTable = dadosCorridaFacade.buscaDadosCorridaIntervalo(filtroBusca);
		}
		return raceTable;
	}
	
	@ApiOperation(
			value="Exibir os tipos de consultas que podem ser realizadas por filtro", 
			notes="Essa operação tem como objetivo exibir quais filtros podem ser utilizados na consulta por filtro")
	@GetMapping(value = "/getInfo")
	public List<InfoFiltroBusca> getInfo() throws Exception {
		return dadosCorridaFacade.getInfo();
	}
	
}
