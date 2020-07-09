package com.bigdata.boot.chapter26.aspect;

import com.bigdata.boot.chapter26.exception.EntityNotFoundException;
import com.bigdata.boot.chapter26.jpa.ItemRepository;
import com.bigdata.boot.chapter26.jpa.ListRepository;
import com.bigdata.boot.chapter26.model.Item;
import com.bigdata.boot.chapter26.model.List;
import com.bigdata.boot.chapter26.exception.AccessDeniedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EntityPermissionCheck {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Around("execution(public com.bigdata.boot.chapter26.model.List com.bigdata.boot.chapter26.service.ListService.*(..))")
    public Object checkQueryListAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.proceed();
        List list = (List) entity;
        checkListAccess(list);
        return list;
    }

    @Around("execution(public com.bigdata.boot.chapter26.model.Item com.bigdata.boot.chapter26.service.ItemService.*(..))")
    public Object checkQueryItemAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.proceed();
        Item item = (Item) entity;
        checkItemAccess(item);
        return item;
    }

    @Before("execution(public * com.bigdata.boot.chapter26.service.ListService.*(Long))")
    public void checkUpdateListAccess(JoinPoint joinPoint) {
        Long id = (Long)joinPoint.getArgs()[0];
        List list = listRepository.findOne(id);
        checkListAccess(list);
    }

    @Before("execution(public * com.bigdata.boot.chapter26.service.ItemService.*(Long))")
    public void checkUpdateItemAccess(JoinPoint joinPoint) {
        Long id = (Long)joinPoint.getArgs()[0];
        Item item = itemRepository.findOne(id);
        checkItemAccess(item);
    }

    private void checkListAccess(List list) {
        if (list == null) {
            throw new EntityNotFoundException();
        }
        else if (list.getUser() == null
                || list.getUser().getUsername() == null
                || !list.getUser().getUsername().equals(getCurrentUser())) {
            throw new AccessDeniedException();
        }
    }

    private void checkItemAccess(Item item) {
        if (item != null && item.getList() != null) {
            checkListAccess(item.getList());
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }
        return null;
    }
}
