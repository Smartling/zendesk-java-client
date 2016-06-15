package org.zendesk.client.v2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.zendesk.client.v2.model.hc.Translation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.Response;

@RunWith(MockitoJUnitRunner.class)
public class ZendeskTranslationTest
{
    private static final String NOT_FOUND_RESPONSE = "{\"error\":\"RecordNotFound\",\"description\":\"Not found\"}";
    private static final String DEFAULT_URL = "https://test.zendesk.com";
    private static final String DEFAULT_USER = "user";
    private static final String DEFAULT_TOKEN = "token";
    private static final String DEFAULT_BODY = "body";
    private static final String DEFAULT_TITLE = "title";

    private Zendesk instance;

    @Mock
    private AsyncHttpClient client;

    @Before
    public void setUp()
    {
        this.instance = new Zendesk.Builder(DEFAULT_URL)
                .setClient(client)
                .setUsername(DEFAULT_USER)
                .setToken(DEFAULT_TOKEN)
                .build();
    }

    @Test
    public void testGetTranslation() throws Exception
    {
        long id = 18;
        String locale = "zo";
        String url = DEFAULT_URL + "/api/v2/help_center/articles/" + id + "/translations/" + locale + ".json";
        Translation responseTranslation = getTranslation(locale, id);
        setupHttpCall("GET", url, null, toContentResponse(200, responseTranslation));

        Translation result = instance.getArticleTranslation(18L, "zo");

        assertNotNull(result);
        assertEquals(responseTranslation.getId(), result.getId());
        assertEquals(responseTranslation.getLocale(), result.getLocale());
        assertEquals(responseTranslation.getSourceType(), result.getSourceType());
        assertEquals(responseTranslation.getSourceId(), result.getSourceId());
        assertEquals(responseTranslation.getTitle(), result.getTitle());
        assertEquals(responseTranslation.getBody(), result.getBody());
    }

    @Test
    public void testGetTranslationNotFound() throws Exception
    {
        long id = 18;
        String locale = "zo";
        String url = DEFAULT_URL + "/api/v2/help_center/articles/" + id + "/translations/" + locale + ".json";
        setupHttpCall("GET", url, null, toContentResponse(200, null));

        Translation result = instance.getArticleTranslation(18L, "zo");

        assertNull(result);
    }

    @Test
    public void testCreateTranslation() throws Exception
    {
        long id = 18;
        String url = DEFAULT_URL + "/api/v2/help_center/articles/" + id + "/translations.json";
        Translation translation = getTranslation("zo", id);
        setupHttpCall("POST", url, serialize(translation), toContentResponse(200, translation));

        Translation result = instance.createArticleTranslation(id, translation);

        assertNotNull(result);
        assertEquals(translation.getId(), result.getId());
        assertEquals(translation.getLocale(), result.getLocale());
        assertEquals(translation.getSourceType(), result.getSourceType());
        assertEquals(translation.getSourceId(), result.getSourceId());
        assertEquals(translation.getTitle(), result.getTitle());
        assertEquals(translation.getBody(), result.getBody());
    }

    @Test
    public void testUpdateTranslation() throws Exception
    {
        long id = 18;
        String locale = "zo";
        String url = DEFAULT_URL + "/api/v2/help_center/articles/" + id + "/translations/" + locale + ".json";
        Translation translation = getTranslation(locale, id);
        setupHttpCall("PUT", url, serialize(translation), toContentResponse(200, translation));

        Translation result = instance.updateArticleTranslation(id, locale, translation);

        assertNotNull(result);
        assertEquals(translation.getId(), result.getId());
        assertEquals(translation.getLocale(), result.getLocale());
        assertEquals(translation.getSourceType(), result.getSourceType());
        assertEquals(translation.getSourceId(), result.getSourceId());
        assertEquals(translation.getTitle(), result.getTitle());
        assertEquals(translation.getBody(), result.getBody());
    }

    @Test
    public void testDeleteTranslation() throws Exception
    {
        long id = 168;
        Translation translation = new Translation();
        translation.setId(id);
        String url = DEFAULT_URL + "/api/v2/help_center/translations/" + id + ".json";
        setupHttpCall("DELETE", url, null, toContentResponse(200, null));

        instance.deleteTranslation(translation);
    }

    private void setupHttpCall(String method, String url, byte[] body, Response response)
    {
        when(client.executeRequest(any(Request.class), Mockito.<AsyncHandler<Translation>>any())).thenAnswer(invocation -> {
            assertEquals(method, invocation.getArgumentAt(0, Request.class).getMethod());
            assertEquals(url, invocation.getArgumentAt(0, Request.class).getUrl());
            assertArrayEquals(body, invocation.getArgumentAt(0, Request.class).getByteData());

            Object result = invocation.getArgumentAt(1, AsyncCompletionHandler.class).onCompleted(response);
            ListenableFuture<Object> futureResult = mock(ListenableFuture.class);
            when(futureResult.get()).thenReturn(result);
            return futureResult;
        });
    }

    private Response toContentResponse(Integer responseCode, Translation translation) throws IOException
    {
        Response response = mock(Response.class);
        when(response.getStatusCode()).thenReturn(responseCode);
        when(response.getResponseBodyAsStream()).thenReturn(new ByteArrayInputStream(serialize(translation)));

        return response;
    }

    private byte[] serialize(Translation translation) throws IOException
    {
        ObjectMapper objectMapper = Zendesk.createMapper();
        byte[] translationAsJsonBytes = NOT_FOUND_RESPONSE.getBytes();
        if (null != translation)
        {
            translationAsJsonBytes = objectMapper.writeValueAsBytes(Collections.singletonMap("translation", translation));
        }
        return translationAsJsonBytes;
    }

    private Translation getTranslation(String locale, Long sourceId)
    {
        Translation translation = new Translation();
        translation.setId(442L);
        translation.setLocale(locale);
        translation.setSourceId(sourceId);
        translation.setTitle(DEFAULT_TITLE);
        translation.setBody(DEFAULT_BODY);

        return translation;
    }
}
