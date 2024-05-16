package org.zendesk.client.v2;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@Ignore("Test uses third party service which may be unavailable")
public class ZendeskExceptionTest
{
    private AsyncHttpClient client;

    @Test
    public void testRequestTimeoutException() throws IOException
    {
        client = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setRequestTimeout(10000).build());
        assertThrows(ZendeskTimeoutException.class, () -> request("https://httpstat.us/504?sleep=20000"));
        client.close();
    }

    @Test
    public void testReadTimeoutException() throws IOException
    {
        client = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setReadTimeout(10000).build());
        assertThrows(ZendeskTimeoutException.class, () -> request("https://httpstat.us/504?sleep=20000"));
        client.close();
    }

    @Test
    public void testRateLimitException() throws IOException
    {
        client = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().build());
        assertThrows(ZendeskResponseRateLimitException.class, () -> request("https://httpstat.us/429"));
        client.close();
    }

    @Test
    public void testSuccess() throws IOException
    {
        client = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().build());
        assertThrows(ZendeskResponseRateLimitException.class, () -> request("https://httpstat.us/429"));
        assertEquals("SUCCESS", request("https://httpstat.us/200"));
        client.close();
    }

    private String request(String url)
    {
        return Zendesk.complete(client.executeRequest(getRequest(url), handler()));
    }

    private static Request getRequest(String template) {
        RequestBuilder builder = new RequestBuilder("GET");
        builder.setUrl(template);
        builder.setHeader("Content-type", "application/json; charset=UTF-8");
        return builder.build();
    }

    private static AsyncCompletionHandler<String> handler() {
        return new AsyncCompletionHandler<String>() {
            @Override
            public String onCompleted(Response response) throws Exception {
                if (response.getStatusCode() / 100 == 2) {
                    return "SUCCESS";
                } else if (response.getStatusCode() == 429) {
                    throw new ZendeskResponseRateLimitException(response);
                }
                throw new ZendeskResponseException(response);
            }
        };
    }
}
