package ru.volod878.buying_auto_parts.util;

public enum Status {
    COMPLETED("Завершен"),
    EXECUTION("В пути");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
