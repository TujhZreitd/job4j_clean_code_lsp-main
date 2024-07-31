package ru.job4j.ood.isp.menu;

public class OutputMenu implements UserAction {
    @Override
    public String name() {
        return "Вывод меню";
    }

    @Override
    public boolean execute(Menu menu, ActionDelegate action) {
        MenuPrinter printer = new Printer();
        printer.print(menu);
        return true;
    }
}
/*для коммита*/