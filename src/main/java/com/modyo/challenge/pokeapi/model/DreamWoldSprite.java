package com.modyo.challenge.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DreamWoldSprite {

	@JsonProperty("front_default")
    private String frontDefault;
	@JsonProperty("front_female")
    private String frontFemale;

}
