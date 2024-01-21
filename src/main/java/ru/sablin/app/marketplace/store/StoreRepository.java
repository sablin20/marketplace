package ru.sablin.app.marketplace.store;

import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository {
    Store create(Store store);
    Store findById(Integer id);
}