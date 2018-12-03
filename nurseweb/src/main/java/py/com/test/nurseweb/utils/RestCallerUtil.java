package py.com.test.nurseweb.utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import py.com.test.nurseweb.error.RestTemplateResponseErrorHandler;

import java.util.Collections;
import java.util.Map;

/**
 * @author mlopez
 * @fecha 11/30/18,2:21 PM
 */


public class RestCallerUtil {
    private static String addParamUrl(String url, Map<String, Object> parametros) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        if(parametros!=null){
            for(Map.Entry<String, Object> p : parametros.entrySet()){
                if((p.getValue()!=null)&&(p.getValue()!=""))
                    builder.queryParam(p.getKey(),p.getValue());
            }
        }
        return builder.build().toUriString();
    }

    public static <T> T doGet(String url, Class<T> responseType, Map<String, Object> parametros, HttpHeaders headers) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<T> response = restTemplate.exchange(addParamUrl(url,parametros), HttpMethod.GET, entity, responseType);

        if(response.getStatusCode().equals(HttpStatus.OK))
            return response.getBody();
        else
            return null;
    }

    public static <T, E> T doPost(String url, E requestObject, Class<T> responseType,HttpHeaders headers) {
        RestTemplate restTemplate = new RestTemplate();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<E> entity = new HttpEntity<>(requestObject, headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);

        return response.getBody();
    }

    public static <T, E> T doPut(String url, E requestObject, Class<T> responseType, HttpHeaders headers) {

        RestTemplate restTemplate = new RestTemplate();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<E> entity = new HttpEntity<>(requestObject, headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);

        return response.getBody();
    }

    public static <T> T doDelete(String url, Class<T> responseType,HttpHeaders headers) {

        RestTemplate restTemplate = new RestTemplate();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
        return response.getBody();
    }



}
