package com.factory.cake.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.factory.cake.domain.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
