package test;

import annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunner {
    public static void runTests(Class<?> aClass) throws Exception {
        List<Method> methods = List.of(aClass.getDeclaredMethods());
        validate(methods);

        Object object = aClass.getConstructor().newInstance();
        Map<? extends Class<? extends Annotation>, List<Method>> methodMap = methods.stream()
                .flatMap(method -> Stream.of(method.getAnnotations())
                        .map(annotation -> Map.entry(method, annotation)))
                .collect(Collectors.groupingBy(e ->
                        e.getValue().annotationType(), Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        runBefore(aClass, methodMap.get(BeforeSuite.class));
        runAllSimpleTests(object,
                methodMap.get(Test.class),
                methodMap.get(BeforeTest.class),
                methodMap.get(AfterTest.class));
        runAfter(aClass, methodMap.get(AfterSuite.class));

        runParameterizedTest(object, methodMap.get(CsvSource.class));
    }

    private static void validate(List<Method> methods) {
        int staticAnnotationCnt = 0;
        for (Method method : methods) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType().equals(BeforeSuite.class) || annotation.annotationType().equals(AfterSuite.class)) {
                    staticAnnotationCnt++;
                }
                if (annotation.annotationType().equals(Test.class) &&
                        (((Test) annotation).priority() < 1 || ((Test) annotation).priority() > 10)) {
                    throw new RuntimeException("method " + method.getName() + " has inappropriate priority value on Test annotation");
                }
                if (Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Test.class) ||
                        !Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(BeforeSuite.class) ||
                        !Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(AfterSuite.class)) {
                    throw new RuntimeException("method " + method.getName() + " has inappropriate annotation");
                }
            }
        }
        if (staticAnnotationCnt != 2) {
            throw new RuntimeException("Inappropriate static annotations count");
        }
    }

    private static void runBefore(Class<?> c, List<Method> methods) {
        methods.forEach(lambdaWrapper((method) -> method.invoke(c)));
    }

    private static void runAllSimpleTests(Object object, List<Method> methods, List<Method> beforeMethods, List<Method> afterMethods) {
        methods.stream()
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
        if (methods.isEmpty()) {
            return;
        }
        Method method = methods.get(0);
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
