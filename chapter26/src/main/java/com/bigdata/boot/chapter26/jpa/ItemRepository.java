package com.bigdata.boot.chapter26.jpa;

import com.bigdata.boot.chapter26.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Collection<Item> findByListId(Long id);
}
