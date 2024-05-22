package com.softcorridor.attendance.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**********************************************************
 2023 Copyright (C),  Soft Corridor Limited                                         
 https://www.softcorridor.com                                        
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 10/05/2024
 * Time      : 16:13
 * Project   : attendance
 * Package   : com.softcorridor.attendance.config
 **********************************************************/

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Soft Corridor Ltd",
                        email = "info@softcorridor.com",
                        url = "https://softcorridor.com"
                ),
                description = "Documentation for Attendance backend",
                title = "ATTENDANCE BACKEND",
                version = "1.0"

        ),
        servers = {
                @Server(
                        description = "Local or Development ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production ENV",
                        url = "http://162.246.20.178:8888"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearer"
                )
        }
)
@SecurityScheme(
        name = "bearer",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI baseOpenAPI() {
        ApiResponse AuthSuccessAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"accessToken\": \"*******************\",\n" +
                                        "\t\"refreshToken\": \"*******************\"\n" +
                                        "}")))).description("Successful Request");

        ApiResponse successAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"code\": 200,\n" +
                                        "\t\"status\": \"OK\",\n" +
                                        "\t\"message\": \"Successful\",\n" +
                                        "\t\"data\": {}\n" +
                                        "}")))).description("Successful Request");

        ApiResponse badRequestAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"code\": 400,\n" +
                                        "\t\"status\": \"BAD REQUEST\",\n" +
                                        "\t\"message\": \"Bad Request\",\n" +
                                        "\t\"data\": {}\n" +
                                        "}")))).description("Bad Request");

        ApiResponse unauthorizedAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"code\": 403,\n" +
                                        "\t\"status\": \"UNAUTHORIZED\",\n" +
                                        "\t\"message\": \"Unauthorized or Invalid token\",\n" +
                                        "\t\"data\": {}\n" +
                                        "}")))).description("Unauthorized Request");

        ApiResponse internalServerErrorAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"code\": 500,\n" +
                                        "\t\"status\": \"INTERNAL SERVER ERRROR\",\n" +
                                        "\t\"message\": \"Error occurred while processing\",\n" +
                                        "\t\"data\": {}\n" +
                                        "}")))).description("Internal Server Error");

        Components components = new Components();
        components.addResponses("AuthSuccessAPI", AuthSuccessAPI);
        components.addResponses("successApi", successAPI);
        components.addResponses("badRequestAPI", badRequestAPI);
        components.addResponses("unauthorizedAPI", unauthorizedAPI);
        components.addResponses("internalServerErrorAPI", internalServerErrorAPI);

        return new OpenAPI().components(components);
    }

//    @Bean
//    public GroupedOpenApi authenticationApi(){
//        String [] paths = {"/api/auth/**"};
//        return GroupedOpenApi.builder().group("Authentication").pathsToMatch(paths).build();
//    }


//    @Bean
//    public GroupedOpenApi accessApi(){
//        String [] paths = {"/api/access/**"};
//        return GroupedOpenApi.builder().group("Authorization & Access Control").pathsToMatch(paths).build();
//    }


}
