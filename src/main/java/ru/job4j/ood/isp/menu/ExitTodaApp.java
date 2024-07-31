package ru.job4j.ood.isp.menu;

public class ExitTodaApp implements UserAction {
    @Override
    public String name() {
        return "Выход из программы";
    }

    @Override
    public boolean execute(Menu menu, ActionDelegate action) {
        return false;
    }
}
