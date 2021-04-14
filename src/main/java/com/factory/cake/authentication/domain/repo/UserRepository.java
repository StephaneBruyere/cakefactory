package com.factory.cake.authentication.domain.repo;

import org.springframework.data.repository.CrudRepository;

import com.factory.cake.authentication.domain.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}