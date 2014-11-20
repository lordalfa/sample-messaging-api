package it.dv.samples.techallenge.api.support;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Configuration
@EnableSwagger
public class SwaggerConfiguration {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {

        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .includePatterns(".*users.*", ".*messages.*", ".*access.*")
                .ignoredParameterTypes(ResponseEntity.class, HttpEntity.class, HttpServletRequest.class, HttpServletResponse.class)
                .apiInfo(new ApiInfo("T.E. Challenge API Documentation", null, null, null, null, null));
    }

}
