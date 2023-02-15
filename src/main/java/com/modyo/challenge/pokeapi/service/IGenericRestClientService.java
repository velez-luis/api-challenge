package com.modyo.challenge.pokeapi.service;

import org.springframework.web.client.RestTemplate;

public interface IGenericRestClientService {

	RestTemplate getRestClient();
	
	default <T> T get(String url, Class<T> responseType) {
		return getRestClient().getForObject(url, responseType);
	}
	
	<T> T getObjectFromApi(String url, Class<T> responseType) ;
	
	<T> T getObjectCacheableFromApi(String baseUrl, String apiName, String id, Class<T> responseType) ;

	default <T> T post(String url, Object request, Class<T> responseType) {
		return getRestClient().postForObject(url, request, responseType);
	}

	default <T> T put(String url, Object request, Class<T> responseType) {
		getRestClient().put(url, request, responseType);
		return getRestClient().getForObject(url, responseType);
	}

	default void delete(String url) {
		getRestClient().delete(url);
	}

}
