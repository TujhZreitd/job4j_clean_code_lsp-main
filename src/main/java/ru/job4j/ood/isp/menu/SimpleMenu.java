package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        boolean result = false;
        MenuItem menuItem = new SimpleMenuItem(childName, actionDelegate);
        if (parentName == (ROOT)) {
            rootElements.add(menuItem);
            result = true;
        } else {
            Optional<ItemInfo> item = findItem(parentName);
            if (item.isPresent()) {
                MenuItem menuItem1 = item.get().menuItem;
                menuItem1.getChildren().add(menuItem);
                result = true;
            }
        }
        return result;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        MenuItemInfo result = null;
        Optional<ItemInfo> item = findItem(itemName);
        if (item.isPresent()) {
            result = new MenuItemInfo(item.get().menuItem, item.get().number);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        List<MenuItemInfo> result = new ArrayList<>();
        DFSIterator iterator = new DFSIterator();
        while(iterator.hasNext()) {
            ItemInfo itemInfo = iterator.next();
            result.add(new MenuItemInfo(itemInfo.menuItem, itemInfo.number));
        }
        return result.iterator();
    }

    private Optional<ItemInfo> findItem(String name) {
        ItemInfo result = null;
        DFSIterator iterator = new DFSIterator();
        while (iterator.hasNext()) {
            ItemInfo itemInfo = iterator.next();
            if (itemInfo.menuItem.getName().equals(name)) {
                result = itemInfo;
            }
        }
        return Optional.ofNullable(result);
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

        Deque<MenuItem> stack = new LinkedList<>();

        Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        MenuItem menuItem;
        String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}