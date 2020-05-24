package lt.vu.usecases;

import lt.vu.interceptors.LoggedInvocation;
import lt.vu.services.HighScaleMovieRating;
import lt.vu.services.LowScaleMovieRatingGenerator;
import lt.vu.services.MovieRatingGenerator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GenerateMovieRating implements Serializable {
    @Inject
    MovieRatingGenerator movieRatingGenerator;

    private CompletableFuture<Integer> movieRatingGenerationTask = null;

    @LoggedInvocation
    public String generateMovieRating() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        movieRatingGenerationTask = CompletableFuture.supplyAsync(() -> movieRatingGenerator.generateMovieRating());

        return "movieInfo?movieId=" + requestParameters.get("movieId") + "&faces-redirect=true";
    }

    public boolean isGenerationRunning() {
        return movieRatingGenerationTask != null && !movieRatingGenerationTask.isDone();
    }

    public String getGenerationStatus() throws ExecutionException, InterruptedException {
        if (movieRatingGenerationTask == null) {
            return null;
        } else if (isGenerationRunning()) {
            return "Rating generation in progress";
        }
        return "Suggested movie rating: " + movieRatingGenerationTask.get();
    }
}

