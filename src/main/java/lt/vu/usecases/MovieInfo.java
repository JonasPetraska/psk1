package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Actor;
import lt.vu.entities.Movie;
import lt.vu.helpers.ActorHelpers;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.ActorsDAO;
import lt.vu.persistence.MoviesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class MovieInfo {

    @Inject
    private MoviesDAO moviesDAO;

    @Inject
    private ActorsDAO actorsDAO;

    @Inject
    private ActorHelpers actorHelpers;

    @Getter
    @Setter
    private Movie movie;

    @Getter
    @Setter
    private String actorsToCreate;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer movieId = Integer.parseInt(requestParameters.get("movieId"));
        loadMovie(movieId);
    }

    @Transactional
    @LoggedInvocation
    public String updateMovie(){
        if(!getActorsString(movie.getActorList()).equals(actorsToCreate))
        {
            List<Actor> actors = actorHelpers.getActorsFromString(actorsToCreate, movie);
            List<Actor> actorsToSet = new ArrayList<Actor>();
            for(Actor actor : actors) {
                List<Actor> found = actorsDAO.findByFirstNameAndLastName(actor.getFirstName(), actor.getLastName());
                if(found.size() == 0) {
                    actorsDAO.persist(actor);
                    actorsToSet.add(actor);
                }else {
                    Actor act = found.get(0);
                    actorsToSet.add(act);
                }
            }

            movie.setActorList(actorsToSet);
        }

        moviesDAO.update(movie);
        return "movieInfo?movieId=" + movie.getId() + "&faces-redirect=true";
    }

    private void loadMovie(Integer id){
        movie = moviesDAO.findOne(id);
        List<Actor> actors = movie.getActorList();
        actorsToCreate = getActorsString(actors);
    }

    private String getActorsString(List<Actor> actors){
        List<String> actorsStrList = new ArrayList<String>();
        for(Actor actor : actors)
            actorsStrList.add(actor.getFirstName() + " " + actor.getLastName());
        return String.join(",", actorsStrList);
    }
}
