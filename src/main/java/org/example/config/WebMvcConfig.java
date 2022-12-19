package org.example.config;

import org.example.serialization.CustomDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(converter);
                converters.add(jsonConverter());
                break;
            }
        }
    }

    /**
     * Json converter.
     */
    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter() {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

        builder.serializerByType(LocalDate.class, new CustomDateSerializer.LocalDateSerializer());
        builder.deserializerByType(LocalDate.class, new CustomDateSerializer.LocalDateDeserializer());

        builder.serializerByType(LocalDateTime.class, new CustomDateSerializer.LocalDateTimeSerializer());
        builder.deserializerByType(LocalDateTime.class, new CustomDateSerializer.LocalDateTimeDeserializer());

        builder.serializerByType(OffsetDateTime.class, new CustomDateSerializer.OffsetDateTimeSerializer());
        builder.deserializerByType(OffsetDateTime.class, new CustomDateSerializer.OffsetDateTimeDeserializer());

        return new MappingJackson2HttpMessageConverter(builder.build());
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
