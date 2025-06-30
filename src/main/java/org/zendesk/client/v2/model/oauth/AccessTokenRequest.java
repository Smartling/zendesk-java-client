package org.zendesk.client.v2.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenRequest
{
    @JsonProperty ("grant_type")
    private final String grantType = "authorization_code";

    private final String code;

    @JsonProperty ("redirect_uri")
    private final String redirectUri;

    @JsonProperty ("client_id")
    private final String clientId;

    @JsonProperty ("client_secret")
    private final String clientSecret;

    // scope parameter is currently ignored, Zendesk API uses scope from initial authorization request
    private final String scope;

    @JsonProperty ("expires_in")
    private final Integer expiresIn;

    @JsonProperty ("refresh_token_expires_in")
    private final Integer refreshTokenExpiresIn;

    public AccessTokenRequest(String code, String redirectUri, String clientId, String clientSecret, String scope, Integer expiresIn, Integer refreshTokenExpiresIn)
    {
        this.code = code;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.expiresIn = expiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public String getCode()
    {
        return code;
    }

    public String getRedirectUri()
    {
        return redirectUri;
    }

    public String getClientId()
    {
        return clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public String getScope()
    {
        return scope;
    }

    public Integer getExpiresIn()
    {
        return expiresIn;
    }

    public Integer getRefreshTokenExpiresIn()
    {
        return refreshTokenExpiresIn;
    }
}
