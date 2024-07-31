package ru.job4j.ood.isp.menu;

import java.util.Scanner;

public interface UserAction {
    Scanner scanner = new Scanner(System.in);

    String name();

    boolean execute(Menu menu, ActionDelegate action);
}
