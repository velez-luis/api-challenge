package com.modyo.challenge.dto;

import java.util.List;

import lombok.Data;

@Data
public class PokemonDTO {
	
	private Integer id;
	private String  name;
	private String photo;
	private	List<String> type;
	private	Integer	weight;
	private	List<String> abilities;
	private  String url;

}
