package org.zendesk.client.v2.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthToken
{
    @JsonProperty ("access_token")
    private String accessToken;
    @JsonProperty ("token_type")
    private String tokenType;
    private String scope;

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(final String accessToken)
    {
        this.accessToken = accessToken;
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
}
