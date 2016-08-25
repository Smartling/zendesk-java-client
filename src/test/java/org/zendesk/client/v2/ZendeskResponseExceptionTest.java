package org.zendesk.client.v2;

import com.ning.http.client.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ZendeskResponseExceptionTest {
    @Mock
    private Response response;

    @Test
    public void shouldBuildGenericResponseException() throws Exception {
        given(response.getStatusCode()).willReturn(400);

        ZendeskResponseException exception = ZendeskResponseException.fromResponse(response);

        assertThat(exception, instanceOf(ZendeskResponseException.class));
    }

    @Test
    public void shouldBuildNotFoundExceptionFor404Response() throws Exception {
        given(response.getStatusCode()).willReturn(404);

        ZendeskResponseException exception = ZendeskResponseException.fromResponse(response);

        assertThat(exception, instanceOf(ZendeskEntityNotFoundException.class));
    }
}