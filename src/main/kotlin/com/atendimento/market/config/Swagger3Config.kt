package com.atendimento.market.config

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuração do Swagger 3 para agrupar os endpoints relacionados à API pública da aplicação
 */

@Configuration
class Swagger3Config {
    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("atendimento-market-public")
            .pathsToMatch("/categories/**", "/products/**", "/cart/**", "/checkout/**")
            .build()
    }
}
