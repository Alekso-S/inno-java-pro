package test;

import annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TestRunner {
    public static void runTests(Class<?> c) throws Exception {
        List<Method> methods = List.of(c.getDeclaredMethods());
        validate(methods);

        Object object = c.getConstructor().newInstance();

        runBefore(c, methods);
        runAllSimpleTests(object, methods);
        runAfter(c, methods);

        runParameterizedTest(object, methods);
    }

    private static void validate(List<Method> methods) {
        validateAnnotationMatching(methods);
        validateTestPriorityValue(methods);
        validateStaticAnnotationUniqueness(methods);
    }

    private static void validateStaticAnnotationUniqueness(List<Method> methods) {
        methods.stream()
                .flatMap(m -> Arrays.stream(m.getAnnotations()))
                .filter(a -> List.of(
                        BeforeSuite.class,
                        AfterSuite.class
                ).contains(a.annotationType()))
                .collect(Collectors.groupingBy(annotation -> annotation, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .forEach(e -> {
                    throw new RuntimeException("Excessive annotations count: " + e.getKey());
                });
    }

    private static void validateTestPriorityValue(List<Method> methods) {
        methods.stream()
                .filter(method -> method.isAnnotationPresent(Test.class))
                .filter(method ->
                        method.getAnnotation(Test.class).priority() < 1 ||
                                method.getAnnotation(Test.class).priority() > 10)
                .forEach(method -> {
                    throw new RuntimeException("method " + method.getName() + " has inappropriate priority value on Test annotation");
                });
    }

    private static void validateAnnotationMatching(List<Method> methods) {
        methods.stream()
                .filter(method ->
                        Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Test.class) ||
                                !Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(BeforeSuite.class) ||
                                !Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(AfterSuite.class))
                .forEach(method -> {
                    throw new RuntimeException("method " + method.getName() + " has inappropriate annotation");
                });
    }

    private static void runBefore(Class<?> c, List<Method> methods) {
        methods.stream()
                .filter(method -> method.isAnnotationPresent(BeforeSuite.class))
                .forEach(lambdaWrapper((method) -> method.invoke(c)));
    }

    private static void runAllSimpleTests(Object object, List<Method> methods) {
        List<Method> beforeMethods = methods.stream().filter(m -> m.isAnnotationPresent(BeforeTest.class)).toList();
        List<Method> afterMethods = methods.stream().filter(m -> m.isAnnotationPresent(AfterTest.class)).toList();
        methods.stream()
                .filter(method -> method.isAnnotationPresent(Test.class))
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()))
                .forEachOrdered(lambdaWrapper(method -> {
                    beforeMethods.forEach(lambdaWrapper(method1 -> method1.invoke(object)));
                    method.invoke(object);
                    afterMethods.forEach(lambdaWrapper(method1 -> method1.invoke(object)));
                }));
    }

    private static void runAfter(Class<?> c, List<Method> methods) {
        methods.stream()
                .filter(method -> method.isAnnotationPresent(AfterSuite.class))
                .forEach(lambdaWrapper((method) -> method.invoke(c)));
    }

    private static void runParameterizedTest(Object object, List<Method> methods) throws Exception {
        Method method = methods.stream()
                .filter(m -> m.isAnnotationPresent(CsvSource.class))
                .findFirst().orElse(null);
        if (method == null) {
            return;
        }

        String[] stringParams = method.getAnnotation(CsvSource.class).value().split(", ");
        ArrayList<Object> params = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            switch (parameters[i].getType().getName()) {
                case "java.lang.String":
                    params.add(stringParams[i]);
                    break;
                case "int":
                    params.add(Integer.parseInt(stringParams[i]));
                    break;
                case "boolean":
                    params.add(Boolean.parseBoolean(stringParams[i]));
                    break;
                default:
                    throw new RuntimeException("Unsupported parameter type: " + parameters[i].getType().getName());
            }
        }

        method.invoke(object, params.toArray());
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    static <T, E extends Exception> Consumer<T> lambdaWrapper(
            ThrowingConsumer<T, E> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
