package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {
    private static final String INDENT = "----";

    private int countIndent(String number) {
        char ch = '.';
        int result = 0;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == ch) {
                result++;
            }
        }
        return result;
    }

    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo menuItemInfo : menu) {
            int count = countIndent(menuItemInfo.getNumber());
            if (count > 1) {
                while (count > 1) {
                    System.out.print(INDENT);
                    count--;
                }
            }
            System.out.println(menuItemInfo.getNumber() + menuItemInfo.getName());
        }

    }
}
