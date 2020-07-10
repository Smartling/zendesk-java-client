package org.zendesk.client.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.zendesk.client.v2.model.hc.Translation;
import org.zendesk.client.v2.model.hc.TranslationSourceType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.zendesk.client.v2.model.hc.TranslationSourceType.ARTICLE;

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
        String url = DEFAULT_URL + "/api/v2/help_center/" + ARTICLE.getUrlPath() + "/" + id + "/translations/" + locale + ".json";
        Translation responseTranslation = getTranslation(locale, ARTICLE, id);
        Response response = toContentResponse(200, responseTranslation);
        setupHttpCall("GET", url, null, response);

        Translation result = instance.getArticleTranslation( 18L, "zo");

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
        String url = DEFAULT_URL + "/api/v2/help_center/" + ARTICLE.getUrlPath() + "/" + id + "/translations/" + locale + ".json";
        Response response = toContentResponse(200, null);
        setupHttpCall("GET", url, null, response);

        Translation result = instance.getArticleTranslation(18L, "zo");

        assertNull(result);
    }

    @Test
    public void testCreateTranslation() throws Exception
    {
        long id = 18;
        String locale = "zo";
        String url = DEFAULT_URL + "/api/v2/help_center/" + ARTICLE.getUrlPath() + "/" + id + "/translations.json";
        Translation translation = getTranslation(locale, ARTICLE, id);
        Response response = toContentResponse(200, translation);
        setupHttpCall("POST", url, serialize(translation), response);

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
        String url = DEFAULT_URL + "/api/v2/help_center/" + ARTICLE.getUrlPath() + "/" + id + "/translations/" + locale + ".json";
        Translation translation = getTranslation(locale, ARTICLE, id);
        Response response = toContentResponse(200, translation);
        setupHttpCall("PUT", url, serialize(translation), response);

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
        Response response = toContentResponse(200, null);
        setupHttpCall("DELETE", url, null, response);

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

    private Translation getTranslation(String locale, TranslationSourceType sourceType, Long sourceId)
    {
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setLocale(locale);
        translation.setSourceId(sourceId);
        translation.setSourceType(sourceType.getSourceType());
        translation.setTitle(DEFAULT_TITLE);
        translation.setBody(DEFAULT_BODY);
        return translation;
    }
}
