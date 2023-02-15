package com.modyo.challenge.pokeapi.service;

import org.springframework.web.client.RestTemplate;

public interface IGenericRestClientService {

	RestTemplate getRestClient();
	
	<T> T getObjectFromApi(String url, Class<T> responseType) ;
	
	<T> T getObjectCacheableFromApi(String baseUrl, String apiName, String id, Class<T> responseType) ;

}
