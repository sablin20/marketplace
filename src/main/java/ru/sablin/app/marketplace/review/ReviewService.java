package ru.sablin.app.marketplace.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sablin.app.marketplace.exception.ReviewRatingNotValidException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepositoryImpl repository;

    public Review leaveFeedback(Review review) {
        var str1 = "плохой";
        var str1v2 = "некачественный";

        var str2 = "крутой";
        var str2v2 = "качественный";

        var resultMessage = review.getMessage().toLowerCase()
                .replaceAll(str1, str1v2)
                .replaceAll(str2, str2v2);

        if (review.getRating() < 0 || review.getRating() > 5) {
            throw new ReviewRatingNotValidException(String.format("Rating = %d is out of range, rate from 0 to 5 please", review.getRating()));
        }

        var validReview = new Review();
        validReview.setId(review.getId());
        validReview.setClientName(review.getClientName());
        validReview.setStoreId(review.getStoreId());
        validReview.setMessage(resultMessage);
        validReview.setRating(review.getRating());

        return repository.leaveFeedback(validReview);
    }

    public List<String> getAvgRatingLast10ReviewAndTop3WordBeforeProduct() {
        var list10review = repository.find10LastReview();

        var sumRating = 0;

        var listRating = list10review.stream().map(Review::getRating).toList();
        for (Integer i : listRating) {
                sumRating += i;
        }

        var avgRating10Review = sumRating / list10review.size();
        var allMessage = list10review.stream().map(Review::getMessage).toList();

        var map = new HashMap<String, Integer>();

        for (String s : allMessage) {
            var arrayStr = s.split(" ");
            for (int i = 0; i < arrayStr.length; i++) {
                if (arrayStr[i].equals("товар")) {
                    if (i != 0 && map.containsKey(arrayStr[i - 1])) {
                        map.put(arrayStr[i - 1], map.get(arrayStr[i-1]) + 1);
                    } else {
                        if (i != 0) {
                            map.put(arrayStr[i - 1], 1);
                        }
                    }
                }
            }
            arrayStr = null;
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        var resultMethod = new ArrayList<String>();
        resultMethod.add(String.format("Average rating last 10 review = %d", avgRating10Review));
        resultMethod.add("Top_1 word before {товар}: " + list.get(list.size() - 1).toString());
        resultMethod.add("Top_2 word before {товар}: " + list.get(list.size() - 2).toString());
        resultMethod.add("Top_3 word before {товар}: " + list.get(list.size() - 3).toString());

        return resultMethod;
    }
}