package com.factory.cake.authentication.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.model.User;
import com.factory.cake.authentication.domain.repo.UserRepository;
import com.factory.cake.domain.model.Address;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public UserDTO createUser(@Valid UserDTO userDTO)  throws Exception{
		if( findUser(userDTO.getUsername()) != null ) {
			throw new Exception("User already exists in database");
		}
		User user = toEntity(userDTO);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User returnedUser = userRepository.save(user);
		return toDTO(returnedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findUser(final String username) {
		User user;
		if((user = userRepository.findById(username).orElse(null))== null) {
			return null;
		} else
			return toDTO(user);
	}

	@Override
	@Transactional
	public void deleteUser(final String username)  {
		User user = userRepository.findById(username).get();
		userRepository.delete(user);
	}

	@Override
	@Transactional
	public void updateUser(@Valid UserDTO userDTO)  {
		User user = userRepository.findById(userDTO.getUsername()).get();
		user.setAddress(new Address(userDTO.getLine1(),userDTO.getLine2(),userDTO.getPostcode()));
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findUsers()  {
		final Iterable<User> users = userRepository.findAll();
		List<UserDTO> usersDTO = ((List<User>) users)
								.stream()
								.map(user -> toDTO(user))
								.collect(Collectors.toList());
		return usersDTO;
	}
	
	private UserDTO toDTO(User user) {
		return new UserDTO(user.getUsername(),user.getPassword(),user.getAddress().getLine1(),user.getAddress().getLine2(),user.getAddress().getPostcode());
	}
	private User toEntity(UserDTO userDTO) {
		return new User(userDTO.getUsername(),userDTO.getPassword(), new Address(userDTO.getLine1(),userDTO.getLine2(),userDTO.getPostcode()));
	}

}
