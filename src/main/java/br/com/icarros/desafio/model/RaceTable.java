package br.com.icarros.desafio.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceTable {

	@Id
	private String id;
	
	@JsonProperty("season")
	private String season;
	
	@JsonProperty("round")
	private String round;
	
	@JsonProperty("Races")
	private ArrayList<Races> races;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public ArrayList<Races> getRaces() {
		return races;
	}

	public void setRaces(ArrayList<Races> races) {
		this.races = races;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
