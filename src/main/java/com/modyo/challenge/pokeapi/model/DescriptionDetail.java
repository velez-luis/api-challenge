package com.modyo.challenge.pokeapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DescriptionDetail {
	
	private String description;
	private NamedApiResource language;

}
