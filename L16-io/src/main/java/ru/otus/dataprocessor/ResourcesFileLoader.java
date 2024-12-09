package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import ru.otus.model.Measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
	this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
	List<Measurement> measurementList = new ArrayList<>();
	// читает файл, парсит и возвращает результат
	try (var jsonReader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
	    JsonArray jsonArray = jsonReader.readArray();
	    for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
		Measurement measurement = objectMapper.readValue(jsonObject.toString(), Measurement.class);
		measurementList.add(measurement);
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	return measurementList;
    }
}
