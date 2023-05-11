package org.example.Task8;

public class Main {
    // Смотреть какие процессы
    // Смотреть какие объекты инстанциируются

    // потоки в синусоиде
    // по файлу - можно вычитать по очереди время метода. (ПЕРВАЯ ЧАСТЬ ЗАДАНИЯ НЕ СВЯЗАНА СО ВТОРЫМ)
    // CPU Views - и справа сверху последняя кнопка с графом (Analyze)
    public static void main(String[] args) throws InterruptedException {
        ThreadController threadController = new ThreadController();
        threadController.start(10);
    }
}
