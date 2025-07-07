package org.zendesk.client.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class BaseWiremockTest
{
    private static final String MOCK_URL_FORMATTED_STRING = "http://localhost:%d";
    private static final RandomStringGenerator RANDOM_STRING_GENERATOR =
            new RandomStringGenerator.Builder().withinRange('a', 'z').build();
    private static final String MOCK_API_TOKEN = RANDOM_STRING_GENERATOR.generate(15);
    private static final String MOCK_USERNAME = RANDOM_STRING_GENERATOR.generate(10).toLowerCase() + "@cloudbees.com";

    @ClassRule
    public static WireMockClassRule zendeskApiClass = new WireMockClassRule(options()
            .dynamicPort()
            .dynamicHttpsPort()
    );

    @Rule
    public WireMockClassRule zendeskApiMock = zendeskApiClass;

    protected Zendesk client;
    protected ObjectMapper objectMapper = Zendesk.createMapper();

    @Before
    public void setUp()
    {
        int ephemeralPort = zendeskApiMock.port();

        String hostname = String.format(MOCK_URL_FORMATTED_STRING, ephemeralPort);

        client = new Zendesk.Builder(hostname)
                .setUsername(MOCK_USERNAME)
                .setToken(MOCK_API_TOKEN)
                .build();
    }

    @After
    public void closeClient()
    {
        if (client != null)
        {
            client.close();
        }
        client = null;
    }
}
