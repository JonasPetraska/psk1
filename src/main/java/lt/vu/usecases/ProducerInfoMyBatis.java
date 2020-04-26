package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.model.Actor;
import lt.vu.mybatis.model.Movie;
import lt.vu.mybatis.model.Producer;
import lt.vu.helpers.ActorHelpers;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.mybatis.dao.ActorMapper;
import lt.vu.mybatis.dao.MovieMapper;
import lt.vu.mybatis.dao.ProducerMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class ProducerInfoMyBatis {

    @Inject
    private ProducerMapper producerMapper;

    @Inject
    private MovieMapper movieMapper;

    @Inject
    private ActorMapper actorMapper;

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
        producerMapper.updateByPrimaryKey(producer);
        return "producerInfo?producerId=" + producer.getId() + "&faces-redirect=true";
    }

    @Transactional
    @LoggedInvocation
    public String createMovie(){
        movieToCreate.setProducerId(producer.getId());

        List<Actor> actors = actorHelpers.getActorsFromStringMyBatis(movieToCreateActors, movieToCreate);
        for(Actor actor : actors)
            actorMapper.insert(actor);

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

        movieToCreate.setActorList(actorsToSet);

        movieMapper.insert(movieToCreate);
        return "producerInfo?producerId=" + producer.getId() + "&faces-redirect=true";
    }

    private void loadProducer(Integer id){
        producer = producerMapper.selectByPrimaryKey(id);
    }
}
