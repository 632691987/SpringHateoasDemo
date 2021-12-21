package com.example.springhateoasdemo.rest.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public abstract class AbstractResourceAssembler<D, T extends RepresentationModel<T>> extends RepresentationModelAssemblerSupport<D, T> {

    private LinkRelationProvider relProvider;

    private EntityLinks entityLinks;

    public AbstractResourceAssembler(Class<?> controllerClass, Class<T> resourceType) {
        super(controllerClass, resourceType);
    }

    @Autowired
    public void setRelProvider(LinkRelationProvider relProvider) {
        this.relProvider = relProvider;
    }

    @Autowired
    public void setEntityLinks(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    public LinkRelationProvider getRelProvider() {
        return relProvider;
    }

    public EntityLinks getEntityLinks() {
        return entityLinks;
    }
}
