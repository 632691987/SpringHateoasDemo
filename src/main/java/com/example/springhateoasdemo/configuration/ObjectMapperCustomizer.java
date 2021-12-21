package com.example.springhateoasdemo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.PagedModel;

/**
 * 这个 ObjectMapper 是专门用来加载对 Hateos 的返回结果 customize 的
 * 但这个并不是必须的
 */
@Configuration
public class ObjectMapperCustomizer {

    @Bean(name = "objectMapper")
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(PagedModel.PageMetadata.class, PageMetadataMixIn.class);
        return objectMapper;
    }

}
