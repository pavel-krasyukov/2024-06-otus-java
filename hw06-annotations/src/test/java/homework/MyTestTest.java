package homework;

import homework.annotations.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author administrator on 09.09.2024.
 */

public class MyTestTest {
    @Test
    @DisplayName("Запуск методов с аннотациями @Before, @Test, @After")
    public void test(){
        TestRunner.runTests("homework.MyTest");
    }
}
