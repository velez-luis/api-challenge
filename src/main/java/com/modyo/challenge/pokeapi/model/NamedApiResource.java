package com.modyo.challenge.pokeapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class NamedApiResource {
	
	String name;
	String url;

}
