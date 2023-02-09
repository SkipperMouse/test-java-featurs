package space.gavinklfong.demo.streamapi.excercise;

import java.util.*;
import java.util.stream.Collectors;

public class Task1 {
    public static List<Product> products;

    static {
        products = new ArrayList<>();
        products.add(new Product("Crime and punishment", "Books", 6560L));
        products.add(new Product("Captain's daughter", "Books", 2300L));
        products.add(new Product("Pasta", "Grocery", 2000L));
        products.add(new Product("Sausages", "Grocery", 1800L));
    }

    // right answer:
    //    Grocery=Product{name='Pasta', category='Grocery', price=2000}
    //    Books=Product{name='Crime and punishment', category='Books', price=6560}

    public static void main(String[] args) {

        //НАЙТИ САМЫЙ ДОРОГОЙ ПРОДУКТ В КАЖДОЙ КАТЕГОРИИ
   

        //printResult(result);
    }

    private static void printResult(Map<String, Product> result) {
        result.entrySet().forEach(System.out::println);
    }


}

record Product(String name, String category, Long price) {
    public Product() {
        this("", "", 0L);
    }
}


