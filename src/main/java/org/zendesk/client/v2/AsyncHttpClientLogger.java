package org.zendesk.client.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpHeaders;
import net.logstash.logback.argument.StructuredArgument;
import net.logstash.logback.argument.StructuredArguments;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaders.Names.AUTHORIZATION;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

public class AsyncHttpClientLogger
{
    public static final String BODY_LENGTH = "bodyLength";
    public static final String URL = "url";
    public static final String DIRECTION = "direction";
    private final Logger logger;
    private final ObjectMapper mapper;

    public AsyncHttpClientLogger(Logger logger, ObjectMapper mapper)
    {
        this.logger = logger;
        this.mapper = mapper;
    }

    public void logRequest(Request request)
    {
        if (logger.isTraceEnabled())
        {
            List<StructuredArgument> requestArguments = new ArrayList<>();
            try (Formatter message = new Formatter())
            {
                message.format("{} --->%n{} {}%n");
                requestArguments.add(StructuredArguments.value("method", request.getMethod()));
                requestArguments.add(StructuredArguments.value(URL, request.getUrl()));

                requestArguments.add(StructuredArguments.value(DIRECTION, "request"));

                logHeaders(request.getHeaders(), message);

                if (request.getStringData() != null)
                {
                    message.format("%n%s%n", request.getStringData());
                    requestArguments.add(StructuredArguments.value(BODY_LENGTH, request.getStringData().length()));
                } else if (request.getByteData() != null)
                {
                    String contentType = request.getHeaders().get("Content-type");
                    String body = new String(request.getByteData());

                    message.format("%n%s%n", format(contentType, body));
                    requestArguments.add(StructuredArguments.value(BODY_LENGTH, request.getByteData().length));
                    requestArguments.add(StructuredArguments.value("contentType", contentType));
                }

                logger.trace(message.toString(), requestArguments.toArray());
            }
        }
    }

    Response logResponse(Response response)
    {
        if (logger.isTraceEnabled())
        {
            List<StructuredArgument> responseArguments = new ArrayList<>();
            try (Formatter message = new Formatter())
            {
                message.format("{} {}%n");
                responseArguments.add(StructuredArguments.value(URL, response.getUri().toUrl()));
                responseArguments.add(StructuredArguments.value("status", response.getStatusCode()));

                message.format("HTTP/1.1 {}%n");
                responseArguments.add(StructuredArguments.value(DIRECTION, "response"));

                logHeaders(response.getHeaders(), message);

                if (response.getResponseBody() != null)
                {
                    String contentType = response.getHeaders().get("Content-type");
                    message.format("%n%s%n", format(contentType, response.getResponseBody()));
                    responseArguments.add(StructuredArguments.value(BODY_LENGTH, response.getResponseBody().length()));
                }

                logger.trace(message.toString(), responseArguments.toArray());
            }
        }

        return response;
    }

    private String format(String contentType, String body)
    {
        String result;
        if (contentType != null && contentType.toLowerCase().contains(APPLICATION_JSON))
        {
            try
            {
                result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
            }
            catch (JsonProcessingException e)
            {
                logger.warn("Enable to format json string {}", body);
                result = body;
            }
        } else
        {
            result = body;
        }
        return result;
    }

    private void logHeaders(HttpHeaders headers, Formatter message)
    {
        for (String headerName : headers.names())
        {
            headers.getAll(headerName).forEach(
                    headerValue ->
                    {
                        if (!AUTHORIZATION.equalsIgnoreCase(headerName))
                        {
                            message.format("%s: %s%n", headerName, headerValue);
                        }
                    }
            );
        }
    }
}
