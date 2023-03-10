package com.modyo.challenge.pokeapi.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.modyo.challenge.controller.PokemonController;
import com.modyo.challenge.dto.PokemonAPIResourceList;
import com.modyo.challenge.dto.PokemonDTO;
import com.modyo.challenge.dto.PokemonDetailedDTO;
import com.modyo.challenge.pokeapi.model.Ability;
import com.modyo.challenge.pokeapi.model.Description;
import com.modyo.challenge.pokeapi.model.EvolutionChain;
import com.modyo.challenge.pokeapi.model.EvolvesTo;
import com.modyo.challenge.pokeapi.model.NamedAPIResourceList;
import com.modyo.challenge.pokeapi.model.NamedApiResource;
import com.modyo.challenge.pokeapi.model.PokemonDetailed;
import com.modyo.challenge.pokeapi.model.Specie;
import com.modyo.challenge.pokeapi.model.StatDetail;
import com.modyo.challenge.pokeapi.model.Type;
import com.modyo.challenge.pokeapi.service.IPokeService;

@Service
public class PokeServiceImpl extends GenericRestClientServiceImpl implements IPokeService {

	@Value("${pokeapi-base-url}")
	private String baseURL;

	@Value("${pokemon-endpoint}")
	private String endPoint;

	@Autowired
	RestTemplate restTemplateCient;

	@Autowired
	@Qualifier("defaultMapper")
	private ModelMapper mapper;

	@Autowired
	@Qualifier("pokemonMapper")
	private ModelMapper pokemonMapper;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public RestTemplate getRestClient() {
		return this.restTemplateCient;
	}

	public PokeServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		super(restTemplateBuilder);
	}

	@Cacheable(value = "pokemons")
	public PokemonAPIResourceList getAllPokemon(int offSet, int limit) {
		
		String url = String.format("%s/%s?offset=%d&limit=%d", baseURL, endPoint, offSet, limit);
		
		NamedAPIResourceList pokeListFromApi = getObjectFromApi(url, NamedAPIResourceList.class);

		List<PokemonDTO> pokeList = pokeListFromApi.getResults().stream().map(obj -> {
			
			PokemonDetailed pokemonFromApi = getDetailedByURL(obj.getUrl());
			
			PokemonDTO pokemonDTO = pokemonMapper.map(pokemonFromApi, PokemonDTO.class);
			
			WebMvcLinkBuilder pokeUrl = linkTo(
					methodOn(PokemonController.class).getByIdOrName(pokemonDTO.getId().toString()));
			
			pokemonDTO.setUrl(pokeUrl.toString());
			return pokemonDTO;
		
		}).collect(Collectors.toList());

		WebMvcLinkBuilder next = linkTo(methodOn(PokemonController.class).getPokemonsPageable((offSet + limit), limit));
		
		WebMvcLinkBuilder previus = linkTo(methodOn(PokemonController.class)
				.getPokemonsPageable((offSet - limit > 0 ? offSet - limit : 0), limit));

		PokemonAPIResourceList pokeApiResourceList = mapper.map(pokeListFromApi, PokemonAPIResourceList.class);

		pokeApiResourceList.setNext(next.toString());
		pokeApiResourceList.setPrevious(previus.toString());
		pokeApiResourceList.setPokemons(pokeList);
		
		return pokeApiResourceList;
	}

	@Cacheable(value = "pokemon", key = "#url")
	public PokemonDetailed getDetailedByURL(String url) {
		
		PokemonDetailed pokemonFromApi = getObjectFromApi(url, PokemonDetailed.class);
		
		Specie specie = getObjectApiPreCache(pokemonFromApi.getSpecies().getUrl(), Specie.class);
		
		EvolutionChain chain = getObjectApiPreCache(specie.getEvolutionChain().getUrl(), EvolutionChain.class);

		List<NamedApiResource> characteristicList = new LinkedList<>();

		pokemonFromApi.getStats().parallelStream().map(t -> getObjectApiPreCache(t.getStat().getUrl(), StatDetail.class))
				.flatMap(statDetail -> statDetail.getCharacteristics().stream()).forEach(characteristicList::add);

		List<String> descriptionList = characteristicList.parallelStream()
				.map(t -> getObjectApiPreCache(t.getUrl(), Description.class))
				.flatMap(description -> description.getDescriptions().stream())
				.filter(ch -> ch.getLanguage().getName().equals(Locale.getDefault()))
				.map(ch -> ch.getDescription())
				.collect(Collectors.toList());

		pokemonFromApi.setDescripcion(descriptionList);
		pokemonFromApi.setChain(chain);
		
		return pokemonFromApi;
	}

	@Cacheable(value = "pokemon", key = "#idOrName")
	public PokemonDetailedDTO getDetailedDTOByURL(String idOrName) {

		PokemonDetailed pokemon = getObjectFromApi(getUrl() + idOrName, PokemonDetailed.class);
		
		PokemonDTO pokemonDTO = pokemonMapper.map(pokemon, PokemonDTO.class);

		WebMvcLinkBuilder link1 = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(PokemonController.class).getByIdOrName(idOrName));
		
		pokemonDTO.setUrl(link1.toString());

		PokemonDetailedDTO pokemonDetailedDTO = mapper.map(pokemonDTO, PokemonDetailedDTO.class);

		Specie specie = getObjectApiPreCache(pokemon.getSpecies().getUrl(), Specie.class);
		
		EvolutionChain evolutionChain = getObjectApiPreCache(specie.getEvolutionChain().getUrl(),
				EvolutionChain.class);

		List<NamedApiResource> characteristicList = new LinkedList<>();
		pokemon.getStats().stream().parallel().map(t -> getObjectApiPreCache(t.getStat().getUrl(), StatDetail.class))
				.flatMap(statDetail -> statDetail.getCharacteristics().stream()).forEach(characteristicList::add);

		List<String> evolutions = getEvolutions(evolutionChain.getChain().getEvolvesTo());
		
		List<String> descriptionList = getDescription(characteristicList);

		pokemonDetailedDTO.setDescription(descriptionList);
		pokemonDetailedDTO.setEvolutions(evolutions);
		
		WebMvcLinkBuilder pokeUrl = linkTo(
				methodOn(PokemonController.class).getByIdOrName(pokemonDTO.getId().toString()));
		
		pokemonDetailedDTO.setUrl(pokeUrl.toString());
		
		return pokemonDetailedDTO;
	}

	public void addCacheIfNotExist(String cacheName) {
		if (!cacheManager.getCacheNames().contains(cacheName)) {
			cacheManager.getCache(cacheName);
		}
	}

	public <T> T getObjectApiPreCache(String url, Class<T> responseType) {

		String parts[] = url.split("/");
		String id = parts[parts.length - 1].trim();
		String apiName = parts[parts.length - 2].trim();
		addCacheIfNotExist(apiName);
		return getObjectCacheableFromApi(baseURL, apiName, id, responseType);
	}

	@Override
	@Cacheable(value = "pokemon", key = "#url")
	public <T> T getObjectFromApi(String url, Class<T> responseType) {
		return getRestClient().getForObject(url, responseType);
	}

	@Override
	@Cacheable(value = "#apiName", key = "#id")
	public <T> T getObjectCacheableFromApi(String baseUrl, String apiName, String id, Class<T> responseType) {
		return getRestClient().getForObject(baseUrl + apiName + "/" + id, responseType);
	}

	private String getUrl() {
		return String.format("%s%s", baseURL, endPoint);
	}

	private List<String> getEvolutions(List<EvolvesTo> evolvesTo) {
		return evolvesTo != null
				? evolvesTo.stream().map(evolution -> evolution.getSpecies().getName()).collect(Collectors.toList())
				: Collections.emptyList();
	}

	private List<String> getDescription(List<NamedApiResource> characteristicList) {
		return characteristicList.stream().parallel().map(t -> getObjectApiPreCache(t.getUrl(), Description.class))
				.flatMap(description -> description.getDescriptions().stream())
				.filter(ch -> ch.getLanguage().getName().equals(Locale.getDefault().getLanguage())).map(ch -> ch.getDescription())
				.collect(Collectors.toList());
	}
}
