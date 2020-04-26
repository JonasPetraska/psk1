package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.ActorMapper;
import lt.vu.mybatis.dao.MovieMapper;
import lt.vu.mybatis.model.Actor;
import lt.vu.mybatis.model.Movie;
import lt.vu.helpers.ActorHelpers;
import lt.vu.interceptors.LoggedInvocation;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class MovieInfoMyBatis {

    @Inject
    private MovieMapper movieMapper;

    @Inject
    private ActorMapper actorMapper;

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
            List<Actor> actors = actorHelpers.getActorsFromStringMyBatis(actorsToCreate, movie);
            List<Actor> actorsToSet = new ArrayList<Actor>();
            for(Actor actor : actors) {
                List<Actor> found = actorMapper.selectByFirstNameAndLastName(actor.getFirstname(), actor.getLastname());
                if(found.size() == 0) {
                    actorMapper.insert(actor);
                    actorsToSet.add(actor);
                }else {
                    Actor act = found.get(0);
                    actorsToSet.add(act);
                }
            }

            movie.setActorList(actorsToSet);
        }

        movieMapper.updateByPrimaryKey(movie);
        return "movieInfo?movieId=" + movie.getId() + "&faces-redirect=true";
    }

    private void loadMovie(Integer id){
        movie = movieMapper.selectByPrimaryKey(id);
        List<Actor> actors = movie.getActorList();
        actorsToCreate = getActorsString(actors);
    }

    private String getActorsString(List<Actor> actors){
        List<String> actorsStrList = new ArrayList<String>();
        for(Actor actor : actors)
            actorsStrList.add(actor.getFirstname() + " " + actor.getLastname());
        return String.join(",", actorsStrList);
    }
}
