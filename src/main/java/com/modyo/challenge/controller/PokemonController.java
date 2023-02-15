package com.modyo.challenge.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modyo.challenge.dto.PokemonAPIResourceList;
import com.modyo.challenge.dto.PokemonDetailedDTO;
import com.modyo.challenge.pokeapi.service.impl.PokeServiceImpl;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

	@Autowired
	private PokeServiceImpl pokeService;

	@GetMapping//("pages")
	public ResponseEntity<PokemonAPIResourceList> getPokemonsPageable(@RequestParam("offset") int offset,
			@RequestParam("limit") int limit) {

		if (limit == 0) {
			offset = 0;
			limit = 20;
		}

		PokemonAPIResourceList response = pokeService.getAllPokemon(offset, limit);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{idOrName}")
	@Cacheable(value = "pokemon", key = "#idOrName")
	public ResponseEntity<PokemonDetailedDTO> getByIdOrName(@PathVariable String idOrName) {
		PokemonDetailedDTO pokemon = pokeService.getDetailedDTOByURL(idOrName);
		return new ResponseEntity<>(pokemon, OK);
	}

}
