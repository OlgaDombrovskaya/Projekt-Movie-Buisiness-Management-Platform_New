package movie_platform.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import movie_platform.enums.MovieGenre;
import movie_platform.enums.MovieStatus;
import movie_platform.model.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Slf4j
public class MovieManager {
    private List<Movie> movies;
    private static final String FILE_NAME = "movie.txt";

    // Конструктор инициализирует список фильмов
    public MovieManager() {
        this.movies = new ArrayList<>();
    }

    // Метод для добавления фильма в список
    public void addMovie(Movie movie) {
        if (movie == null) {
            System.out.println("Попытка добавить null-фильм.");
            log.warn("Попытка добавить null-фильм.");
            return;
        }
        movies.add(movie);
    }

    // Метод для удаления фильма по ID
    public void removeMovie(String movieId) {
        if (movieId == null || movieId.trim().isEmpty()) {
            System.out.println("Попытка удалить фильм с пустым ID.");
            log.warn("Попытка удалить фильм с пустым ID.");
            return;
        }
        Movie movieToRemove = null;
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId)) {
                movieToRemove = movie;
                break;
            }
        }
        if (movieToRemove != null) {
            movies.remove(movieToRemove);
            System.out.println("Фильм удалён: " + movieToRemove.getTitle());
            log.info("Фильм удалён: {}", movieToRemove.getTitle());
        } else {
            System.out.println("Фильм с ID " + movieId + " не найден.");
            log.warn("Фильм с ID {} для удаления не найден.", movieId);

        }
    }

    // Метод для обновления данных фильма
    public void updateMovie(Movie updatedMovie) {
        if (updatedMovie == null) {
            System.out.println("Попытка обновить null-фильм.");
            log.warn("Попытка обновить null-фильм.");
            return;
        }
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(updatedMovie.getId())) {
                movies.set(i, updatedMovie);
                System.out.println("Фильм обновлён: " + updatedMovie.getTitle());
                log.info("Фильм обновлён: {}", updatedMovie.getTitle());
                return;
            }
        }
        System.out.println("Фильм с ID " + updatedMovie.getId() + " не найден.");
        log.warn("Фильм с ID {} для обновления не найден.", updatedMovie.getId());
    }

    // Метод выводит в консоль все фильмы из списка
    public void printAllMovies() {
        if (movies.isEmpty()) {
            System.out.println("Список фильмов пуст.");
        } else {
            for (Movie movie : movies) {
                System.out.println("ID фильма: " + movie.getId());
                System.out.println("Название: " + movie.getTitle());
                System.out.println("Статус: " + movie.getStatus());
                System.out.println("Жанр: " + movie.getGenre());

                System.out.println("---------------------------------------");
            }
        }
    }

    public void saveMovies() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, false))) {// false - перезапись
            for (Movie movie : movies) {
                bufferedWriter.write(movie.getId() + ", " + movie.getTitle() + ", " + movie.getStatus() +", " + movie.getGenre());
                bufferedWriter.newLine();
            }
            System.out.println("Фильмы сохранены в файл.");
        } catch (IOException exception) {
            System.out.println("Ошибка при загрузке списка фильмов: " + exception.getMessage());
            log.error("Ошибка при загрузке списка фильмов: {}", exception.getMessage());
        }
    }

    public void loadMovie() {
        movies.clear(); // Очищаем список перед загрузкой
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String movieLine;
            int lineNumber = 0;
            while ((movieLine = bufferedReader.readLine()) != null) {
                lineNumber++;
                String[] parts = movieLine.split(", ");
                if (parts.length >= 4){ // Если в строке минимум 4 элемента
                    try {
                        String id = parts[0].trim();
                        String title = parts[1].trim();
                        MovieStatus status = MovieStatus.valueOf(parts[2].trim());
                        MovieGenre genre = MovieGenre.valueOf(parts[3].trim()); // Разбираем жанр

                        Movie movie = new Movie(id, title, status, genre); // Вызываем правильный конструктор
                        movies.add(movie);
                    }catch (IllegalArgumentException exception){
                        log.warn("Некорректный статус фильма: {} - {}", movieLine, lineNumber);
                    }
                }else {
                    log.warn("Некорректная строка в файле: {} - {}", movieLine, lineNumber);
                }
            }
            System.out.println("Фильмы успешно загружены.");
        } catch (FileNotFoundException exception) {
            log.error("Файл не найден: {}", exception.getMessage());
        } catch (IOException exception) {
            log.error("Ошибка чтения файла: {}", exception.getMessage());
        }
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }
}