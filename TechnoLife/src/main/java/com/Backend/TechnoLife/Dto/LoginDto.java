package com.Backend.TechnoLife.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    public String email;
    public String password;
    public String rol;

    public LoginDto() {}
    public LoginDto(String email, String password, String rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

}
