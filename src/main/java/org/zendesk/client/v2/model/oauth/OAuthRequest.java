package org.zendesk.client.v2.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthRequest
{
    @JsonProperty ("grant_type")
    private final String grantType = "authorization_code";
    private final String code;
    @JsonProperty ("client_id")
    private final String clientId;
    @JsonProperty ("client_secret")
    private final String clientSecret;
    @JsonProperty ("redirect_uri")
    private final String redirectUri;
    private final String scope = "read";

    public OAuthRequest(final String code, final String redirectUri, final String clientId, final String clientSecret)
    {
        this.code = code;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public String getCode()
    {
        return code;
    }

    public String getClientId()
    {
        return clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public String getRedirectUri()
    {
        return redirectUri;
    }

    public String getScope()
    {
        return scope;
    }
}
