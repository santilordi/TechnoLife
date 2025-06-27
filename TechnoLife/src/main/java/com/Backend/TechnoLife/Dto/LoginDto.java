package com.Backend.TechnoLife.Dto;

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
