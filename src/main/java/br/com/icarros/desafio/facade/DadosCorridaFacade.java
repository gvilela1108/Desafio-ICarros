package br.com.icarros.desafio.facade;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.icarros.desafio.cnt.Filtros;
import br.com.icarros.desafio.dto.FiltroBusca;
import br.com.icarros.desafio.dto.InfoFiltroBusca;
import br.com.icarros.desafio.model.DadosCorridaJson;
import br.com.icarros.desafio.model.RaceTable;
import br.com.icarros.desafio.model.Races;
import br.com.icarros.desafio.model.Results;
import br.com.icarros.desafio.repository.RaceTableRepository;

@Service
public class DadosCorridaFacade extends BaseFacade {

	@Autowired
	private RaceTableRepository raceTableRepository;
	
	@Value("${urlJsonUltimasCorridas}")
	private String urlJsonUltimasCorridas;
	
	public RaceTable getDadosCorrida(int temporada, String rodada) throws Exception{
		
		LocalDate data = LocalDate.now();
		int anoAtual = data.getYear();
		
		RaceTable raceTable = new RaceTable();
		
		try {
			
			//Valida parametro
			if (temporada > anoAtual) {
				raceTable.setSeason("Temporada: "+temporada+" informada superior ao ano corrente!");
			} else {
				//Verifica se a corrida e rodada ja possuem registros gravados
				RaceTable existeCorrida = existeCorridaBanco(Integer.toString(temporada), rodada);
				
				if (existeCorrida != null) {
					raceTable = existeCorrida;
				} else { //Realiza busca via json
					
					//Caso algum erro de HTTP ou dados nulos pre carrega a variavel de retorno;
					raceTable.setSeason("Temporada: "+ temporada+" Rodada: "+rodada+ " - Dados não encontrados!");
					
					String url = String.format(urlJsonUltimasCorridas, Integer.toString(temporada), rodada);
					DadosCorridaJson dadosCorridaJson = (DadosCorridaJson) getJson(url,DadosCorridaJson.class);
					
					if	(dadosCorridaJson.getMrData() != null) {
						raceTable = dadosCorridaJson.getMrData().getRaceTable();
						
						/*Grava no banco -- Verifica se o registro não existe, casos de parametro rodada passado como last, onde não 
						 * é gravado last no bd. 
						 */
						RaceTable raceTableMongo = existeCorridaBanco(raceTable.getSeason(), raceTable.getRound());
						
						if (raceTableMongo != null) {
							raceTable.setId(raceTableMongo.getId());
						}
						
						raceTableRepository.save(raceTable);

					}	
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return raceTable;
	}
	
	public RaceTable existeCorridaBanco(String temporada, String rodada) {
		return raceTableRepository.findBySeasonAndRound(temporada, rodada);
	}
	
	public List<RaceTable> getDadosCorridaByFilter(FiltroBusca filtroBusca) throws Exception {
		
		List<RaceTable> intervaloBusca = buscaDadosCorridaIntervalo(filtroBusca);
		if (intervaloBusca != null) {
			if (intervaloBusca.get(0).getRaces() == null) {
				return intervaloBusca; //Ocorreu erro ao buscar o intervalo de corridas
			}
		}
		
		List<RaceTable> dadosFiltrados = aplicaFiltro(filtroBusca, intervaloBusca);

		return dadosFiltrados;
	}
	
	public List<RaceTable> buscaDadosCorridaIntervalo(FiltroBusca filtroBusca) throws Exception {
		List<RaceTable> list = new ArrayList<>();;
		RaceTable raceTableErro = new RaceTable();
		
		int temporadaInicial = transformaStringToInt(filtroBusca.getTemporadaInicial());
		int temporadaFinal;
		if (filtroBusca.getTemporadaFinal() != null) {
			temporadaFinal = transformaStringToInt(filtroBusca.getTemporadaFinal()); 
		} else {
			temporadaFinal = temporadaInicial;
		}

		if (temporadaFinal < temporadaInicial) {
			raceTableErro.setSeason("Temporada Inicial superior a Temporada Final");
			list.add(raceTableErro);
			return list;
		}
		
		String erroValidaRodada = "";
		int rodadaInicial = transformaStringToInt(filtroBusca.getRodadaInicial());
		erroValidaRodada = validaInteiro("rodadaInicial",rodadaInicial);
		
		if (erroValidaRodada  != null) {
			raceTableErro.setSeason(erroValidaRodada);
			list.add(raceTableErro);
			return list;
		}

		int rodadaFinal;
		if (filtroBusca.getRodadaFinal() != null) {
			
			rodadaFinal = transformaStringToInt(filtroBusca.getRodadaFinal());
			erroValidaRodada  = validaInteiro("rodadaFinal",rodadaFinal);
			
			if (erroValidaRodada  != null) {
				raceTableErro.setSeason(erroValidaRodada);
				list.add(raceTableErro);
				return list;
			}
		} else {
			rodadaFinal = rodadaInicial;
		}

		if (rodadaFinal < rodadaInicial) {
			raceTableErro.setSeason("Rodada Inicial superior a Rodada Final");
			list.add(raceTableErro);
			return list;
		}
		
		for (int c = temporadaInicial; c <= temporadaFinal; c++) {
			for (int d = rodadaInicial; d <= rodadaFinal; d++) {
				RaceTable raceTable = getDadosCorrida(c,String.valueOf(d));
				list.add(raceTable);
			}
		}
		
		return list;
	}
	
	public List<RaceTable> aplicaFiltro(FiltroBusca filtroBusca, List<RaceTable> intervaloBusca) throws Exception{
		Filtros f = null;
		List<RaceTable> listRaceTableErro = new ArrayList<>();
		RaceTable raceTableErro = new RaceTable(); 
		int tipoBusca = transformaStringToInt(filtroBusca.getTipoBusca());
		String erroTipoBusca = validaInteiro("tipoBusca",tipoBusca);
		
		if (erroTipoBusca  != null) {
			raceTableErro.setSeason(erroTipoBusca);
			listRaceTableErro.add(raceTableErro);
			return listRaceTableErro;
		} 
		List<Integer> list;
		List<Float> listFloat;
		List<LocalDate> listDate;
		f = Filtros.getFiltrosByCode(Integer.valueOf(filtroBusca.getTipoBusca()));
		Predicate<Results> predicateResults;
		Predicate<Races> predicateRaces;
		String velocidade;
		DecimalFormat df = new DecimalFormat("#.000");
		String dataNascimento;
		switch(f) {
			case FILTRO_NACIONALIDADE_PILOTO:
				predicateResults = x-> x.getDriver().getNationality().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);	
				break;
			
			case FILTRO_NOME_PILOTO:
				predicateResults = x-> x.getDriver().getGivenName().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);				
				break;
				
			case FILTRO_SOBRENOME_PILOTO:
				predicateResults = x-> x.getDriver().getFamilyName().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);				
				break;
				
			case FILTRO_NACIONALIDADE_CONSTRUTOR:
				predicateResults = x-> x.getConstructor().getNationality().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);				
				break;
				
			case FILTRO_NOME_CONSTRUTOR:
				predicateResults = x-> x.getConstructor().getName().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);				
				break;
				
			case FILTRO_NOME_CORRIDA:
				predicateRaces = x-> x.getRaceName().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarRaces(predicateRaces, intervaloBusca);				
				break;
				
			case FILTRO_POSICAO:
				predicateResults = x-> x.getPosition().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults,intervaloBusca);
				break;
				
			case FILTRO_GRID:
				predicateResults = x-> x.getGrid().equalsIgnoreCase(filtroBusca.getParametro());
				intervaloBusca = filtrarResults(predicateResults,intervaloBusca);
				break;			
				
			case FILTRO_MAIOR_PONTUACAO:
				list = convertValueFromStringToInt(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));
				predicateResults = x-> x.getPoints().equalsIgnoreCase(list.stream().max(Integer::compare).get().toString());
				intervaloBusca = filtrarResults(predicateResults,intervaloBusca);
				break;		

			case FILTRO_MENOR_PONTUACAO:
				list = convertValueFromStringToInt(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));
				predicateResults = x-> x.getPoints().equalsIgnoreCase(list.stream().min(Integer::compare).get().toString());
				intervaloBusca = filtrarResults(predicateResults,intervaloBusca);
				break;					
				
			case FILTRO_MAIOR_VOLTAS:
				list = convertValueFromStringToInt(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));
				predicateResults = x-> x.getLaps().equalsIgnoreCase(list.stream().max(Integer::compare).get().toString());
				intervaloBusca = filtrarResults(predicateResults,intervaloBusca);
				break;
				
			case FILTRO_MENOR_VOLTAS:
				list = convertValueFromStringToInt(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));
				predicateResults = x-> x.getLaps().equalsIgnoreCase(list.stream().min(Integer::compare).get().toString());
				intervaloBusca = filtrarResults(predicateResults,intervaloBusca);
				break;
				
			case FILTRO_MAIOR_VELOCIDADE:
				listFloat = convertValueFromStringToFloat(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));	
				velocidade = df.format(listFloat.stream().max(Float::compare).get()).replace(",", ".");
				intervaloBusca = filtrarVelocidade(velocidade,intervaloBusca);
				break;
				
			case FILTRO_MENOR_VELOCIDADE:
				listFloat = convertValueFromStringToFloat(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));	
				velocidade = df.format(listFloat.stream().min(Float::compare).get()).replace(",", ".");
				intervaloBusca = filtrarVelocidade(velocidade,intervaloBusca);
				break;
				
			case FILTRO_PILOTO_MAIS_VELHO:
				listDate = convertValueFromStringToDate(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));
				dataNascimento = listDate.stream().min(Comparator.comparing(LocalDate::toEpochDay)).get().toString();
				predicateResults = x-> x.getDriver().getDateOfBirth().equalsIgnoreCase(dataNascimento);
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);	
				break;
				
			case FILTRO_PILOTO_MAIS_NOVO:
				listDate = convertValueFromStringToDate(intervaloBusca,transformaStringToInt(filtroBusca.getTipoBusca()));
				dataNascimento = listDate.stream().max(Comparator.comparing(LocalDate::toEpochDay)).get().toString();
				predicateResults = x-> x.getDriver().getDateOfBirth().equalsIgnoreCase(dataNascimento);
				intervaloBusca = filtrarResults(predicateResults, intervaloBusca);
				break;
		}

		
		return intervaloBusca;
	}
	
	public List<RaceTable> filtrarResults(Predicate<Results> predicate,  List<RaceTable> intervaloBusca) {
		intervaloBusca.forEach(races -> {
			races.getRaces().forEach(results -> {
				ArrayList<Results> r = results.getResults().stream()
									.filter(predicate)
									.collect(Collectors.toCollection(ArrayList::new));
				results.setResults(r);
			});
		});
		
		return intervaloBusca;
	}

	public List<RaceTable> filtrarVelocidade(String valor,  List<RaceTable> intervaloBusca) {
		intervaloBusca.forEach(races -> {
			races.getRaces().forEach(results -> {
				ArrayList<Results> r = results.getResults().stream()
									.filter( x -> x.getFastestLap() != null)
									.filter( x -> x.getFastestLap().getAverageSpeed() != null)
									.filter( x -> x.getFastestLap().getAverageSpeed().getSpeed() != null)
									.filter( x -> x.getFastestLap().getAverageSpeed().getSpeed().equalsIgnoreCase(valor) )
									.collect(Collectors.toCollection(ArrayList::new));
				results.setResults(r);
			});
		});
		
		return intervaloBusca;
	}
	public List<RaceTable> filtrarRaces(Predicate<Races> predicate,  List<RaceTable> intervaloBusca) {
		intervaloBusca.forEach(races -> {
			ArrayList<Races> r = races.getRaces().stream() 					
									.filter(predicate)
									.collect(Collectors.toCollection(ArrayList::new));
			races.setRaces(r);
			
		});
		
		return intervaloBusca;
	}
	public List<LocalDate> convertValueFromStringToDate(List<RaceTable> intervaloBusca, int tipoBusca) {
		List<LocalDate> lista = new ArrayList<>();
		Filtros f = Filtros.getFiltrosByCode(tipoBusca);
		intervaloBusca.forEach(races -> {
			races.getRaces().forEach(results -> {						
				results.getResults().forEach( r ->  {
					try {
						
						LocalDate valor = null;
						switch(f) {
							case FILTRO_PILOTO_MAIS_VELHO:
							case FILTRO_PILOTO_MAIS_NOVO:
								valor = transformaStringToDate(r.getDriver().getDateOfBirth());

						}
						lista.add(valor);
					} catch (Exception e ) {
						e.printStackTrace();
					}
				});
			});
		});	
		lista.removeIf(Objects::isNull);
		return lista;
	}
	
	public List<Float> convertValueFromStringToFloat(List<RaceTable> intervaloBusca, int tipoBusca) {
		List<Float> lista = new ArrayList<>();
		Filtros f = Filtros.getFiltrosByCode(tipoBusca);
		intervaloBusca.forEach(races -> {
			races.getRaces().forEach(results -> {						
				results.getResults().forEach( r ->  {
					try {
						
						Float valor = null;
						switch(f) {
							case FILTRO_MAIOR_VELOCIDADE:
							case FILTRO_MENOR_VELOCIDADE:
								if (r.getFastestLap() != null) {
									if (r.getFastestLap().getAverageSpeed() != null) {
										if (r.getFastestLap().getAverageSpeed().getSpeed() != null) {
											valor = transformaStringToFloat(r.getFastestLap().getAverageSpeed().getSpeed());
										}
									}
								}
						}
						lista.add(valor);
					} catch (Exception e ) {
						e.printStackTrace();
					}
				});
			});
		});	
		lista.removeIf(Objects::isNull);
		return lista;
	}
	
	public List<Integer> convertValueFromStringToInt(List<RaceTable> intervaloBusca, int tipoBusca) {
		List<Integer> lista = new ArrayList<>();
		Filtros f = Filtros.getFiltrosByCode(tipoBusca);
		intervaloBusca.forEach(races -> {
			races.getRaces().forEach(results -> {						
				results.getResults().forEach( r ->  {
					try {
						
						int valor = -1;
						switch(f) {
							case FILTRO_MAIOR_PONTUACAO:
							case FILTRO_MENOR_PONTUACAO:
								valor = transformaStringToInt(r.getPoints());
								break;
								
							case FILTRO_MAIOR_VOLTAS:
							case FILTRO_MENOR_VOLTAS:
								valor = transformaStringToInt(r.getLaps());
								break;

						}
						lista.add(valor);
					} catch (Exception e ) {
						e.printStackTrace();
					}
				});
			});
		});	
		return lista;
	}

	public List<InfoFiltroBusca> getInfo() {
		List<InfoFiltroBusca> retorno = new ArrayList<>();
		List<Filtros> list = new ArrayList<Filtros>(EnumSet.allOf(Filtros.class));
		InfoFiltroBusca info = new InfoFiltroBusca();
		info.setIdTipoBusca("Código dos Parametros para realizar o filtro");
		info.setDescricao("Descrição dos Parametros para realizar o filtro");
		retorno.add(info);
		
		list.forEach(x -> {
			
			if (x.getCode() > 0 ) { //Não exibe os códigos negativos (Erros internos)
				InfoFiltroBusca infoFiltros = new InfoFiltroBusca();
				infoFiltros.setIdTipoBusca(Integer.toString(x.getCode()));
				infoFiltros.setDescricao(x.getLabel());
				retorno.add(infoFiltros);	
			}

		});
		
		return retorno;
	}
}
