package org.zendesk.client.v2;

import org.asynchttpclient.Response;

import java.io.IOException;

public class ZendeskEntityNotFoundException extends ZendeskResponseException {
    public ZendeskEntityNotFoundException(Response response) throws IOException {
        super(response);
    }
}
