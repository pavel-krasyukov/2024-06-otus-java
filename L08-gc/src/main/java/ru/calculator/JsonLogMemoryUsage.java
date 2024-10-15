/**
 * @author administrator on 13.10.2024.
 */
package ru.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class JsonLogMemoryUsage {

    private static final String FILE_PATH = "L08_test_memory.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeLogMemoryUsage(MemoryUsageStatistics newData) throws IOException {
	if (newData == null) {
	    return;
	}
	File file = new File(FILE_PATH);
	if (file.exists()) {
	    String fileContent = Files.readString(file.toPath());
	    List<MemoryUsageStatistics> fileData;
	    if (fileContent.isEmpty() || fileContent.equals("[]")) {
		fileData = new ArrayList<>();
	    } else {
		fileData = objectMapper.readValue(file,
				objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, MemoryUsageStatistics.class));
	    }
	    fileData.add(newData);
	    objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, fileData);
	}
    }

}
