package ru.calculator;

/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m

Запуск тестов с разными значениями heap: L08-gc\testSizeMemory.sh
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
	logMemoryUsage(delta);

    }

    public static void logMemoryUsage(Long spentTime) {
	// Получение текущего размера хипа
	long heapSize = Runtime.getRuntime().totalMemory();
	// Получение списка сборщиков мусора
	List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
	// Сборка информации о сборщиках мусора
	String gcInfo = gcBeans.stream().map(gcBean -> gcBean.getName() + " ").collect(Collectors.joining());
	MemoryUsageStatistics memoryUsageStatistics = new MemoryUsageStatistics(gcInfo.trim(), heapSize / (1024 * 1024),
			Double.valueOf(spentTime / 1000));
	try {
	    JsonLogMemoryUsage.writeLogMemoryUsage(memoryUsageStatistics);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
