/**
 * @author administrator on 02.12.2024.
 */
package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorEvenSecExcTest {

    @Test
    @DisplayName("Нечётная секунда")
    void processAnOddSecond() {

	ProcessorEvenSecondExc processor = new ProcessorEvenSecondExc(() -> LocalDateTime.of(2000, Month.JANUARY, 1, 1, 1, 1));

	var message = new Message.Builder(1L).build();

	assertDoesNotThrow(() -> processor.process(message));
    }

    @Test
    @DisplayName("Чётная секунда")
    void processAnEvenSecond() {

	ProcessorEvenSecondExc processor = new ProcessorEvenSecondExc(() ->LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 2));

	var message = new Message.Builder(1L).build();

	Throwable exception = assertThrows(RuntimeException.class, () -> processor.process(message));
	assertEquals("ProcessorEvenSecondExc RuntimeException!", exception.getMessage());
    }
}
