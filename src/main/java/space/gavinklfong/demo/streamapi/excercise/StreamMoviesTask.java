package space.gavinklfong.demo.streamapi.excercise;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamMoviesTask {
    public static void main(String[] args) {
        List<Movie> movies = init();

        /*
         * Вывести по жанрам фильмы, а так же их актеров. Фильмы должны быть с рейтингом выше 7.5.
         * Фильмы могут выводиться по нескольку раз в разных жанрах
         * Если по жанру нет фильмов с указанным рейтингом, категория всё равно должна выводиться
         * */

        Map<String, Map<Movie, List<Actor>>> moviesWithHighRatingAndItsActors = movies
                .stream()
                .flatMap(movie -> movie.genres().stream()
                        .map(genre -> new AbstractMap.SimpleEntry<>(genre, movie)))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.filtering(
                                entry -> entry.getValue().rating() > 7.5,
                                Collectors.groupingBy(
                                        AbstractMap.SimpleEntry::getValue,
                                        Collectors.flatMapping(
                                                entry -> entry.getValue().actors().stream(),
                                                Collectors.toList()
                                        )))
                ));


        printExercise(moviesWithHighRatingAndItsActors);


    }

    private static List<Movie> init() {
        Actor stallone = new Actor("Sylvester Stallone", 76);
        Actor schwarzenegger = new Actor("Arnold Schwarzenegger", 75);
        Actor patrick = new Actor("Robert Patrick", 64);
        Actor cena = new Actor("John Cena", 45);
        Actor dennehy = new Actor("Brian Dennehy", 81);
        Actor young = new Actor("Burt Young", 82);
        Actor chalamet = new Actor("Timothee Chalamet", 27);
        Actor zendaya = new Actor("Zendaya", 26);
        Actor holland = new Actor("Tom Holland", 26);
        Actor naceri = new Actor("Samy Naceri", 61);
        Actor diefenthal = new Actor("Frederic Diefenthal", 54);

        Movie rambo = new Movie("First Blood", 7.7, 1982, List.of(stallone, dennehy),
                List.of("Action", "Adventure", "Thriller"));
        Movie terminatorTwo = new Movie("Terminator 2: Judge Day", 8.6, 1991, List.of(schwarzenegger, patrick),
                List.of("Action", "Sci-Fi"));
        Movie peacemaker = new Movie("Peacemaker", 8.3, 2022, List.of(cena, patrick),
                List.of("Action", "Adventure", "Comedy"));
        Movie rocky = new Movie("Rocky", 8.1, 1976, List.of(stallone, young),
                List.of("Drama", "Sport"));
        Movie dune = new Movie("Dune", 8.0, 2021, List.of(chalamet, zendaya),
                List.of("Action", "Adventure", "Drama"));
        Movie spiderMan = new Movie("Spider-Man: Far from Home", 7.4, 2019, List.of(zendaya, holland),
                List.of("Action", "Adventure", "Sci-Fi"));
        Movie escapePlan = new Movie("Escape Plan", 6.7, 2013, List.of(stallone, schwarzenegger),
                List.of("Action", "Thriller"));
        Movie taxi = new Movie("Taxi", 7.0, 1998, List.of(naceri, diefenthal),
                List.of("Action", "Comedy", "Crime"));

        List<Movie> movies = List.of(rambo, terminatorTwo, peacemaker, rocky, dune, spiderMan, escapePlan, taxi);

        return movies;
    }

    private static void printExercise(Map<String, Map<Movie, List<Actor>>> moviesWithHighRatingAndItsActors) {
        moviesWithHighRatingAndItsActors.entrySet().forEach(entry -> {
            System.out.println(entry.getKey());
            if (entry.getValue() == null || entry.getValue().isEmpty())
                System.out.println("\tNo movies with rating higher than 7.5 were found for this category");
            else {
                entry.getValue().entrySet().forEach(movie -> {
                    System.out.println("\t" + movie.getKey().name() + " - " + movie.getKey().rating() + "/10 | " + movie.getKey().year());
                    movie.getValue().forEach(actor -> System.out.println("\t\t" + actor.name() + " (" + actor.age() + ")"));
                });
            }
        });
    }

    private static void rightAnswer(List<Movie> movies) {
        Map<String, Map<Movie, List<Actor>>> moviesWithHighRatingAndItsActors = movies.stream()
                .flatMap(movie -> movie.genres().stream()
                        .map(genre -> new AbstractMap.SimpleEntry<>(genre, movie)))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.filtering(
                                entry -> entry.getValue().rating() > 7.5,
                                Collectors.groupingBy(
                                        AbstractMap.SimpleEntry::getValue,
                                        Collectors.flatMapping(entry -> entry.getValue().actors().stream(), Collectors.toList()
                                        )))));

        printExercise(moviesWithHighRatingAndItsActors);
    }

}

record Actor(String name, int age) {
}

record Movie(String name, double rating, int year, List<Actor> actors, List<String> genres) {
}
