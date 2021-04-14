package com.factory.cake.authentication.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
	private String password;
    private String line1;
    private String line2;
    private String postcode;
    
}
