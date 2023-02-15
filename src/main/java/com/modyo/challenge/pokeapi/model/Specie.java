package com.modyo.challenge.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Specie {
	
	@JsonProperty("evolution_chain")
	private EvolutionChainURL evolutionChain;

}
