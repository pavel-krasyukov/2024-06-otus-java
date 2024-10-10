/**
 * @author administrator on 09.09.2024.
 */
package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class MyTest {
    @Before
    public void setup1() {
	System.out.println("setup1....");
    }

    @Before
    public void setup2() {
	System.out.println("setup2....");
    }

    @Test
    public void test1() {
	System.out.println("test1....");
    }

    @Test
    public void test2() {
	System.out.println("test2....");
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
