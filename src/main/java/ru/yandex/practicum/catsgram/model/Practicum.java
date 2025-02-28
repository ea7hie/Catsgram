package ru.yandex.practicum.catsgram.model;
/*
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class Cat {
    private String color;
    private int age;

    @Override
    public String toString() {
        return "Cat{" +
                "color='" + color + '\'' +
                ", age=" + age +
                '}';
    }
}

public class Practicum {
    public static void main(String[] args) {
        final Cat cat = new Cat();
        cat.setColor("black");
        cat.setAge(5);
        System.out.println(cat);
    }
}*/
/*
import lombok.ToString;

@ToString
class Person {
    private String firstName;
    private String lastName;
    private int age;
    private String phone;

    public Person(String firstName, String lastName, int age, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
    }
}

public class Practicum {
    public static void main(String[] args) {
        Person roman = new Person("Roman", "Igorev", 38, "+78889991234");
        System.out.println(roman);
    }
}*/
/*
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class Person {
    private String firstName;
    private String lastName;
    private int age;
    @EqualsAndHashCode.Exclude
    private String phone;

    public Person(String firstName, String lastName, int age, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
    }
}

public class Practicum {
    public static void main(String[] args) {
        Person roman1 = new Person("Roman", "Igorev", 38, "+78889991234");
        Person roman2 = new Person("Roman", "Igorev", 38, "");
        if(roman1.equals(roman2)) {
            System.out.println("Это один и тот же человек");
        } else {
            System.out.println("Это разные люди");
        }
    }
}*/
/*
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public class Practicum {

    @Data
    @Builder
    public static class House {
        @NonNull
        private int wallWidth;
        @NonNull
        private int wallLength;
        @NonNull
        private String wallsColor;

        @Builder.Default
        private int wallHeight = 2500;
        @Builder.Default
        private int numberOfWindows = 6;
        @Builder.Default
        private int numberOfFloors = 1;

        public int calculateTotalArea() {
            return wallWidth * wallLength * numberOfFloors;
        }
    }

    public static void main(String[] args) {
        /*final House house = new House(15, 12, "red");
        house.setNumberOfFloors(2);*/

       /* final House house = House.builder()
                .wallWidth(15)
                .wallLength(12)
                .wallsColor("red")
                .numberOfFloors(2)
                .build();

        System.out.println(house);
        System.out.println("Общая площадь = " + house.calculateTotalArea());
    }
}*/
/*
import lombok.Value;
import lombok.Builder;

@Value
@Builder
class Point {
    int x;
    int y;
}

public class Practicum {
    public static void main(String[] args) {

        final Point point1 = Point.builder().x(0).build();
        System.out.println(point1);

        final Point point2 = new Point(0, 0);
        System.out.println(point2);
    }
}*/
/*
import lombok.Value;
import lombok.Builder;

@Value
@Builder(toBuilder = true)
class Point {
    int x;
    int y;
}

public class Practicum {
    public static void main(String[] args) {

        final Point point1 = Point.builder().x(0).y(0).build();
        System.out.println(point1);

        // используем метод toBuilder на уже созданном объекте
        // point1, чтобы на его основе создать новый объект
        // с другим значением в поле y
        final Point point2 = point1.toBuilder().y(3).build();
        System.out.println(point2);
    }
}*/