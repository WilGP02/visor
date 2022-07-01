package visor.lambda.auna.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;



@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfigRemoteTokenService extends ResourceServerConfigurerAdapter {

    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
    public static final String TOKEN_EXCLUDE_REST_PUB = "/pub/*";
    
    @Value("${vhc.urisecurity.checktoken}")
    String checkToken;
    
    @Value("${vhc.urisecurity.clientId}")
    String clientId;
    
    @Value("${vhc.urisecurity.clientSecret}")
    String clientSecret;
    
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        /*
		 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * IF_REQUIRED) .and() .authorizeRequests().anyRequest().permitAll();
         */
        // @formatter:on
        // @formatter:off
        http.csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().authorizeRequests().antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT)
                .access("#oauth2.hasScope('read') and #oauth2.hasScope('write')") // Protected API End-points
                .antMatchers(TOKEN_EXCLUDE_REST_PUB).permitAll().anyRequest().permitAll();
        // @formatter:on
    }

    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
 
        tokenService.setCheckTokenEndpointUrl(checkToken);
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(clientSecret);

        //tokenService.setCheckTokenEndpointUrl("http://10.41.190.223:8080/oauth2-server-ptm/oauth/check_token");
//        tokenService.setCheckTokenEndpointUrl("http://localhost:8080/oauth2-server-ptm/oauth/check_token");
//        tokenService.setCheckTokenEndpointUrl("https://qaspagoenlinea.auna.pe/oauth2-server-ptm/oauth/check_token");
//        tokenService.setCheckTokenEndpointUrl("http://localhost:8080/auna.oauth2.ptm.ws.1.0/oauth/check_token");

//        tokenService.setClientId("ptm-portal-medico-app");
//        tokenService.setClientSecret("B95B75295F3D3E2296A3DA02AC8EF932ED33B7CC47727D74F0B02004330AAA87");
        
 
//        tokenService.setCheckTokenEndpointUrl(UtilGeneric.RSC.getOauth2().get("url"));
//        tokenService.setClientId(UtilGeneric.RSC.getOauth2().get("client_id"));
//        tokenService.setClientSecret(UtilGeneric.RSC.getOauth2().get("client_secret")); 

        return tokenService;
    }

}
