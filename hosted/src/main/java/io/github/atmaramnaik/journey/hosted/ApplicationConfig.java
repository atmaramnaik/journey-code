package io.github.atmaramnaik.journey.hosted;
import io.github.atmaramnaik.journey.journey.JourneyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.concurrent.Executor;

import static io.github.atmaramnaik.journey.http.rest.steps.RestStep.*;
import static io.github.atmaramnaik.journey.journey.Journey.journey;
import static io.github.atmaramnaik.journey.template.template.Template.*;


@EnableAsync
@Configuration
@EnableWebSecurity
@Order(1)
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
    @Value("${hosted.http.auth-token-header-name}")
    private String principalRequestHeader;

    @Value("${hosted.http.auth-token}")
    private String principalRequestValue;

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "journeyManager")
    public JourneyManager journeyManager(){
        JourneyManager journeyManager=new JourneyManager();
        journeyManager.add(journey()
                .with(
                        get(
                                text(string("https://quotes.rest/qod")))
                                .capture(object("contents",object("quotes",array(object("quote",xVar("quote"))))))
                ).responding(text(
                        string("Quote for now is: "),
                        textEx(var("quote").ofType(String.class))
                        )
                )
                .as("Get Random Quote"))
                .add(journey()
                        .with(
                                get(text(string("http://api.icndb.com/jokes/random")))
                                .capture(object("value",object("joke",xVar("joke"))))
                        )
                        .responding(text(
                                string("Joke for now is: "),
                                textEx(var("joke").ofType(String.class))
                        )).as("Get Random joke")
                );
        return journeyManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
        filter.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                if (!principalRequestValue.equals(principal))
                {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });
        http.
                antMatcher("/api/**").
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }
}
