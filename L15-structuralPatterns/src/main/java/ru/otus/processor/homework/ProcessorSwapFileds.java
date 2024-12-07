/**
 * @author administrator on 25.11.2024.
 */
package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorSwapFileds implements Processor {
    @Override
    public Message process(Message message) {
	return message.toBuilder().field11(message.getField12()).field12(message.getField11()).build();
    }
}
