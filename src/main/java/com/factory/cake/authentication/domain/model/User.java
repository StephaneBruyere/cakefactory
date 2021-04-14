package com.factory.cake.authentication.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.factory.cake.domain.model.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_USER") 
public class User implements Serializable {

	@Id
	@Column(name="ID")
	@NotBlank(message = "email must be defined")
    private String username;
    
    @Size(min=4, message="password's length exception (mini. of 4 char. required)")
	private String password;
    
    @Embedded
    private Address address = new Address();
    
}
