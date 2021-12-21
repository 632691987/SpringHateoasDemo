package com.example.springhateoasdemo.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 这是个 customize Hateo Metadata 的类，但不是必须的
 */
public abstract class PageMetadataMixIn {

    @JsonProperty("pageSize")
    abstract long getSize();

    @JsonProperty("totalElements")
    abstract long getTotalElements();

    @JsonProperty("totalPages")
    abstract long getTotalPages();

    @JsonProperty("currentPage")
    abstract long getNumber();

}
