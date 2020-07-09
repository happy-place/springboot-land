package com.bigdata.boot.chapter26.controller;

import com.bigdata.boot.chapter26.exception.AccessDeniedException;
import com.bigdata.boot.chapter26.command.CreateListCommand;
import com.bigdata.boot.chapter26.command.UpdateListCommand;
import com.bigdata.boot.chapter26.model.List;
import com.bigdata.boot.chapter26.model.User;
import com.bigdata.boot.chapter26.resource.ListResource;
import com.bigdata.boot.chapter26.resource.ListResourceAssembler;
import com.bigdata.boot.chapter26.service.ListService;
import com.bigdata.boot.chapter26.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(List.class)
@RequestMapping("/lists")
public class ListRestController {

    @Autowired
    private ListService listService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ListResource> readLists(Principal principal) {
        String username = principal.getName();
        Link link = linkTo(ListRestController.class).withSelfRel();
        return new Resources<ListResource>(new ListResourceAssembler().toResources(listService.findByUserUsername(username)), link);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.GET)
    public ListResource readList(@PathVariable Long listId) {
        return new ListResourceAssembler().toResource(listService.findOne(listId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createList(Principal principal, @RequestBody CreateListCommand createListCommand) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(AccessDeniedException::new);
        List list = new List(createListCommand.getName(), user);
        list = listService.save(list);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(entityLinks.linkForSingleResource(List.class, list).toUri());
        return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.PUT)
    public ListResource updateList(@PathVariable Long listId, @RequestBody UpdateListCommand updateListCommand) {
        List list = listService.findOne(listId);
        list.setName(updateListCommand.getName());
        list = listService.save(list);
        return new ListResourceAssembler().toResource(list);
    }

    @RequestMapping(value = "/{listId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteList(@PathVariable Long listId) {
        listService.delete(listId);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
