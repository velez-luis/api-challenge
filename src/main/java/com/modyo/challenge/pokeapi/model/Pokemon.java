package com.modyo.challenge.pokeapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Pokemon {
	
	private Integer id;
	private String  name;
	private	List<Type> types;
	private	Integer	weight;
	private	List<Ability> abilities;
	private NamedApiResource species;
	@JsonProperty("base_experience")
	private	Integer	baseExperience;
	private	Integer	height;
    private Sprites sprites;
    private Evolution  evolutions;
    
}
