package com.modyo.challenge.dto;

import java.util.List;

import lombok.Data;

@Data
public class PokemonDetailedDTO extends PokemonDTO{
	
	private List<String> description;
	private List<String> evolutions;

}
