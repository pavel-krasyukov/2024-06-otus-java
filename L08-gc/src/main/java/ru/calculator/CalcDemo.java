package ru.calculator;

/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m

--До оптимизации
1. Размер хипа 256 MB - 33 сек.
spend msec:33920, sec:33
21:09:59.491 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 256 MB
21:09:59.491 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 256 MB
21:09:59.491 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 248 MB
21:09:59.491 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

2. Размер хипа 512 MB - 33 сек.
21:13:24.132 [main] INFO ru.calculator.CalcDemo -- spend msec:33085, sec:33
21:13:24.150 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 512 MB
21:13:24.151 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 512 MB
21:13:24.151 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 237 MB
21:13:24.151 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

3. Размер хипа 1024 MB - 33 сек.
21:15:02.775 [main] INFO ru.calculator.CalcDemo -- spend msec:33363, sec:33
21:15:02.800 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 1024 MB
21:15:02.800 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 1024 MB
21:15:02.800 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 509 MB
21:15:02.800 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

4. Размер хипа 1536 MB - 30 сек.
21:16:45.618 [main] INFO ru.calculator.CalcDemo -- spend msec:30790, sec:30
21:16:45.639 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 1536 MB
21:16:45.640 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 1536 MB
21:16:45.640 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 1438 MB
21:16:45.640 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

5. Размер хипа 2048 MB - 28 сек.
21:18:13.884 [main] INFO ru.calculator.CalcDemo -- spend msec:28618, sec:28
21:18:13.902 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 2048 MB
21:18:13.902 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 2048 MB
21:18:13.902 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 1790 MB
21:18:13.902 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

6. Размер хипа 4096 MB - 26 сек.
21:19:55.807 [main] INFO ru.calculator.CalcDemo -- spend msec:26130, sec:26
21:19:55.828 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 4096 MB
21:19:55.828 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 4096 MB
21:19:55.828 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 1706 MB
21:19:55.828 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

7. Размер хипа 8192 MB - 19 сек.
21:22:06.329 [main] INFO ru.calculator.CalcDemo -- spend msec:19329, sec:19
21:22:06.352 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 8192 MB
21:22:06.352 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 8192 MB
21:22:06.352 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 6272 MB
21:22:06.352 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

8. Размер хипа 16384 MB - 20 сек.
21:25:08.006 [main] INFO ru.calculator.CalcDemo -- spend msec:20150, sec:20
21:25:08.026 [main] INFO ru.calculator.CalcDemo -- Current heap size (totalMemory): 16384 MB
21:25:08.026 [main] INFO ru.calculator.CalcDemo -- Maximum heap size (maxMemory): 16384 MB
21:25:08.026 [main] INFO ru.calculator.CalcDemo -- Free heap size (freeMemory): 10401 MB
21:25:08.026 [main] INFO ru.calculator.CalcDemo -- Garbage collectors: G1 Young Generation G1 Old Generation

ПОСЛЕ ОПТИМИЗАЦИИ (использование примитивных типов int вместо Integer)

1. Размер хипа 256 MB - 5 сек.
21:37:19.161 [main] INFO ru.calculator.CalcDemo -- spend msec:5565, sec:5

2. Размер хипа 512 MB - 5 сек.
21:39:56.837 [main] INFO ru.calculator.CalcDemo -- spend msec:5852, sec:5

3. Размер хипа 1024 MB - 5 сек.
21:40:34.108 [main] INFO ru.calculator.CalcDemo -- spend msec:5503, sec:5

4. Размер хипа 1536 MB - 5 сек.
21:41:31.146 [main] INFO ru.calculator.CalcDemo -- spend msec:5522, sec:5

5. Размер хипа 2048 MB - 5 сек.
21:42:08.479 [main] INFO ru.calculator.CalcDemo -- spend msec:5730, sec:5

6. Размер хипа 4096 MB - 6 сек.
21:43:44.638 [main] INFO ru.calculator.CalcDemo -- spend msec:6009, sec:6

7. Размер хипа 8192 MB - 6 сек.
21:44:24.608 [main] INFO ru.calculator.CalcDemo -- spend msec:6870, sec:6

8. Размер хипа 16384 MB - 7 сек.
21:45:00.375 [main] INFO ru.calculator.CalcDemo -- spend msec:7211, sec:7

*/

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalcDemo {
    private static final Logger log = LoggerFactory.getLogger(CalcDemo.class);

    public static void main(String[] args) {
	long counter = 500_000_000;
	var summator = new Summator();
	long startTime = System.currentTimeMillis();

	for (var idx = 0; idx < counter; idx++) {
	    var data = new Data(idx);
	    summator.calc(data);

	    if (idx % 10_000_000 == 0) {
		log.info("{} current idx:{}", LocalDateTime.now(), idx);
	    }
	}

	long delta = System.currentTimeMillis() - startTime;
	log.info("PrevValue:{}", summator.getPrevValue());
	log.info("PrevPrevValue:{}", summator.getPrevPrevValue());
	log.info("SumLastThreeValues:{}", summator.getSumLastThreeValues());
	log.info("SomeValue:{}", summator.getSomeValue());
	log.info("Sum:{}", summator.getSum());
	log.info("spend msec:{}, sec:{}", delta, (delta / 1000));
	logMemoryUsage();
    }

    public static void logMemoryUsage() {
	// Получение текущего размера хипа
	long heapSize = Runtime.getRuntime().totalMemory();
	long maxHeapSize = Runtime.getRuntime().maxMemory();
	long freeHeapSize = Runtime.getRuntime().freeMemory();

	// Получение списка сборщиков мусора
	List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

	// Сборка информации о сборщиках мусора
	String gcInfo = gcBeans.stream().map(gcBean -> gcBean.getName() + " ").collect(Collectors.joining());

	// Вывод размеров и информации о сборщиках мусора в консоль
	log.info("Current heap size (totalMemory): {} MB", heapSize / (1024 * 1024));
	log.info("Maximum heap size (maxMemory): {} MB", maxHeapSize / (1024 * 1024));
	log.info("Free heap size (freeMemory): {} MB", freeHeapSize / (1024 * 1024));
	log.info("Garbage collectors: {}", gcInfo.trim());
    }
}
