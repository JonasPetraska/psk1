package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class LowScaleMovieRatingGenerator implements MovieRatingGenerator {
    public Integer generateMovieRating() {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedMovieRating = new Random().nextInt(10-1) + 1;
        return generatedMovieRating;
    }
}
