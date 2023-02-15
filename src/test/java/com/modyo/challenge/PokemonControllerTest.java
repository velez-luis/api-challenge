package com.modyo.challenge;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.modyo.challenge.dto.PokemonDetailedDTO;
import com.modyo.challenge.pokeapi.service.impl.PokeServiceImpl;

@WebMvcTest
public class PokemonControllerTest {

	@MockBean
	private PokeServiceImpl pokeService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetByIdOrName() throws Exception {

		String idOrName = "snorlax";
		PokemonDetailedDTO pokemonDetailedDTO = new PokemonDetailedDTO();
		pokemonDetailedDTO.setId(143);
		pokemonDetailedDTO.setName("snorlax");
		pokemonDetailedDTO.setPhoto(
				"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/143.svg");
		pokemonDetailedDTO.setType(Collections.singletonList("normal"));
		pokemonDetailedDTO.setWeight(4600);
		pokemonDetailedDTO.setAbilities(Arrays.asList("immunity", "thick-fat", "gluttony"));
		List<String> descriptions = new ArrayList<String>();
		descriptions.add("Siente mucha curiosidad por todo");
		descriptions.add("Le gusta hacer travesuras");
		descriptions.add("Es muy sagaz");
		descriptions.add("A menudo está en Babia");
		descriptions.add("Es muy tiquismiquis");
		descriptions.add("Se caracteriza por su cuerpo resistente");
		descriptions.add("Encaja bien los ataques");
		descriptions.add("Es muy persistente");
		descriptions.add("Se caracteriza por ser muy resistente");
		descriptions.add("Es muy perseverante");
		descriptions.add("Le encanta comer");
		descriptions.add("A menudo se duerme");
		descriptions.add("Duerme mucho");
		descriptions.add("Suele desordenar cosas");
		descriptions.add("Le gusta relajarse");
		descriptions.add("Se enorgullece de su fuerza");
		descriptions.add("Le gusta revolverse");
		descriptions.add("A veces se enfada");
		descriptions.add("Le gusta luchar");
		descriptions.add("Tiene mal genio");
		descriptions.add("Le gusta ir muy rápido");
		descriptions.add("Siempre tiene el oído alerta");
		descriptions.add("Es de carácter simple e impetuoso");
		descriptions.add("Le encanta hacer payasadas");
		descriptions.add("Huye rápido");
		descriptions.add("Se distingue por su gran fuerza de voluntad");
		descriptions.add("Es un poco petulante");
		descriptions.add("Es muy insolente");
		descriptions.add("Odia perder");
		descriptions.add("Es un poco cabezota");
		pokemonDetailedDTO.setDescription(descriptions);
		pokemonDetailedDTO.setEvolutions(Collections.singletonList("snorlax"));

		when(pokeService.getDetailedDTOByURL(anyString())
		).thenReturn(pokemonDetailedDTO);

		ResultActions response = mockMvc.perform(get("/pokemon/{id}", idOrName));

		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.id", is(pokemonDetailedDTO.getId())))
				.andExpect(jsonPath("$.name", is(pokemonDetailedDTO.getName())))
				.andExpect(jsonPath("$.photo", is(pokemonDetailedDTO.getPhoto())))
				.andExpect(jsonPath("$.type[0]", is(pokemonDetailedDTO.getType().get(0))))
				.andExpect(jsonPath("$.weight", is(pokemonDetailedDTO.getWeight())))
				.andExpect(jsonPath("$.abilities[0]", is(pokemonDetailedDTO.getAbilities().get(0))))
				.andExpect(jsonPath("$.description[0]", is(pokemonDetailedDTO.getDescription().get(0))))
				.andExpect(jsonPath("$.evolutions[0]", is(pokemonDetailedDTO.getEvolutions().get(0))));
	}

}
