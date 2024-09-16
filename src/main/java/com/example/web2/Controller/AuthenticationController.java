package com.example.web2.Controller;

import com.example.web2.DTO.request.AuthenticationRequest;
import com.example.web2.DTO.request.verifytokenRequest;
import com.example.web2.DTO.response.AuthenticationResponse;
import com.example.web2.DTO.response.verigyTokenResponse;
import com.example.web2.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse loginto(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/checkToken")
    public verigyTokenResponse verifyToken(verifytokenRequest request) throws JOSEException, ParseException {
        return authenticationService.veryfitoken(request);
    }
    @PostMapping("/Logout")
    public void Logout(String token) throws JOSEException, ParseException {
         authenticationService.Logout(token);
    }
}