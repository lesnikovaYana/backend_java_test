package ru.lesnikovaYana.enam;

import lombok.Getter;

public enum CategoryType {
    FOOD("Food", 1),
    ELECTRONICS("Electronic", 2),
    FURNITURE("Furniture", 3);

    @Getter
    private final String title;
    @Getter
    private final int id;

    CategoryType(String title, int id) {
        this.title = title;
        this.id = id;
    }
}
