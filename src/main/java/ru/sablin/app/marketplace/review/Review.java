package ru.sablin.app.marketplace.review;

import lombok.Data;

@Data
public class Review {
    private Integer id;
    private String clientName;
    private String storeName;
    private String message;
    private Integer rating;
}
