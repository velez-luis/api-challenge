package com.modyo.challenge.config.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
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

		typeMap.addMappings(mapping -> {
			
            mapping.using(new AbstractConverter<List<Type>, List<String>>() {
                @Override
                protected List<String> convert(List<Type> types) {
                    return types.parallelStream()
                            .map(type -> type.getType().getName())
                            .collect(Collectors.toList());
                }
            }).map(PokemonDetailed::getTypes, PokemonDTO::setType);
            
            mapping.using(new AbstractConverter<List<Ability>, List<String>>() {
                @Override
                protected List<String> convert(List<Ability> abilities) {
                    return abilities.parallelStream()
                            .map(ability -> ability.getAbility().getName())
                            .collect(Collectors.toList());
                }
            }).map(PokemonDetailed::getAbilities, PokemonDTO::setAbilities);
            
            
        });
		return mapper;
	}
}
