package br.com.icarros.desafio.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component 
public class Beans {

	@Bean
	@Scope("prototype")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
