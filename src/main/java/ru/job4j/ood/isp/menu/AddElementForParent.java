package ru.job4j.ood.isp.menu;

public class AddElementForParent implements UserAction {
    @Override
    public String name() {
        return "Добавление элемента к родительскому элементу";
    }

    @Override
    public boolean execute(Menu menu, ActionDelegate action) {
        System.out.println("Введите имя родителя");
        String nameParent = SCANNER.nextLine();
        System.out.println("Введите имя нового элемента");
        String nameChild = SCANNER.nextLine();
        return menu.add(nameParent, nameChild, action);
    }
}
/*для коммита*/
