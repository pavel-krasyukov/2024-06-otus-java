package ru.otus.listener.homework;

import ru.otus.listener.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> historyMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
	historyMap.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
	return Optional.ofNullable(historyMap.get(id));
    }
}
