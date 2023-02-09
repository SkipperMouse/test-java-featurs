package space.gavinklfong.demo.streamapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import space.gavinklfong.demo.streamapi.models.Customer;
import space.gavinklfong.demo.streamapi.models.Order;
import space.gavinklfong.demo.streamapi.models.Product;
import space.gavinklfong.demo.streamapi.repos.CustomerRepo;
import space.gavinklfong.demo.streamapi.repos.OrderRepo;
import space.gavinklfong.demo.streamapi.repos.ProductRepo;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@DataJpaTest
public class MyExercise {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    @DisplayName("Obtain a list of product with category = \"Books\" and price > 100")
    public void exercise1() {
        productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("books"))
                .filter(p -> p.getPrice().compareTo(100.0) > 0)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Obtain a list of product with category = \"Books\" and price > 100 (using Predicate chaining for filter)")
    public void exercise1a() {
        Predicate<Product> category = p -> p.getCategory().equalsIgnoreCase("books");
        Predicate<Product> price = p -> p.getPrice().compareTo(100.0) > 0;

        productRepo.findAll()
                .stream()
                .filter(category.and(price))
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Obtain a list of order with product category = \"Baby\"")
    public void exercise2() {
        List<Order> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getProducts().stream()
                        .anyMatch(p -> p.getCategory().equalsIgnoreCase("baby")))
                .collect(Collectors.toList());

        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("Obtain a list of product with category = “Toys” and then apply 10% discount\"")
    public void exercise3() {
        productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("toys"))
                .map(p -> p.withPrice(p.getPrice() * 0.9))
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021")
    public void exercise4() {
        Predicate<Order> tier = o -> o.getCustomer().getTier().equals(2);
        Predicate<Order> before = o -> o.getOrderDate().isAfter(LocalDate.of(2021, 1, 31));
        Predicate<Order> after = o -> o.getOrderDate().isBefore(LocalDate.of(2021, 4, 1));

        List<Product> result = orderRepo.findAll()
                .stream()
                .filter(tier.and(before).and(after))
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(result.size());
        result.forEach(System.out::println);
    }

    @Test
    @DisplayName("Get the 3 cheapest products of \"Books\" category")
    public void exercise5() {
        productRepo.findAll()
                .stream()
                .filter(p -> p.getCategory().equalsIgnoreCase("books"))
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .limit(3)
                .forEach(System.out::println);

    }

    @Test
    @DisplayName("Get the 3 most recent placed order")
    public void exercise6() {
        orderRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Get a list of products which was ordered on 15-Mar-2021")
    public void exercise7() {
        List<Product> result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());


        System.out.println(result.size());
    }

    @Test
    @DisplayName("Calculate the total lump of all orders placed in Feb 2021")
    public void exercise8() {

        Predicate<Order> month = o -> o.getOrderDate().getMonth().equals(Month.FEBRUARY);
        Predicate<Order> year = o -> o.getOrderDate().getYear() == 2021;

        Double count = orderRepo.findAll()
                .stream()
                .filter(year.and(month))
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        System.out.println(count);
    }

    //todo reduce
    @Test
    @DisplayName("Calculate the total lump of all orders placed in Feb 2021 (using reduce with BiFunction)")
    public void exercise8a() {


        Double d = orderRepo.findAll()
                .stream()
                .filter(order -> order.getOrderDate().getMonth().equals(Month.FEBRUARY))
                .filter(order -> order.getOrderDate().getYear() == 2021)
                .flatMap(o -> o.getProducts().stream())
                .reduce(0.0, (acc, product) -> acc + product.getPrice(), Double::sum);

        System.out.println(d);

    }

    //todo повторить в ответе неправильно рассчитано
    @Test
    @DisplayName("Calculate the average price of all orders placed on 15-Mar-2021")
    public void exercise9() {
        Double result = orderRepo.findAll()
                .stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .mapToDouble(o -> o.getProducts()
                        .stream()
                        .reduce(0.0, (acc, product) -> acc + product.getPrice(), Double::sum))
                .average()
                .orElse(0.0);

        System.out.println(result);
    }

    @Test
    @DisplayName("Obtain statistics summary of all products belong to \"Books\" category")
    public void exercise10() {
        DoubleSummaryStatistics result = productRepo.findAll()
                .stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                        .mapToDouble(Product::getPrice)
                                .summaryStatistics();


        System.out.println(result);
    }

    @Test
    @DisplayName("Obtain a mapping of order id and the order's product count")
    public void exercise11() {
        Map<Long, Integer> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts().size()
                ));
    }

    @Test
    @DisplayName("Obtain a data map of customer and list of orders")
    public void exercise12() {
        Map<Customer, List<Order>> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

    }

    //todo try this one again !!!
    @Test
    @DisplayName("Obtain a data map of customer_id and list of order_id(s)")
    public void exercise12a() {
        Map<Long, List<Long>> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCustomer().getId(),
                        Collectors.mapping(Order::getId,
                                Collectors.toList())));
    }

    @Test
    @DisplayName("Obtain a data map with order and its total price")
    public void exercise13() {
        Map<Order, Double> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        order -> order.getProducts()
                                .stream()
                                .mapToDouble(Product::getPrice)
                                .sum()));


    }

    @Test
    @DisplayName("Obtain a data map with order and its total price (using reduce)")
    public void exercise13a() {
        Map<Order, Double> result = orderRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.flatMapping(
                                order -> order.getProducts().stream(),
                                Collectors.reducing(0.0, Product::getPrice, Double::sum)
                        )
                ));
        result.entrySet().forEach(System.out::println);
    }

    @Test
    @DisplayName("Obtain a data map of product names by category")
    public void exercise14() {
        Map<String, List<String>> result = productRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(
                                Product::getName,
                                Collectors.toList())
                ));
        result.entrySet().stream().forEach(System.out::println);

    }

    //todo Couldn't do it second time
    @Test
    @DisplayName("Get the most expensive product per category")
    void exercise15() {
        Map<String, Optional<Product>> result = productRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.maxBy(Comparator.comparing(Product::getPrice))
                ));
    }

    @Test
    @DisplayName("Get name of the most expensive product per category")
    void exercise15a() {
        Map<String, Product> result = productRepo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(Collectors.maxBy(
                                        Comparator.comparingDouble(Product::getPrice)),
                                optional -> optional.orElse(null)

                        )));
        System.out.println(result);

    }
}
