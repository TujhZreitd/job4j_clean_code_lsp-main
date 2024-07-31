package ru.job4j.ood.isp.menu;

public class AddElementForRoot implements UserAction {
    @Override
    public String name() {
        return "Добавить элемент в корень меню";
    }

    @Override
    public boolean execute(Menu menu, ActionDelegate action) {
        System.out.println("Введите название элемента");
        String name = SCANNER.nextLine();
        return menu.add(Menu.ROOT, name, action);
    }
}
/*для коммита*/