/**
 * @author administrator on 25.11.2024.
 */
package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSecondExc implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSecondExc(DateTimeProvider dateTimeProvider) {
	this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
	int sec = dateTimeProvider.localDateTime().getSecond();
	if (sec % 2 == 0) {
	    throw new RuntimeException("ProcessorEvenSecondExc RuntimeException!");
	}
	return message;
    }
}
