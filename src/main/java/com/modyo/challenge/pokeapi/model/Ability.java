package com.modyo.challenge.pokeapi.model;

import lombok.Data;

@Data
public class Ability {
	
	private	NamedApiResource ability;
	private	boolean	isHidden;
	private	Integer	slot;

}
