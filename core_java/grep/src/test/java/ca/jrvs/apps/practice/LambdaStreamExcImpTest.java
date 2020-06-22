package ca.jrvs.apps.practice;

import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LambdaStreamExcImpTest {

    private LambdaStreamExcImp lsei;
    ByteArrayOutputStream baos;


    @org.junit.Before
    public void setUp() throws Exception {
        lsei = new LambdaStreamExcImp();
        baos = new ByteArrayOutputStream();

    }

    @org.junit.Test
    public void createStrStream() {
        System.out.println("Test Case: Test createStrStream method.");
        String expected = "Hello, World!";
        Assert.assertEquals(expected, lsei.createStrStream("Hello", ", ", "World!").collect(Collectors.joining("")));
    }

    @org.junit.Test
    public void toUpperCase() {
        System.out.println("Test case: Test the toUpperCase method.");
        String expected = "HELLO, WORLD!";
        assertEquals(expected, lsei.toUpperCase("hello, ", "world!").collect(Collectors.joining("")));
    }

    @org.junit.Test
    public void filter() {
        System.out.println("Test case: Test the filter method.");
        String expected = "C#Python";
        Stream<String> strStream = lsei.createStrStream("C#", "Java", "Python");
        assertEquals(expected, lsei.filter(strStream, ".*a.*").collect(Collectors.joining("")));
    }

    @org.junit.Test
    public void createIntStream() {
        System.out.println("Test case: Test the createIntStream method.");
        // expected = 12;
        //IntStream intStream = lsei.createIntStream(new int[] {2, 4, 6});
        //assertEquals(12, intStream.sum());
        assertEquals(12, lsei.createIntStream(new int[] {2, 4, 6}).sum());

    }

    @org.junit.Test
    public void toList() {
        System.out.println("Test case: Test the toList method.");
        assertEquals(Arrays.asList("C#", "Java", "Python"), lsei.toList(lsei.createStrStream("C#", "Java", "Python")));
    }

    @org.junit.Test
    public void squareRootIntStream() {
        System.out.println("Test case: Test the squareRootIntStream method.");
        assertEquals(14, lsei.squareRootIntStream(lsei.createIntStream(new int[]{ 4, 9, 16, 25 })).sum(), 0.0);
    }

    @org.junit.Test
    public void getOdd() {
        System.out.println("Test case: Test the getOdd method.");
        assertEquals(9 , lsei.getOdd(lsei.createIntStream(1, 5)).sum());
    }

    @org.junit.Test
    public void getLambdaPrinter() {
        System.out.println("Test case: Test the getLambdaPrinter method.");
        System.setOut(new PrintStream(baos));
        Consumer<String> consumer = lsei.getLambdaPrinter("C#, ", "Python");
        consumer.accept("Java, ");

        assertEquals("C#, Java, Python", baos.toString());
    }

    @org.junit.Test
    public void printMessages() {
        System.out.println("Test case: Test the printMessages method.");

        System.setOut(new PrintStream(baos));
        lsei.printMessages(new String[] {" C# ", " Java ", " Python "}, lsei.getLambdaPrinter("Programming", "Language "));
        assertEquals("Programming C# Language Programming Java Language Programming Python Language ", baos.toString());
    }

    @org.junit.Test
    public void printOdd() {
        System.out.println("Test case: Test the printOdd method.");
        System.setOut(new PrintStream(baos));
        lsei.printOdd(lsei.createIntStream(new int[] { 1, 2, 3, 4, 5 }), lsei.getLambdaPrinter("Odd: ", "\n"));
        assertEquals("Odd: 1\nOdd: 3\nOdd: 5\n", baos.toString());
    }

    @org.junit.Test
    public void flatNestedInt() {
        System.out.println("Test case: Test the flatNestedInt method.");

        assertEquals(new Integer(30), lsei.flatNestedInt(Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4)).stream()).reduce(0, (total, current) -> total + current));
    }
}