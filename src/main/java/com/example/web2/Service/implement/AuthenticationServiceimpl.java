package com.example.web2.Service.implement;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.example.web2.Entity.TokenInvalid;
import com.example.web2.Entity.actor;
import com.example.web2.Repository.TokenInvalidRepository;
import com.example.web2.Repository.actorRepository;
import com.example.web2.DTO.request.AuthenticationRequest;
import com.example.web2.DTO.request.verifytokenRequest;
import com.example.web2.DTO.response.AuthenticationResponse;
import com.example.web2.DTO.response.verigyTokenResponse;
import com.example.web2.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class AuthenticationServiceimpl implements AuthenticationService {
    @Autowired
    private TokenInvalidRepository tokenInvalidRepository;
    @Autowired
    private  actorRepository actors;
    protected static final String KEY = "NQc7mrnHIwVaDA519Ka3ph/ZdHVjvu5NhWkNMfExmAIHpDtO3PShgPqK4w3Rivq7";

    @Override
    public verigyTokenResponse veryfitoken(verifytokenRequest request) throws JOSEException, ParseException {

        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(KEY.getBytes());
        try {
            SignedJWT sign = SignedJWT.parse(token);
            Date tokenExpiry = sign.getJWTClaimsSet().getExpirationTime();
            boolean checkExpiry = false;
            if (tokenExpiry.after(new Date())) {
                checkExpiry = true;
            }
            boolean checkToken = sign.verify(verifier);
            if (checkExpiry && checkToken) {
                return new verigyTokenResponse().builder()
                        .valid(true)
                        .build();

            } else {
                return new verigyTokenResponse().builder()
                        .valid(false)
                        .build();
            }

        } catch (Exception e) {
            throw new ParseException(null, 21);
        }

    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest requests) {
        if (requests.getUsername() == null || requests.getUsername().trim().isEmpty() ||
                requests.getPassword() == null || requests.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Email và mật khẩu không được để trống");
        }

        BCryptPasswordEncoder passwordCheck = new BCryptPasswordEncoder();
        AuthenticationResponse authenCheck = new AuthenticationResponse();
        actor userfind =actors.findByUsername(requests.getUsername());

        if (userfind == null) {
            throw new RuntimeException("khong xac thuc duoc");
        }
        boolean Authen = passwordCheck.matches(requests.getPassword(), userfind.getPassword());
        if (Authen) {
            final String token = GenerateToken(userfind);
            authenCheck.setToken(token);
            authenCheck.setAuthenticated(true);
            authenCheck.setUsername(userfind.getUsername());
            authenCheck.setType(userfind.getType());
        } else {
            throw new RuntimeException("khong xac thuc duoc");
        }
        return authenCheck;

    }
    @Override
    public void Logout(String token) throws ParseException, JOSEException {
        var signtoken=verifyToken( token);
        TokenInvalid tokenInvalid=new TokenInvalid().builder()
                .id(signtoken.getJWTClaimsSet().getJWTID())
                .expiryTime(signtoken.getJWTClaimsSet().getExpirationTime())
                .build();
        tokenInvalidRepository.save(tokenInvalid);

    }
    private  SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(KEY.getBytes());
        SignedJWT sign = SignedJWT.parse(token);
        Date tokenExpiry = sign.getJWTClaimsSet().getExpirationTime();
        var verify=sign.verify(verifier);
        if (!(verify&&tokenExpiry.after(sign.getJWTClaimsSet().getExpirationTime()))){
            throw new RuntimeException("khong xac thuc duoc");
        }
        if (tokenInvalidRepository.existsById(sign.getJWTClaimsSet().getJWTID())){
            throw new RuntimeException("khong xac thuc duoc");
        }

        return sign;
    }

    private String GenerateToken(actor actors) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder().subject(actors.getUsername()).issuer("son.com").issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", actors.getType()).claim("userid",actors.getId()).build();
        Payload payload = new Payload(jwtClaimSet.toJSONObject());

        JWSObject object = new JWSObject(header, payload);
        try {
            object.sign(new MACSigner(KEY.getBytes()));
            return object.serialize();

        } catch (Exception e) {
            throw new RuntimeException("error");

        }
    }
}