package com.wanted.common

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    companion object {
        const val API_TITLE = "원티드 프리온보딩 3주차"
        const val API_DESCRIPTION = "금방주식회사 백엔드 입사과제 API 문서입니다."
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        return OpenAPI()
            .info(Info().title(API_TITLE).description(API_DESCRIPTION))
            .addServersItem(Server().url("http://localhost:8888").description("AUTH SERVER"))
            .addServersItem(Server().url("http://localhost:9999").description("RESOURCE SERVER"))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .security(listOf(io.swagger.v3.oas.models.security.SecurityRequirement().addList(securitySchemeName)))
    }
}