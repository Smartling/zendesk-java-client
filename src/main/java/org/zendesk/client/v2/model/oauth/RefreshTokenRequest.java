package org.zendesk.client.v2.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenRequest
{
    @JsonProperty("grant_type")
    private final String grantType = "refresh_token";

    @JsonProperty ("refresh_token")
    private final String refreshToken;

    @JsonProperty ("client_id")
    private final String clientId;

    @JsonProperty ("client_secret")
    private final String clientSecret;

    // It is plural, see https://developer.zendesk.com/api-reference/ticketing/oauth/grant_type_tokens/#scope
    // It is currently ignored, Zendesk API uses scope from initial authorization request
    private final String scopes;

    @JsonProperty ("expires_in")
    private final Integer expiresIn;

    @JsonProperty ("refresh_token_expires_in")
    private final Integer refreshTokenExpiresIn;

    public RefreshTokenRequest(String refreshToken, String clientId, String clientSecret, String scope, Integer expiresIn, Integer refreshTokenExpiresIn)
    {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scope;
        this.expiresIn = expiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public String getClientId()
    {
        return clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public String getScopes()
    {
        return scopes;
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
