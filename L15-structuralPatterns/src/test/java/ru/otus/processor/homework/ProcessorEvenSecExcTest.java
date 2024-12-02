/**
 * @author administrator on 02.12.2024.
 */
package ru.otus.processor.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import ru.otus.model.Message;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessorEvenSecExcTest {

    private final LocalDateTime ld1 = LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 1);
    private final LocalDateTime ld2 = LocalDateTime.of(2024, Month.JANUARY, 1, 1, 1, 2);

    @Test
    @DisplayName("Нечётная секунда")
    void processAnOddSecond() {

	ProcessorEvenSecondExc processor = new ProcessorEvenSecondExc(ld1);

	var message = new Message.Builder(1L).build();

	Assertions.assertDoesNotThrow(() -> processor.process(message));
    }

    @Test
    @DisplayName("Чётная секунда")
    void processAnEvenSecond() {

	ProcessorEvenSecondExc processor = new ProcessorEvenSecondExc(ld2);

	var message = new Message.Builder(1L).build();

	Throwable exception = assertThrows(RuntimeException.class, () -> processor.process(message));
	assertEquals("ProcessorEvenSecondExc RuntimeException!", exception.getMessage());
    }
}
