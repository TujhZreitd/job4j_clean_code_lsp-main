package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;
    public static final ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    public static final PrintStream ORIGINAL_OUT = System.out;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    public void whenAddAndSelect() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Первая задача", STUB_ACTION);
        menu.add(Menu.ROOT, "Вторая задача", STUB_ACTION);
        menu.add("Первая задача", "Первая подзадача", STUB_ACTION);
        menu.add("Первая задача", "Вторая подзадача", STUB_ACTION);
        menu.add("Первая подзадача", "Первая подподзадача", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Первая подподзадача", List.of(), STUB_ACTION, "1.1.1."))
                .isEqualTo(menu.select("Первая подподзадача").get());
        assertThat(menu.select("Вторая подподзадача").isEmpty()).isTrue();
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(OUT_CONTENT));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(ORIGINAL_OUT);
    }

    @Test
    public void whenUsePrinter() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        MenuPrinter printer = new Printer();
        printer.print(menu);
        String expected = "1.Сходить в магазин" + System.lineSeparator()
                + "----1.1.Купить продукты" + System.lineSeparator()
                + "--------1.1.1.Купить хлеб" + System.lineSeparator()
                + "--------1.1.2.Купить молоко" + System.lineSeparator()
                + "2.Покормить собаку" + System.lineSeparator();
        assertEquals(expected, OUT_CONTENT.toString());
    }
}