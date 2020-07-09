package com.bigdata.boot.chapter26.resource;

import com.bigdata.boot.chapter26.controller.ListRestController;
import com.bigdata.boot.chapter26.model.List;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ListResourceAssembler extends ResourceAssemblerSupport<List, ListResource> {

    public ListResourceAssembler() {
        super(ListRestController.class, ListResource.class);
    }

    @Override
    public ListResource toResource(List list) {
        ListResource resource = createResourceWithId(list.getId(), list);
        return resource;
    }

    @Override
    protected ListResource instantiateResource(List entity) {
        return new ListResource(entity);
    }
}
