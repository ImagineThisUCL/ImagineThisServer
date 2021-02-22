package com.ucl.imaginethisserver.Conf;

import com.google.gson.*;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Type;

/**
 * This config file is used to configure springfox-swagger2 to use Gson as serialization tool
 * Reference: https://stackoverflow.com/questions/53155161/how-to-use-google-gson-instead-of-the-default-jackson-of-the-spring-in-swagger
 */
@Configuration
public class GsonConfig {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Json.class, new SwaggerJsonTypeAdapter())
                .create();
    }

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        return new HttpMessageConverters(gsonHttpMessageConverter);
    }

    public static class SwaggerJsonTypeAdapter implements JsonSerializer<Json> {

        @Override
        public JsonElement serialize(Json json, Type type, JsonSerializationContext context) {
            return JsonParser.parseString(json.value());
        }
    }
}
