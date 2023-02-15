package com.modyo.challenge.dto;

import java.util.List;

import com.modyo.challenge.pokeapi.model.PokemonDetailed;

import lombok.Data;

@Data
public class PokemonAPIResourceList {
	
	private Integer count;
    private String next;
    private String previous;
    private List<PokemonDTO> pokemons;
//    private List<PokemonDetailed> pokemons;

}
