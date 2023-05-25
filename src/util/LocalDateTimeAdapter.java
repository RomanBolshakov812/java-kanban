package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter formatterReader = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), formatterReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        if (value == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(value.format(formatterWriter));
        }
    }
}
/*
Приветствую, Сергей!
Цитата из ревью:
"Обрати внимание, что у тебя падают практически все тесты с ошибкой сериализации/десерилиазации полей
 типа LocalDateType, нужно написать адаптер для gson'a, чтобы этих ошибок избежать."

 Проблема в том, что у меня все тесты работали. Не знаю почему так. Если бы они "падали" - конечно
 я бы обратил внимание))
 */