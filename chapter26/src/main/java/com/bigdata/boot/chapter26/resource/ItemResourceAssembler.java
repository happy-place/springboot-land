package com.bigdata.boot.chapter26.resource;

import com.bigdata.boot.chapter26.controller.ItemRestController;
import com.bigdata.boot.chapter26.model.Item;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ItemResourceAssembler extends ResourceAssemblerSupport<Item, ItemResource> {

    public ItemResourceAssembler() {
        super(ItemRestController.class, ItemResource.class);
    }

    @Override
    public ItemResource toResource(Item item) {
        return createResourceWithId(item.getId(), item, item.getList().getId());
    }

    @Override
    protected ItemResource instantiateResource(Item entity) {
        return new ItemResource(entity);
    }
}
