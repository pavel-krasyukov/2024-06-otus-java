/**
 * @author administrator on 25.11.2024.
 */
package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import java.time.LocalDateTime;

public class ProcessorEvenSecondExc implements Processor {
    private final LocalDateTime dateTimeProvider;

    public ProcessorEvenSecondExc(LocalDateTime dateTimeProvider) {
	this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
	int sec = dateTimeProvider.getSecond();
	if (sec % 2 == 0) {
	    throw new RuntimeException("ProcessorEvenSecondExc RuntimeException!");
	}
	return message;
    }
}
