package test;

import annotation.*;

public class TestCases {
    @BeforeSuite
    public static void start() {
        System.out.println("start");
    }

    @AfterSuite
    public static void finish() {
        System.out.println("finish");
    }


    @BeforeTest
    public void runBefore() {
        System.out.println("before");
    }

    @AfterTest
    public void runAfter() {
        System.out.println("after");
    }

    @Test(priority = 10)
    public void case1() {
        System.out.println("case1");
    }

    @Test
    public void case2() {
        System.out.println("case2");
    }

    @Test(priority = 1)
    public void case3() {
        System.out.println("case3");
    }

    @CsvSource("10, Java, 20, true")
    public void paramCase(int a, String b, int c, boolean d) {
        System.out.printf("%d years of %s coding is not enough, only after %d you'll become a %s coder%n", a, b, c, d);
    }
}
