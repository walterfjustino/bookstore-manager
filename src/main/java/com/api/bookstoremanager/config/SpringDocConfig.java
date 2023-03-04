package com.api.bookstoremanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(tags = {
        @Tag(name = "Authors"),
        @Tag(name = "Books"),
        @Tag(name = "Users"),
        @Tag(name = "Publishers")})
@Configuration
@SecurityScheme(
        name = "token",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class SpringDocConfig {

    public static final String BASE_PACKAGE = "com.api.bookstoremanager";
    public static final String API_TITLE = "Bookstore Manager";
    public static final String API_DESCRIPTION = "REST API for complete bookstore manager";
    public static final String API_VERSION = "1.0.0";
    public static final String CONTACT_NAME = "Walter Fernandes Justino";
    public static final String CONTACT_GITHUB = "https://github.com/walterfjustino";
    public static final String CONTACT_EMAIL = "walterfjustino@gmail.com";
    public static final String LICENSE_NAME = "Apache 2.0";
    public static final String URL_SPRINGDOC = "http://springdoc.org";


    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION)
                        .license(new License()
                                .name(LICENSE_NAME)
                                .url(URL_SPRINGDOC))
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .email(CONTACT_EMAIL)
                                .url(CONTACT_GITHUB)));
    }

//    @Bean
//    public Docket api() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
//                .paths(PathSelectors.any())
//                .build().apiInfo(buildApiInfo());
//    }
//
//    private ApiInfo buildApiInfo() {
//        return new ApiInfoBuilder()
//                .title(API_TITLE)
//                .description(API_DESCRIPTION)
//                .version(API_VERSION)
//                .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
//                .build();
//    }
}
