package com.bigdata.boot.chapter26.jpa;

import com.bigdata.boot.chapter26.model.List;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ListRepository extends PagingAndSortingRepository<List, Long> {
    Collection<List> findByUserUsername(String username);
}
