package com.modyo.challenge.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({"url"})
public class PokemonDetailedDTO extends PokemonDTO{
	
	private List<String> description;
	private List<String> evolutions;

}
