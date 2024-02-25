package ru.sablin.app.marketplace.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/")
    public Review leaveFeedback(@RequestBody Review review) {
        return reviewService.leaveFeedback(review);
    }

    @GetMapping("/")
    public List<String> getAvgRatingLast10ReviewAndTop3WordBeforeProduct(@RequestParam("storeName") String storeName) {
        return reviewService.getAvgRatingLast10ReviewAndTop3WordBeforeProduct(storeName);
    }
}
