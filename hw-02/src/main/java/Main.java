import model.Employee;
import org.instancio.Instancio;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static enums.Position.ENGINEER;
import static org.instancio.Select.*;

public class Main {
    public static void main(String[] args) {
        getDistinct();
        getThirdMax();
        getThirdMaxUnique();
        getOldestEngineersNames();
        getAverageEngineersAge();
        getLongestWordFromWords();
        getWordsCount();
        getSortedWordStream();
        getLongestWordFromStrings();
    }

    private static void getDistinct() {
        System.out.println("1. Distinct");
        Instancio.ofList(Integer.class)
                .size(10)
                .generate(allInts(), gen -> gen.ints().range(1, 5))
                .create().stream()
                .peek(i -> System.out.println("in: " + i))
                .toList().stream()
                .distinct()
                .forEach(i -> System.out.println("out:" + i));
        System.out.println();
    }

    private static void getThirdMax() {
        System.out.println("2. Third max");
        Instancio.ofList(Integer.class)
                .size(10)
                .generate(allInts(), gen -> gen.ints().range(1, 10))
                .create().stream()
                .peek(i -> System.out.println("in: " + i))
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .sorted()
                .findFirst().ifPresent(i -> System.out.println("out: " + i));
        System.out.println();
    }

    private static void getThirdMaxUnique() {
        System.out.println("3. Third max unique");
        Instancio.ofList(Integer.class)
                .size(10)
                .generate(allInts(), gen -> gen.ints().range(1, 10))
                .create().stream()
                .peek(i -> System.out.println("in: " + i))
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .sorted()
                .findFirst().ifPresent(i -> System.out.println("out: " + i));
        System.out.println();
    }

    private static void getOldestEngineersNames() {
        System.out.println("4. Oldest engineers names");
        Instancio.ofList(Employee.class)
                .size(10)
                .generate(field(Employee::getAge), gen -> gen.ints().range(18, 80))
                .create().stream()
                .peek(i -> System.out.println("in: " + i))
                .filter(e -> e.getPosition().equals(ENGINEER))
                .sorted(Comparator.comparing(Employee::getAge, Comparator.reverseOrder()))
                .limit(3)
                .forEach(e -> System.out.println("out: " + e.getName()));
        System.out.println();
    }

    private static void getAverageEngineersAge() {
        System.out.println("5. Average engineers age");
        Instancio.ofList(Employee.class)
                .size(10)
                .generate(field(Employee::getAge), gen -> gen.ints().range(18, 80))
                .create().stream()
                .peek(i -> System.out.println("in: " + i))
                .filter(e -> e.getPosition().equals(ENGINEER))
                .mapToInt(Employee::getAge)
                .average().ifPresent(i -> System.out.println("out: " + i));
        System.out.println();
    }

    private static void getLongestWordFromWords() {
        System.out.println("6. Longest word from words");
        Instancio.ofList(String.class)
                .size(10)
                .create().stream()
                .peek(i -> System.out.println("in: " + i))
                .max(Comparator.comparing(String::length)).ifPresent(s -> System.out.println("out: " + s));
        System.out.println();
    }

    private static void getWordsCount() {
        System.out.println("7. Words count");
        Instancio.ofList(String.class)
                .size(1)
                .generate(allStrings(), gen -> gen.text().loremIpsum())
                .create().stream()
                .peek(s -> System.out.println("in: " + s))
                .flatMap(string -> Stream.of(string.split(" ")))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet()
                .forEach(e -> System.out.println("out: " + e));
        System.out.println();
    }

    private static void getSortedWordStream() {
        System.out.println("8. Sorted word stream");
        Instancio.ofList(String.class)
                .size(10)
                .create().stream()
                .peek(s -> System.out.println("in: " + s))
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .forEach(e -> System.out.println("out: " + e));
        System.out.println();
    }

    private static void getLongestWordFromStrings() {
        System.out.println("9. Longest word from strings");
        Stream.generate(() -> String.join(" ", Instancio.ofList(String.class)
                        .size(5)
                        .create()))
                .limit(5)
                .peek(s -> System.out.println("in: " + s))
                .flatMap(string -> Stream.of(string.split(" ")))
                .max(Comparator.comparing(String::length)).ifPresent(s -> System.out.println("out: " + s));
        System.out.println();
    }
}
