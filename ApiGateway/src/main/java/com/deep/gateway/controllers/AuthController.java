package com.deep.gateway.controllers;

import com.deep.gateway.model.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user,
            Model model

    ){

        LOGGER.info("user email id is : ", user.getEmail());

        //creating auth response object
      AuthResponse response =  new AuthResponse();

      //setting email  to auth response
      response.setUserId(user.getEmail());

        //setting object to auth response
      response.setAccessToken(client.getAccessToken().getTokenValue());

      response.setRefreshToken(client.getRefreshToken().getTokenValue());

      response.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

     List<String> authorities = user.getAuthorities().stream().map(grantedAuthority -> {
          return grantedAuthority.getAuthority();
      }).collect(Collectors.toList());

      response.setAuthorities(authorities);

      return ResponseEntity.ok().body(response);

    }

}
