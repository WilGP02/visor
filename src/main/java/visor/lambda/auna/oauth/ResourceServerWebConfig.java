package visor.lambda.auna.oauth;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({ "pvt.auna.visorhc.controller" })
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
    //
}
