package org.zendesk.client.v2;

public class ZendeskTimeoutException extends ZendeskException {
    public ZendeskTimeoutException(String message) {
        super(message);
    }

    public ZendeskTimeoutException() {
    }

    public ZendeskTimeoutException(Throwable cause) {
        super(cause);
    }

    public ZendeskTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
