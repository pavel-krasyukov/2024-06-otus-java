/**
 * @author administrator on 06.01.2025.
 */
package ru.otus;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Main {

    private static final Lock lock = new ReentrantLock();
    private static volatile boolean isThreadOneTurn = true; // true, если первый поток должен выполнять свою задачу

    private static final String THREAD1_NAME = "Поток 1";
    private static final String THREAD2_NAME = "Поток 2";

    public static void main(String[] args) {
	Thread thread1 = new Thread(new NumberPrinter(THREAD1_NAME));
	Thread thread2 = new Thread(new NumberPrinter(THREAD2_NAME));

	thread1.start();
	thread2.start();
    }

    static class NumberPrinter implements Runnable {
	private String threadName;

	public NumberPrinter(String threadName) {
	    this.threadName = threadName;
	}

	@Override
	public void run() {
	    printNumbers(1, 10);
	}

	private void printNumbers(int start, int end) {
	    while (true) {
		lock.lock(); // блокируем
		try {
		    //Поток1
		    if (THREAD1_NAME.equals(threadName) && isThreadOneTurn) {
			System.out.print(THREAD1_NAME+": ");
			for (int i = start; i <= end; i++) {
			    System.out.print(i + " ");
			}
			for (int i = end - 1; i >= start; i--) {
			    System.out.print(i + " ");
			}
			System.out.println();
			isThreadOneTurn = false; // Меняем флаг
		    // Поток2
		    } else if (THREAD2_NAME.equals(threadName) && !isThreadOneTurn) {
			System.out.print(THREAD2_NAME+": ");
			for (int i = start; i <= end; i++) {
			    System.out.print(i + " ");
			}
			for (int i = end - 1; i >= start; i--) {
			    System.out.print(i + " ");
			}
			System.out.println();
			isThreadOneTurn = true;
		    }
		} finally {
		    lock.unlock(); // разблокируем
		}

		try {
		    Thread.sleep(100); // задержка, чтобы было легче читать вывод
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt();
		}
	    }
	}
    }
}
