package com.factory.cake.authentication.domain.repo;

import org.springframework.data.repository.CrudRepository;

import com.factory.cake.authentication.domain.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {

}
