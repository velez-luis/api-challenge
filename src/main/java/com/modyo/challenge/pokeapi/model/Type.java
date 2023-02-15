package com.modyo.challenge.pokeapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Type {
	
	private	NamedApiResource type;
	private	Integer	slot;

}
