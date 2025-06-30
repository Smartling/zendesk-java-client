package org.zendesk.client.v2.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthToken
{
    @JsonProperty ("access_token")
    private String accessToken;
    @JsonProperty ("refresh_token")
    private String refreshToken;
    @JsonProperty ("token_type")
    private String tokenType;
    private String scope;
    @JsonProperty ("expires_in")
    private Integer expiresIn;
    @JsonProperty ("refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(final String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(final String tokenType)
    {
        this.tokenType = tokenType;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(final String scope)
    {
        this.scope = scope;
    }

    public Integer getExpiresIn()
    {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn)
    {
        this.expiresIn = expiresIn;
    }

    public Integer getRefreshTokenExpiresIn()
    {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(Integer refreshTokenExpiresIn)
    {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
