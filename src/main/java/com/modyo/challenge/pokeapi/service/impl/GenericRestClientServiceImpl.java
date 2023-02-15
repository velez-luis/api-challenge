package com.modyo.challenge.pokeapi.service.impl;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import com.modyo.challenge.pokeapi.service.IGenericRestClientService;

public abstract class GenericRestClientServiceImpl implements IGenericRestClientService {

	private RestTemplate restClient;

	public GenericRestClientServiceImpl(RestTemplateBuilder restTemplateBuilder) {
	    this.restClient = restTemplateBuilder.build();
	  }

	@Override
	public RestTemplate getRestClient() {
		return this.restClient;
	}

}
