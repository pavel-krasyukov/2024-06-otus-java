#!/bin/bash

#переходим в корень проекта
cd "$(dirname "$0")/.."
#создаем json куда будем писать статистику
filename="L08_test_memory.json"
echo "[]" > $filename
#путь где находится толстый jar
JAVA_APP_JAR="L08-gc/build/libs/L08-gc-all.jar"
#в цикле для разных размеров heap вызываем толстый jar
for memory in "256m" "512m" "1g" "2g" "4g" "8g" "16g"; do
    echo "Testing with memory size: $memory"
    #java -Xms$memory -Xmx$memory -cp "$CLASSPATH" ru.calculator.CalcDemo
    java -Xms$memory -Xmx$memory -jar "$JAVA_APP_JAR"
done
