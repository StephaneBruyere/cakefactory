package com.factory.cake.authentication.domain.service;

import java.util.List;

import javax.validation.Valid;

import com.factory.cake.authentication.domain.dto.UserDTO;

public interface UserService {

	public UserDTO createUser(@Valid final UserDTO userDTO);

	public UserDTO findUser(final String username);

	public void deleteUser(final String username);

	public void updateUser(@Valid final UserDTO userDTO);

	public List<UserDTO> findUsers();


}