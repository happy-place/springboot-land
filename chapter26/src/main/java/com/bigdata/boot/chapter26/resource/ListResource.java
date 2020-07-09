package com.bigdata.boot.chapter26.resource;

import com.bigdata.boot.chapter26.controller.ItemRestController;
import com.bigdata.boot.chapter26.model.List;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ListResource extends Resource {

    private final String name;

    public ListResource(List list) {
        super(list);
        this.name = list.getName();
        Long listId = list.getId();
        add(ControllerLinkBuilder.linkTo(methodOn(ItemRestController.class).readItems(listId)).withRel("items"));
    }

    public String getName() {
        return name;
    }
}
