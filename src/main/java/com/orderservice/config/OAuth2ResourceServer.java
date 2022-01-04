package com.orderservice.config;

import com.orderservice.dto.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    private static final String ORDER_RESOURCE_ID = "order";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .accessDeniedHandler(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception{
        resources
                .resourceId(ORDER_RESOURCE_ID);

    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }



/*
///
.tokenServices(createTokenServices());



    @Bean
    public DefaultTokenServices createTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore( createTokenStore() );
        return defaultTokenServices;
    }

    @Bean
    public TokenStore createTokenStore() {
        return new JwtTokenStore( createJwtAccessTokenConverter() );
    }

    @Bean
    public JwtAccessTokenConverter createJwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter( new JwtConverter() );
        return converter;
    }


//    public static class CustomAccessTokenConverter extends JwtAccessTokenConverter{
//
//        @Override
//        public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
//            OAuth2Authentication authentication = super.extractAuthentication(claims);
//
//            CustomPrincipal userDetails = new CustomPrincipal ();
//            //userDetails.setUserId(((Integer)claims.get("id")).longValue());
//            userDetails.setUsername((String) claims.get("name"));
//            userDetails.setEmail((String) claims.get("email"));
//
//            authentication.setDetails(userDetails);
//
//            return authentication;
//        }
//    }

    public static class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {

        @Override
        public void configure(JwtAccessTokenConverter converter) {
            converter.setAccessTokenConverter(this);
        }

        @Override
        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
            OAuth2Authentication auth = super.extractAuthentication(map);
            auth.setDetails(map); //this will get spring to copy JWT content into Authentication
            return auth;
        }
    }



 */





}
