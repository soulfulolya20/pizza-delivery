package org.example.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;

/**
 * Класс сериализации/десериализации дат.
 */
@Slf4j
public class CustomDateSerializer {

    private static Object deserialize(JsonParser jsonParser, BiFunction<LocalDateTime, ZoneOffset, Object> biFunction)
            throws IOException {
        String date = jsonParser.getValueAsString();
        LocalDateTime localDateTime;
        log.debug("deserialize: {}", date);
        if (date.isEmpty()) {
            return null;
        }
        ZoneOffset offset = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());

        if (NumberUtils.isParsable(date)) {
            localDateTime = LocalDateTime.ofEpochSecond(Long.parseLong(date), 0, offset);
            return biFunction.apply(localDateTime, offset);
        }

        // todo: сделать нормально?
        try {
            date = String.valueOf(OffsetDateTime.parse(date).toEpochSecond());
        } catch (Exception e) {
            try {
                date = String.valueOf(LocalDateTime.parse(date).toEpochSecond(offset));
            } catch (Exception e1) {
                date = String.valueOf(LocalDate.parse(date).atStartOfDay().toEpochSecond(offset));
            }
        }

        localDateTime = LocalDateTime.ofEpochSecond(Long.parseLong(date), 0, offset);
        return biFunction.apply(localDateTime, offset);
    }

    /**
     * OffsetDateTimeSerializer.
     */
    public static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

        @Override
        public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (isNull(offsetDateTime)) {
                return;
            }
            long toEpochSecond = offsetDateTime.toLocalDateTime().atZone(ZoneId.systemDefault()).toEpochSecond();
            jsonGenerator.writeString(String.valueOf(toEpochSecond));
        }
    }

    /**
     * OffsetDateTimeDeserializer.
     */
    public static class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

        @Override
        public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            return (OffsetDateTime) CustomDateSerializer.deserialize(jsonParser, OffsetDateTime::of);
        }
    }

    /**
     * LocalDateSerializer.
     */
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (isNull(localDate)) {
                return;
            }
            long toEpochSecond = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toEpochSecond();
            jsonGenerator.writeString(String.valueOf(toEpochSecond));
        }
    }

    /**
     * LocalDateDeserializer.
     */
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            return (LocalDate) CustomDateSerializer.deserialize(jsonParser, (localDateTime, zoneOffset) ->
                    localDateTime.toLocalDate());
        }
    }

    /**
     * LocalDateTimeSerializer.
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (isNull(localDateTime)) {
                return;
            }
            long toEpochSecond = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
            jsonGenerator.writeString(String.valueOf(toEpochSecond));
        }
    }

    /**
     * LocalDateTimeDeserializer.
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            return (LocalDateTime) CustomDateSerializer.deserialize(jsonParser, (localDateTime, zoneOffset) ->
                    localDateTime);
        }
    }
}
