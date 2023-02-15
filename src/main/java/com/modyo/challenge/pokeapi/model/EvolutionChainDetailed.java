package com.modyo.challenge.pokeapi.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvolutionChainDetailed {
	
	private List<EvolvesTo> evolves_to;

}
