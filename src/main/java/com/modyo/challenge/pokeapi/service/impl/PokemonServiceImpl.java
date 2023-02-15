package com.modyo.challenge.pokeapi.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.modyo.challenge.pokeapi.model.NamedAPIResourceList;
import com.modyo.challenge.pokeapi.model.Pokemon;
import com.modyo.challenge.pokeapi.model.PokemonDetailed;
import com.modyo.challenge.pokeapi.service.IPokemonService;

@Service
public class PokemonServiceImpl implements IPokemonService {

	@Autowired
	private RestTemplate restClient;

	@Value("${pokeapi-base-url}")
	private String baseURL;

	@Value("${pokemon-endpoint}")
	private String endPoint;

	@Override
	public Pokemon getByIdOrName(String idOrName) {

		System.out.println(Pokemon.class.getSimpleName().toLowerCase());
		Map<String, String> pathVars = new HashMap<String, String>();
		pathVars.put("idOrName", idOrName);
		Pokemon pokemon = restClient.getForObject(getFinalUrl(), Pokemon.class, pathVars);

		return pokemon;
	}

	@Override
	public List<Pokemon> getAllPokemon() {
		NamedAPIResourceList lista = restClient.getForObject("https://pokeapi.co/api/v2/pokemon/", NamedAPIResourceList.class);
		List<Pokemon> pokeList = new LinkedList<>();
		lista.getResults().forEach (obj -> { 
			Pokemon pokemon = getByURL(obj.getUrl());
			pokeList.add(pokemon);
		});
		
		return pokeList;
	}

	private String getFinalUrl() {
		return String.format("%s%s/{idOrName}", this.baseURL, this.endPoint);
	}

	@Override
	public Pokemon getByURL(String url) {
		Pokemon pokemon = restClient.getForObject(url, Pokemon.class);
		return pokemon;
	}

	@Override
	public PokemonDetailed getDetailedByURL(String url) {
		PokemonDetailed pokemon = restClient.getForObject(url, PokemonDetailed.class);
		return pokemon;
	}

}
