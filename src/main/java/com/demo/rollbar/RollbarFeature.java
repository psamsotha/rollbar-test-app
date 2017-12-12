package com.demo.rollbar;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.notifier.config.ConfigBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;


@Provider
public class RollbarFeature implements Feature {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Override
    public boolean configure(FeatureContext context) {
        String accessToken = getAccessToken();
        if (accessToken == null) {
            return false;
        }

        Config config = ConfigBuilder.withAccessToken(accessToken)
                .environment(getEnv())
                .accessToken(accessToken)
                .build();
        Rollbar rollbar = new Rollbar(config);

        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(rollbar).to(Rollbar.class);
            }
        });
        context.register(RollbarExceptionMapper.class);

        return true;
    }

    private String getAccessToken() {
        String accessToken = System.getenv("ROLLBAR_TOKEN");
        if (accessToken == null) {
            LOGGER.warn("No ROLLBAR_TOKEN in environment. Rollbar not activated.");
            return null;
        }
        return accessToken;
    }

    private String getEnv() {
        return (System.getenv("ENV") != null && System.getenv("ENV").equals("production"))
                ? "production"
                : "development";
    }
}
