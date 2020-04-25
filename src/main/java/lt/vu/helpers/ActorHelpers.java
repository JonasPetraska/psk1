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
}
