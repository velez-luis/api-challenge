package com.modyo.challenge.pokeapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvolutionChainDetailed {

	@JsonProperty("evolves_to")
	private List<EvolvesTo> evolvesTo;

}
