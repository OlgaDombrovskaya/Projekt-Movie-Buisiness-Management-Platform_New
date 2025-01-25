package movie_platform;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Premiere {

    private static final Logger logger = Logger.getLogger(Premiere.class.getName());

    private int id;
    private String movieTitle;
    private String date;
    private String location;
    private int ticketCount;
    private int ticketSold;
    private double budget;
    private List<String> guestList;
    private List<String> reviews;

    public Premiere(int id, String movieTitle, String date, String location, int ticketCount,
                    double budget) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.date = date;
        this.location = location;
        this.ticketCount = ticketCount;
        this.ticketSold = 0;
        this.budget = budget;
        this.guestList = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public int getTicketSold() {
        return ticketSold;
    }

    public double getBudget() {
        return budget;
    }

    public List<String> getGuestList() {
        return guestList;
    }

    public List<String> getReviews() {
        return reviews;
    }

    // Метод для проверки доступности бюджета
    public boolean isBudgetAvailable(double cost) {
        // Сравниваем, если бюджет больше или равен нужной сумме
        if (budget >= cost) {
            return true; // Если достаточно, возвращаем true
        } else {
            logger.warning("Ошибка: Недостаточно бюджета для суммы: " + cost);
            return false; // Если нет, возвращаем false
        }
    }

    // Метод для проверки, можем ли мы продать указанное количество билетов
    public boolean canSellTickets(int count) {
        // Проверяем, не продано ли больше билетов, чем есть в наличии
        if (ticketSold + count <= ticketCount) {
            return true; // Если количество проданных билетов и новые билеты не превышают доступных, возвращаем true
        } else {
            logger.warning("Ошибка: Недостаточно билетов для продажи " + count + " билетов.");
            return false; // Если билетов недостаточно, возвращаем false
        }
    }

    // Метод для продажи билетов
    public void sellTickets(int count) {
        // Проверяем, можем ли продать указанное количество билетов
        if (canSellTickets(count)) {
            // Если можем продать, то увеличиваем количество проданных билетов
            ticketSold = ticketSold + count;
        } else {
            // Если нет, выводим сообщение об ошибке
            logger.warning("Ошибка при продаже билетов: Недостаточно билетов.");
            System.out.println("Ошибка: Недостаточно билетов для продажи.");
        }
    }

    // Метод для возврата билетов
    public void returnTickets(int count) {
        // Проверяем, можем ли вернуть указанное количество билетов
        if (ticketSold - count >= 0) {
            // Если можем вернуть, уменьшаем количество проданных билетов
            ticketSold = ticketSold - count;
            logger.info("Возвращено " + count + " билетов. Общее количество проданных билетов: " + ticketSold);
        } else {
            // Если пытаемся вернуть больше, чем было продано, выводим ошибку
            System.out.println("Ошибка: Невозможно вернуть больше билетов, чем было продано.");
            logger.warning("Ошибка при возврате билетов: Невозможно вернуть больше билетов, чем было продано.");
        }
    }

    // Метод для добавления гостей
    public void addGuest(String guest) {
        if (guest == null || guest.trim().isEmpty()) {
            logger.warning("Ошибка при добавлении гостя: имя гостя не может быть пустым.");
        } else {
            guestList.add(guest); // Добавляем имя гостя в список приглашенных
            logger.info("Гость " + guest + " добавлен в список.");
        }
    }

    // Метод для добавления отзыва
    public void addReview(String review) {
        if (review == null || review.trim().isEmpty()) {
            logger.warning("Ошибка при добавлении отзыва: отзыв не может быть пустым.");
        } else {
            reviews.add(review); // Добавляем отзыв в список
            logger.info("Отзыв добавлен: " + review);
        }
    }

    // Генерация отчета о премьере
    public String generateReport() {
        double totalRevenue = ticketSold * 10.0; // Примерная стоимость билета $10
        String report = "Отчет о премьере: " + movieTitle + "\n" +
                "Дата: " + date + "\n" +
                "Место проведения: " + location + "\n" +
                "Продано билетов: " + ticketSold + "\n" +
                "Общая прибыль: $" + totalRevenue + "\n" +
                "Список гостей: " + String.join(", ", guestList) + "\n" +
                "Отзывы: " + String.join("; ", reviews) + "\n";
        return report; // Возвращаем строку отчета
    }
}