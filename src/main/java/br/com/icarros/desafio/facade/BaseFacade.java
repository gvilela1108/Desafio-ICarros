package br.com.icarros.desafio.facade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class BaseFacade {

	@Autowired
	private RestTemplate restTemplate;
	
	protected Object getJson(String url, Class classe) throws Exception {
		return restTemplate.getForObject(url, classe);
	}

	protected int transformaStringToInt(String numero) throws Exception {
	
		int retorno = -1;
		
		try {
			retorno = Integer.valueOf(numero);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	protected Float transformaStringToFloat(String numero) throws Exception {
		Float retorno = null;
		try {
			retorno = Float.valueOf(numero);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	protected String validaInteiro(String nomeParametro, int value) {
		String retorno = null;
		if (value == -1) {
			retorno = nomeParametro+" deve conter valor numerico!";
		}
		return retorno;
	}
	
	protected LocalDate transformaStringToDate(String data) throws Exception {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			localDate = LocalDate.parse(data, formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localDate;
	}
}
