package com.modyo.challenge.pokeapi.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Description {
	
	List<DescriptionDetail> descriptions;

}
