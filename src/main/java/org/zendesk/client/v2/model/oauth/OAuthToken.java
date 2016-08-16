package org.zendesk.client.v2.model.oauth;

public class OAuthToken
{
    private String access_token;
    private String token_type;
    private String scope;

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(final String access_token)
    {
        this.access_token = access_token;
    }

    public String getToken_type()
    {
        return token_type;
    }

    public void setToken_type(final String token_type)
    {
        this.token_type = token_type;
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
