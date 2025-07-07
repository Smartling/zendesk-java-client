package org.zendesk.client.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.zendesk.client.v2.model.oauth.OAuthToken;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zendesk.client.v2.Zendesk.REFRESH_TOKEN_EXPIRES_IN_DEFAULT;

/**
 * Tests for OAuth token operations
 */
public class OauthTest extends BaseWiremockTest {
    private static final String CODE = "test-auth-code";
    private static final String REFRESH_TOKEN = "refresh-token";
    private static final String REDIRECT_URI = "https://example.com/callback";
    private static final String CLIENT_ID = "test-client-id";
    private static final String CLIENT_SECRET = "test-client-secret";
    private static final String SCOPE = "read write";
    private static final Integer ACCESS_TOKEN_EXPIRES_IN = 300;
    private static final Integer REFRESH_TOKEN_EXPIRES_IN = 604_800;

    @Test
    public void getOAuthToken() throws JsonProcessingException {
        OAuthToken response = new OAuthToken();
        response.setAccessToken("new-access-token");
        response.setRefreshToken("new-refresh-token");
        response.setTokenType("Bearer");
        response.setScope(SCOPE);
        response.setExpiresIn(ACCESS_TOKEN_EXPIRES_IN);
        response.setRefreshTokenExpiresIn(REFRESH_TOKEN_EXPIRES_IN);

        zendeskApiMock.stubFor(
            post(urlPathEqualTo("/oauth/tokens"))
                .withRequestBody(equalToJson("{" +
                        "\"grant_type\":\"authorization_code\"," +
                        "\"code\":\"" + CODE +
                        "\",\"redirect_uri\":\"" + REDIRECT_URI +
                        "\",\"client_id\":\"" + CLIENT_ID +
                        "\",\"client_secret\":\"" + CLIENT_SECRET +
                        "\",\"scope\":\"" + SCOPE +
                        "\",\"expires_in\":" + ACCESS_TOKEN_EXPIRES_IN +
                        ",\"refresh_token_expires_in\":" + REFRESH_TOKEN_EXPIRES_IN +
                        "}"))
                .willReturn(ok()
                    .withBody(objectMapper.writeValueAsString(response))
                    .withHeader("Content-Type", "application/json"))
        );

        OAuthToken actualToken = client.getOAuthToken(CODE, REDIRECT_URI, CLIENT_ID, CLIENT_SECRET, SCOPE, ACCESS_TOKEN_EXPIRES_IN, REFRESH_TOKEN_EXPIRES_IN);

        assertThat(actualToken).isNotNull();
        assertThat(actualToken.getAccessToken()).isEqualTo("new-access-token");
        assertThat(actualToken.getRefreshToken()).isEqualTo("new-refresh-token");
        assertThat(actualToken.getTokenType()).isEqualTo("Bearer");
        assertThat(actualToken.getScope()).isEqualTo(SCOPE);
        assertThat(actualToken.getExpiresIn()).isEqualTo(ACCESS_TOKEN_EXPIRES_IN);
        assertThat(actualToken.getRefreshTokenExpiresIn()).isEqualTo(REFRESH_TOKEN_EXPIRES_IN);
    }

    @Test
    public void getOAuthTokenWithDefaults() throws JsonProcessingException {
        OAuthToken response = new OAuthToken();
        response.setAccessToken("new-access-token");
        response.setRefreshToken("new-refresh-token");
        response.setTokenType("Bearer");
        response.setScope(SCOPE);
        response.setRefreshTokenExpiresIn(REFRESH_TOKEN_EXPIRES_IN_DEFAULT);

        zendeskApiMock.stubFor(
            post(urlPathEqualTo("/oauth/tokens"))
                .withRequestBody(equalToJson("{" +
                        "\"grant_type\":\"authorization_code\"" +
                        ",\"code\":\"" + CODE +
                        "\",\"redirect_uri\":\"" + REDIRECT_URI +
                        "\",\"client_id\":\"" + CLIENT_ID +
                        "\",\"client_secret\":\"" + CLIENT_SECRET +
                        "\"}"))
                .willReturn(ok()
                    .withBody(objectMapper.writeValueAsString(response))
                    .withHeader("Content-Type", "application/json"))
        );

        OAuthToken actualToken = client.getOAuthToken(CODE, REDIRECT_URI, CLIENT_ID, CLIENT_SECRET, null, null, null);

        assertThat(actualToken).isNotNull();
        assertThat(actualToken.getAccessToken()).isEqualTo("new-access-token");
        assertThat(actualToken.getRefreshToken()).isEqualTo("new-refresh-token");
        assertThat(actualToken.getTokenType()).isEqualTo("Bearer");
        assertThat(actualToken.getScope()).isEqualTo(SCOPE);
        assertThat(actualToken.getExpiresIn()).isNull();
        assertThat(actualToken.getRefreshTokenExpiresIn()).isEqualTo(REFRESH_TOKEN_EXPIRES_IN_DEFAULT);
    }

    @Test
    public void refreshOAuthToken() throws JsonProcessingException {
        OAuthToken response = new OAuthToken();
        response.setAccessToken("new-access-token");
        response.setRefreshToken("new-refresh-token");
        response.setTokenType("Bearer");
        response.setScope(SCOPE);
        response.setExpiresIn(ACCESS_TOKEN_EXPIRES_IN);
        response.setRefreshTokenExpiresIn(REFRESH_TOKEN_EXPIRES_IN);

        zendeskApiMock.stubFor(
            post(urlPathEqualTo("/oauth/tokens"))
                .withRequestBody(equalToJson("{" +
                        "\"grant_type\":\"refresh_token\"," +
                        "\"refresh_token\":\"" + REFRESH_TOKEN +
                        "\",\"client_id\":\"" + CLIENT_ID +
                        "\",\"client_secret\":\"" + CLIENT_SECRET +
                        "\",\"scopes\":\"" + SCOPE +
                        "\",\"expires_in\":" + ACCESS_TOKEN_EXPIRES_IN +
                        ",\"refresh_token_expires_in\":" + REFRESH_TOKEN_EXPIRES_IN +
                        "}"))
                .willReturn(ok()
                    .withBody(objectMapper.writeValueAsString(response))
                    .withHeader("Content-Type", "application/json"))
        );

        OAuthToken actualToken = client.refreshOAuthToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET, SCOPE, ACCESS_TOKEN_EXPIRES_IN, REFRESH_TOKEN_EXPIRES_IN);

        assertThat(actualToken).isNotNull();
        assertThat(actualToken.getAccessToken()).isEqualTo("new-access-token");
        assertThat(actualToken.getRefreshToken()).isEqualTo("new-refresh-token");
        assertThat(actualToken.getTokenType()).isEqualTo("Bearer");
        assertThat(actualToken.getScope()).isEqualTo(SCOPE);
        assertThat(actualToken.getExpiresIn()).isEqualTo(ACCESS_TOKEN_EXPIRES_IN);
        assertThat(actualToken.getRefreshTokenExpiresIn()).isEqualTo(REFRESH_TOKEN_EXPIRES_IN);
    }
}
