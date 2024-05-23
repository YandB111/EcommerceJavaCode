package com.ecommerce.swaggerConfig;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  
    @Bean
    public Docket api() {
    	   return new Docket(DocumentationType.SWAGGER_2)
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("com.ecommerce.Controller"))
                   .paths(PathSelectors.ant("/api/**"))
                   .build()
                   .apiInfo(metaData())
                   .securityContexts(Collections.singletonList(securityContext()))
                   .securitySchemes(Collections.singletonList(apiKey()));
       }
    
    private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Ecommerce API").description("Ecommerce Application API reference for developers")
				.termsOfServiceUrl("https://yellowandblack0111.web.app/")
				.contact(new Contact("Kashish Sharma", "https://yellowandblack0111.web.app/", "kashishkant428@gmail.com"))
				.license("Y&B License").licenseUrl("kashishkant428@gmail.com").version("1.0").build();
	}
    
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }
}
