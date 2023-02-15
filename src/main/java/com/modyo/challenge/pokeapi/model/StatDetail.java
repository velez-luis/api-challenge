package com.modyo.challenge.pokeapi.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatDetail {
	
	List<NamedApiResource> characteristics;

}
