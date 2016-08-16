package org.zendesk.client.v2.model.oauth;

public class OAuthRequest
{
    private final String grant_type = "authorization_code";
    private final String code;
    private final String client_id;
    private final String client_secret;
    private final String redirect_uri;
    private final String scope = "read";

    public OAuthRequest(final String code, final String redirect_uri, final String client_id, final String client_secret)
    {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }

    public String getGrant_type()
    {
        return grant_type;
    }

    public String getCode()
    {
        return code;
    }

    public String getClient_id()
    {
        return client_id;
    }

    public String getClient_secret()
    {
        return client_secret;
    }

    public String getRedirect_uri()
    {
        return redirect_uri;
    }

    public String getScope()
    {
        return scope;
    }
}
