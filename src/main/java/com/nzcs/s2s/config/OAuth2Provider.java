package com.nzcs.s2s.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;


@Service
public class OAuth2Provider {

    public static final Authentication ANONYMOUS_USER_AUTHENTICATION =
            new AnonymousAuthenticationToken(
                    "key",
                    "anonymous",
                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
            );


    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;


    public String getAuthenticationToken(String paymentPlatform) {
        final OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId(paymentPlatform)
                .principal(ANONYMOUS_USER_AUTHENTICATION)
                .build();
        String token = requireNonNull(authorizedClientManager.authorize(request))
                .getAccessToken()
                .getTokenValue();

        System.out.println(token);

        return "Bearer " + token;
    }
}
