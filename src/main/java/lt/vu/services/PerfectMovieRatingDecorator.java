package lt.vu.services;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.Date;
import java.util.Random;

//Logs any perfect (100) movie scores that have been generated
@Decorator
public abstract class PerfectMovieRatingDecorator implements MovieRatingGenerator{

    @Inject
    @Delegate @Any
    MovieRatingGenerator generator;

    public Integer generateMovieRating() {
        Integer result = generator.generateMovieRating();
        if(result == 100){
            System.out.println("100 points rating has been scored on: " + new Date().toString() + " !!!!!!!!!!");
        }
        return result;
    }

}
