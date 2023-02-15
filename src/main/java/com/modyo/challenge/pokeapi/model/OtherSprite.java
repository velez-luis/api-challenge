package com.modyo.challenge.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OtherSprite {
	
	@JsonProperty("dream_world")
	private DreamWoldSprite dreamWorld;
	@JsonProperty("official-artwork")
	private OfficialArtworkSprite officialArtword;

}
