package lt.vu.helpers;

import lt.vu.entities.Actor;
import lt.vu.entities.Movie;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ActorHelpers {

    public List<Actor> getActorsFromString(String string, Movie forMovie){
        List<Actor> actorList = new ArrayList<Actor>();
        String[] actors = string.trim().split(",");
        for(String actor : actors){
            String[] actorFNLN = actor.split(" ");
            String actorFN = actorFNLN[0];
            String actorLN = actorFNLN[1];

            Actor actorToCreate = new Actor();
            if(forMovie != null)
                actorToCreate.getMovieList().add(forMovie);

            actorToCreate.setFirstName(actorFN);
            actorToCreate.setLastName(actorLN);
            actorList.add(actorToCreate);
        }

        return actorList;
    }

    public List<lt.vu.mybatis.model.Actor> getActorsFromStringMyBatis(String string, lt.vu.mybatis.model.Movie forMovie){
        List<lt.vu.mybatis.model.Actor> actorList = new ArrayList<lt.vu.mybatis.model.Actor>();
        String[] actors = string.trim().split(",");
        for(String actor : actors){
            String[] actorFNLN = actor.split(" ");
            String actorFN = actorFNLN[0];
            String actorLN = actorFNLN[1];

            lt.vu.mybatis.model.Actor actorToCreate = new lt.vu.mybatis.model.Actor();
            if(forMovie != null)
                actorToCreate.getMovieList().add(forMovie);

            actorToCreate.setFirstname(actorFN);
            actorToCreate.setLastname(actorLN);
            actorList.add(actorToCreate);
        }

        return actorList;
    }
}
