package com.example.web2.DTO.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class actorRequest {
    private String username;
    private String password;
    private String full_name;
    private String phone_number;
    private String email;
    private String address;
    private String birthday;
    private String Type;
}
