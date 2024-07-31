package ru.job4j.ood.isp.menu;

import java.util.List;
import java.util.Scanner;

public class TodoApp {
    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");
    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu;

    public TodoApp(Menu menu) {
        this.menu = menu;
    }

    public void init(List<UserAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            System.out.println("Выберете пункт (введите его номер)");
            int select = scanner.nextInt();
            if (select < 0 || select >= actions.size()) {
                System.out.println("Введено некорректное значение, повторите ввод");
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(menu, DEFAULT_ACTION);
        }
    }

    private void showMenu(List<UserAction> action) {
        System.out.println("Меню действий:");
        for (int i = 0; i < action.size(); i++) {
            System.out.println(i + "." + action.get(i).name());
        }
    }

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        List<UserAction> actions = List.of(
                new AddElementForRoot(),
                new AddElementForParent(),
                new CallActionMenuItem(),
                new OutputMenu(),
                new ExitTodaApp()
        );
        new TodoApp(menu).init(actions);
    }
}
