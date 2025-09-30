package io.miinhho.recomran.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("recomran-openapi")
                .version("1.0.0")
                .description("Recomran API Documentation")
            )
    }
}