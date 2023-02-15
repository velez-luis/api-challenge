package com.modyo.challenge.pokeapi.service;

import java.util.List;

import com.modyo.challenge.pokeapi.model.Pokemon;
import com.modyo.challenge.pokeapi.model.PokemonDetailed;

public interface IPokemonService{
	
	public Pokemon getByIdOrName(String idOrName);
	
	public Pokemon getByURL(String url);
	
	public List<Pokemon> getAllPokemon();

	public PokemonDetailed getDetailedByURL(String url);

}
