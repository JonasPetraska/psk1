package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Actor;
import lt.vu.entities.Movie;
import lt.vu.entities.Producer;
import lt.vu.helpers.ActorHelpers;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.ActorsDAO;
import lt.vu.persistence.MoviesDAO;
import lt.vu.persistence.ProducersDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class ProducerInfo {

    @Inject
    private ProducersDAO producersDAO;

    @Inject
    private MoviesDAO moviesDAO;

    @Inject
    private ActorsDAO actorsDAO;

    @Inject
    private ActorHelpers actorHelpers;

    @Getter
    @Setter
    private Producer producer;

    @Getter
    @Setter
    private Movie movieToCreate = new Movie();

    @Getter
    @Setter
    private String movieToCreateActors;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer producerId = Integer.parseInt(requestParameters.get("producerId"));
        loadProducer(producerId);
    }

    @Transactional
    @LoggedInvocation
    public String updateProducer(){
        producersDAO.update(producer);
        return "producerInfo?producerId=" + producer.getId() + "&faces-redirect=true";
    }

    @Transactional
    @LoggedInvocation
    public String createMovie(){
        movieToCreate.setProducer(producer);

        List<Actor> actors = actorHelpers.getActorsFromString(movieToCreateActors, movieToCreate);
        for(Actor actor : actors)
            actorsDAO.persist(actor);

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

        movieToCreate.setActorList(actorsToSet);

        moviesDAO.persist(movieToCreate);
        return "producerInfo?producerId=" + producer.getId() + "&faces-redirect=true";
    }

    private void loadProducer(Integer id){
        producer = producersDAO.findOne(id);
    }
}
