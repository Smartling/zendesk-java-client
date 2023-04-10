package org.zendesk.client.v2;

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

public class AsyncHttpClientLogger
{
    private final Logger logger;

    public AsyncHttpClientLogger(Logger logger)
    {
        this.logger = logger;
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
                requestArguments.add(StructuredArguments.value("url", request.getUrl()));

                requestArguments.add(StructuredArguments.value("direction", "request"));

                logHeaders(request.getHeaders(), message);

                if (request.getStringData() != null)
                {
                    message.format("%n%s%n", request.getStringData());
                    requestArguments.add(StructuredArguments.value("bodyLength", request.getStringData().length()));
                } else if (request.getByteData() != null)
                {
                    requestArguments.add(StructuredArguments.value("bodyLength", request.getByteData().length));
                    requestArguments.add(StructuredArguments.value("contentType", request.getHeaders().get("Content-type")));
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
                message.format("{} --->%n{} {}%n");
//                responseArguments.add(StructuredArguments.value("method", response.getUri().ge));
                responseArguments.add(StructuredArguments.value("url", response.getUri().toUrl()));

                message.format("HTTP/1.1 {} {} ({}ms)%n");
                responseArguments.add(StructuredArguments.value("status", response.getStatusCode()));
//                responseArguments.add(StructuredArguments.value("elapsedTime", System.nanoTime() - startTime));
                responseArguments.add(StructuredArguments.value("direction", "response"));

                logHeaders(response.getHeaders(), message);

                if (response.getResponseBody() != null)
                {
                    message.format("%n");

                    message.format("%s%n", response.getResponseBody());
                    responseArguments.add(StructuredArguments.value("bodyLength", response.getResponseBody().length()));

                    logger.trace(message.toString(), responseArguments.toArray());
                } else
                {
                    logger.trace(message.toString(), responseArguments.toArray());
                }
            }
        }

        return response;
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
