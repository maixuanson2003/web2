package com.example.web2.Service;


import com.example.web2.DTO.request.AuthenticationRequest;
import com.example.web2.DTO.request.verifytokenRequest;
import com.example.web2.DTO.response.AuthenticationResponse;
import com.example.web2.DTO.response.verigyTokenResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEException.*;

import java.text.ParseException;

public interface AuthenticationService {
    public verigyTokenResponse veryfitoken(verifytokenRequest request) throws JOSEException, ParseException;
    public void Logout(String token) throws ParseException, JOSEException ;

    public AuthenticationResponse login(AuthenticationRequest requests);


}
