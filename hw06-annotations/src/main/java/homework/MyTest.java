/**
 * @author administrator on 09.09.2024.
 */
package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class MyTest {
    @Before
    public void setup() {
	System.out.println("setup....");
    }

    @Test
    public void test() {
	System.out.println("test....");
    }

    @After
    public void testAfterOne() {
	System.out.println("testAfterOne...");
    }

    @After
    public void testAfterTwo() {
	System.out.println("testAfterTwo...");
    }
}
