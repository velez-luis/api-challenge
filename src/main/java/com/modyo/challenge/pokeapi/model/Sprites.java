package com.modyo.challenge.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Sprites {	
	
	@JsonProperty("front_default")
    private String frontDefault;
	@JsonProperty("front_shiny")
    private String frontShiny;
	@JsonProperty("front_female")
    private String frontFemale;
	@JsonProperty("front_shiny_female")
    private String frontShinyFemale;
	@JsonProperty("back_default")
    private String backDefault;
	@JsonProperty("back_shiny")
    private String backShiny;
	@JsonProperty("back_female")
    private String backFemale;
	@JsonProperty("back_shiny_female")
    private String backShinyFemale;
    private OtherSprite other;

}
