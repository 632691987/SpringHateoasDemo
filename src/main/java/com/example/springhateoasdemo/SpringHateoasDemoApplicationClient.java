package com.example.springhateoasdemo;

import com.example.springhateoasdemo.configuration.PageMetadataMixIn;
import com.example.springhateoasdemo.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

public class SpringHateoasDemoApplicationClient {

    public static void main(String[] args) {
        SpringHateoasDemoApplicationClient client = new SpringHateoasDemoApplicationClient();
        PagedModel<User> pagedModel = client.searchUsers();
        List<User> users = pagedModel.getContent().stream().collect(Collectors.toList());
        users.forEach(user -> {
            System.out.println(user);
        });
    }

    public PagedModel<User> searchUsers() {
        final String url = "http://localhost:8080/users/search";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(HAL_JSON);

            final HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<PagedModel<User>> responseEntity = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PagedModel<User>>() {});
            return responseEntity.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return PagedModel.of(Collections.emptyList(), new PagedModel.PageMetadata(0,0,0));
        }
    }

    public static RestTemplate getRestTemplate() {
        final int CONNECTION_TIME_OUT = 2000;
        final int READ_TIMEOUT_DEFAULT = 10000;
        final int MAX_CONN_TOTAL_DEFAULT = 200;
        final int MAX_CONN_PER_ROUNTE_DEFAULT = 100;

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(MAX_CONN_TOTAL_DEFAULT)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUNTE_DEFAULT)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(READ_TIMEOUT_DEFAULT);
        factory.setConnectTimeout(CONNECTION_TIME_OUT);

        RestTemplate restTemplate = new RestTemplate(factory);
        return getRestTemplateWithHalMessageConverter(restTemplate);
    }

    private static RestTemplate getRestTemplateWithHalMessageConverter(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> existingConverters = restTemplate.getMessageConverters();
        List<HttpMessageConverter<?>> newConverters = new ArrayList<>();
        newConverters.add(getHalMessageConverter());
        newConverters.addAll(existingConverters);
        restTemplate.setMessageConverters(newConverters);
        return restTemplate;
    }

    private static HttpMessageConverter getHalMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(new JavaTimeModule(), new Jackson2HalModule());

        // 这不是最重点的，如果程序用了上面的 PageMetadataMixIn.class, 这里就必须一样要加
        objectMapper.addMixIn(PagedModel.PageMetadata.class, PageMetadataMixIn.class);

        MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(RepresentationModel.class);
        halConverter.setSupportedMediaTypes(Collections.singletonList(HAL_JSON));
        halConverter.setObjectMapper(objectMapper);
        return halConverter;
    }

}
