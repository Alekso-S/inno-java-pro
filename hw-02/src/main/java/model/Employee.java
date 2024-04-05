package model;

import enums.Position;

public class Employee {
    private String name;
    private Integer age;
    private Position position;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", position=" + position +
                '}';
    }
}
