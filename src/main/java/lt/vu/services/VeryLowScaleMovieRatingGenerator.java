package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import java.util.Random;

@Specializes
@ApplicationScoped
public class VeryLowScaleMovieRatingGenerator extends LowScaleMovieRatingGenerator {
    public Integer generateMovieRating() {
        try {
            Thread.sleep(1000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedMovieRating = new Random().nextInt(5-1) + 1;
        return generatedMovieRating;
    }
}
