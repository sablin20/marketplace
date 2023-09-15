package marketplace.product;

public enum Category {
    PC("компьютер"),
    LAPTOP("ноутбук"),
    VACUUM_CLEANER("пылесос"),
    TV("тв"),
    MICROWAVE("микроволновая печь"),
    SMARTPHONE("смартфон"),
    FRIDGE("холодильник"),
    WASHING_MACHINE("стиральная машина"),
    ELECTRIC_KETTLE("электрический чайник"),
    COFFEE_MACHINE("кофе машина");

    private String category;
    Category(String category) {
        this.category = category;
    }
}