/**
 * @author administrator on 06.01.2025.
 */
package ru.otus;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean isThreadOneTurn = true;

    public static void main(String[] args) {
	Thread thread1 = new Thread(new NumberPrinter(1));
	Thread thread2 = new Thread(new NumberPrinter(2));

	thread1.start();
	thread2.start();
    }

    static class NumberPrinter implements Runnable {
	private final int threadId;

	public NumberPrinter(int threadId) {
	    this.threadId = threadId;
	}

	@Override
	public void run() {
	    while (true) {
		// Сначала печатаем от 1 до 10
		for (int i = 1; i <= 10; i++) {
		    printNumber(i);
		}
		// Затем печатаем от 9 до 1
		for (int i = 9; i >= 1; i--) {
		    printNumber(i);
		}
	    }
	}

	private void printNumber(int number) {
	    lock.lock();
	    try {
		while ((threadId == 1 && !isThreadOneTurn) || (threadId == 2 && isThreadOneTurn)) {
		    condition.await();
		}
		System.out.println("Поток " + threadId + ": " + number);
		isThreadOneTurn = !isThreadOneTurn; // Меняем очередь
		condition.signalAll(); // Пробуждаем другой поток
	    } catch (InterruptedException e) {
		Thread.currentThread().interrupt();
	    } finally {
		lock.unlock();
	    }
	}
    }
}
