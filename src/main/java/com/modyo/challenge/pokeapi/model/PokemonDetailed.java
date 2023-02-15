package com.modyo.challenge.pokeapi.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PokemonDetailed {
	
	private Integer id;
	private String  name;
	private	List<Type> types;
	private	Integer	weight;
	private	List<Ability> abilities;
	private NamedApiResource species;
	private	Integer	base_experience;
	private	Integer	height;
    private Sprites sprites;
    private EvolutionChainURL evolution_chain;
    private EvolutionChain chain;
    private List<Stat> stats;
    private List<String> descripcion;
//    private List<Characteristic>

}
