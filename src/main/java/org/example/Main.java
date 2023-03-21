package org.example;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5};
        List<Integer> list = toList(array);
        System.out.println("List: " + list);

        Fruit apple = new Apple(2.99);
        Fruit orange = new Orange(1.99);

        System.out.println("Apple: " + apple.getName() + " price: " + apple.getPrice());
        System.out.println("Orange: " + orange.getName() + " price: " + orange.getPrice());

        Box<Apple> appleBox = new Box<>();
        appleBox.addFruit(new Apple());
        System.out.println("Apple Box weight: " + appleBox.getWeight());

        Box<Orange> orangeBox = new Box<>();
        List<Orange> newOranges = new ArrayList<>();
        newOranges.add(new Orange());
        newOranges.add(new Orange());
        orangeBox.addFruits(newOranges);
        System.out.println("Orange Box weight: " + orangeBox.getWeight());

        Box<Fruit> mixedBox1 = new Box<>();
        mixedBox1.addFruit(new Apple());
        System.out.println("Mixed Box 1 weight: " + mixedBox1.getWeight());

        Box<Fruit> mixedBox2 = new Box<>();
        mixedBox2.addFruit(new Apple());
        System.out.println("Mixed Box 2 weight: " + mixedBox2.getWeight());
        System.out.println("Mixed Box 1 is equal to Mixed Box 2: " + mixedBox1.compare(mixedBox2));

        mixedBox1.merge(mixedBox2);
        System.out.println("Mixed Box 1 after merging with Mixed Box 2: " + mixedBox1.getWeight());

    }

    public static <T> List<T> toList(T[] array) {
        List<T> list = new ArrayList<>();
        for (T item : array) {
            list.add(item);
        }
        return list;
    }

    public static class Fruit {
        private String name;
        private double price;

        public Fruit(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static class Apple extends Fruit {
        public Apple() {
            super("Apple", 0.0);
        }

        public Apple(double price) {
            super("Apple", price);
        }
    }

    public static class Orange extends Fruit {
        public Orange() {
            super("Orange", 0.0);
        }

        public Orange(double price) {
            super("Orange", price);
        }
    }

    public static class Box<T extends Fruit> {
        private List<T> fruits;

        public Box() {
            fruits = new ArrayList<>();
        }

        public void addFruit(T fruit) {
            if (fruits.isEmpty() || fruits.get(0).getClass().equals(fruit.getClass())) {
                fruits.add(fruit);
            } else {
                throw new IllegalArgumentException("Cannot add fruit of different type to the box.");
            }
        }

        public void addFruits(List<T> newFruits) {
            for (T fruit : newFruits) {
                addFruit(fruit);
            }
        }
        public float getWeight() {
            if (fruits.isEmpty()) {
                return 0.0f;
            }

            float weight = 0.0f;
            Fruit fruit = fruits.get(0);
            if (fruit instanceof Apple) {
                weight = 1.0f;
            } else if (fruit instanceof Orange) {
                weight = 1.5f;
            }

            return weight * fruits.size();
        }

        public boolean compare(Box<?> otherBox) {
            return Math.abs(getWeight() - otherBox.getWeight()) < 0.0001f;
        }
        public void merge(Box<T> otherBox) {
            if (this == otherBox) {
                return;
            }
            if (this.fruits.isEmpty() || otherBox.fruits.isEmpty()) {
                this.fruits.addAll(otherBox.fruits);
                otherBox.fruits.clear();
                return;
            }
            if (this.fruits.get(0).getClass() == otherBox.fruits.get(0).getClass()) {
                this.fruits.addAll(otherBox.fruits);
                otherBox.fruits.clear();
            } else {
                throw new IllegalArgumentException("Cannot merge boxes of different fruit types.");
            }
        }
    }
}
