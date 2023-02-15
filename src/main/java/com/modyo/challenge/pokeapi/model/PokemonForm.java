package com.modyo.challenge.pokeapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PokemonForm {
	
	private	Integer	id;
	private	String	name;
	private	Integer	order;
	private	Integer	formOrder;
	private	Boolean	isDefault;
	private	Boolean	isBattleOnly;
	private	Boolean	isMega;
	private	String	formName;

}
