package com.modyo.challenge.pokeapi.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class NamedAPIResourceList {
	
	private Integer count;
    private String next;
    private String previous;
    private List<NamedApiResource> results;

}
