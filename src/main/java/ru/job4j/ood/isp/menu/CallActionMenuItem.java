package ru.job4j.ood.isp.menu;

public class CallActionMenuItem implements UserAction {
    @Override
    public String name() {
        return "Вызвать действие, привязанное к пункту меню";
    }

    @Override
    public boolean execute(Menu menu, ActionDelegate action) {
        action.delegate();
        return true;
    }
}
