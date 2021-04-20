package com.factory.cake.authentication.domain.service;

import java.util.List;

import javax.validation.Valid;

import com.factory.cake.authentication.domain.dto.UserDTO;

public interface UserService {

	public UserDTO createUser(@Valid final UserDTO userDTO) throws Exception;

	public UserDTO findUser(final String username);

	public void deleteUser(final String username);

	public List<UserDTO> findUsers();

	public boolean exists(String username);
	
}