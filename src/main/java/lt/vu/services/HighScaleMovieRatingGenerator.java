package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import java.io.Serializable;
import java.util.Random;

@Alternative
@ApplicationScoped
public class HighScaleMovieRatingGenerator implements MovieRatingGenerator {
    public Integer generateMovieRating() {
        try {
            Thread.sleep(5000); // Simulate intensive work
        } catch (InterruptedException e) {
        }
        Integer generatedMovieRating = new Random().nextInt(100-1) + 1;
        return generatedMovieRating;
        //Used to show decorator working
        //return 100;
    }
}
