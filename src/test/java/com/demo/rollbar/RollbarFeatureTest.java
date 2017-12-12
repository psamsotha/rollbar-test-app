package com.demo.rollbar;

import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.FeatureContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


public class RollbarFeatureTest {

    private static final String KEY_ROLLBAR_TOKEN = "ROLLBAR_TOKEN";


    @Test
    public void testFeatureEnabledWithToken() throws Exception {
        EnvUtils.setEnv(KEY_ROLLBAR_TOKEN, "<token>");

        FeatureContext context = Mockito.mock(FeatureContext.class);
        boolean configured = new RollbarFeature().configure(context);

        verify(context).register(any());
        assertThat(configured).isTrue();

        EnvUtils.clearEnv(KEY_ROLLBAR_TOKEN);
    }

    @Test
    public void testFeatureNotEnabledWithoutToken() {
        FeatureContext context = Mockito.mock(FeatureContext.class);
        boolean configured = new RollbarFeature().configure(context);

        assertThat(configured).isFalse();
    }
}
