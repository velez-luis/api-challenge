package com.modyo.challenge.config.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.modyo.challenge.dto.PokemonDTO;
import com.modyo.challenge.pokeapi.model.Ability;
import com.modyo.challenge.pokeapi.model.PokemonDetailed;
import com.modyo.challenge.pokeapi.model.Type;

@Configuration
public class MapperConfig {

	@Bean("defaultMapper")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean("pokemonMapper")
	public ModelMapper pokemonMapper() {
		ModelMapper mapper = new ModelMapper();
		TypeMap<PokemonDetailed, PokemonDTO> typeMap = mapper.createTypeMap(PokemonDetailed.class, PokemonDTO.class);
		typeMap.addMapping(src -> (src.getSprites().getOther().getDreamWorld().getFrontDefault()),
				(dest, photo) -> dest.setPhoto((String) photo));

		typeMap.addMapping(src -> src.getTypes() != null
				? src.getTypes().stream().map(type -> type.getType().getName()).collect(Collectors.toList())
				: Collections.emptyList(), (dest, type) -> dest.setType((List<String>) type));

		typeMap.addMapping(src -> src.getAbilities() != null ? src.getAbilities().stream()
//				.map(ability -> ability.getAbility().getName()  )
				.flatMap(ability -> src.getAbilities().stream())
				.map(ability -> ability.getAbility().getName()  )
				.collect(Collectors.toList()) : Collections.emptyList(),
				(dest, value) -> dest.setAbilities((List<String>) value));

//		value !=null ? (  (Collection<Ability>) value).stream() 
//		.map(a -> a.getAbility().getName()).
//		collect(Collectors.toList()) : Collections.emptyList()
//		));

		return mapper;
	}
}
