package com.modyo.challenge.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OfficialArtworkSprite {
	
	@JsonProperty("front_default")
    private String frontDefault;
	@JsonProperty("front_shiny")
    private String frontShiny;

}
