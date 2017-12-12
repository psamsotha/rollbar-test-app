package com.demo.rollbar;

import com.rollbar.notifier.Rollbar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class RollbarExceptionMapperTest {

    @Mock
    private Rollbar rollbar;

    @InjectMocks
    private RollbarExceptionMapper mapper;


    @Test
    public void testMapperSendsErrorToRollbar() {
        Exception ex = new Exception();
        mapper.toResponse(ex);

        verify(rollbar).error(ex);
    }

    @Test
    public void testMapperReturnsWebApplicationExceptionResponse() {
        WebApplicationException wae = new WebApplicationException(Response.status(400).build());
        Response res = mapper.toResponse(wae);

        assertThat(res.getStatus()).isEqualTo(400);
    }

    @Test
    public void testMapperReturnsServerErrorResponse() {
        IllegalArgumentException ex = new IllegalArgumentException("Bad arg.");
        Response res = mapper.toResponse(ex);

        assertThat(res.getStatus()).isEqualTo(500);
        assertThat(res.getEntity()).isEqualTo("Bad arg.");
    }
}
