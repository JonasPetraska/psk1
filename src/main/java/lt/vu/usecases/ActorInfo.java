package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Actor;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.ActorsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class ActorInfo {

    @Inject
    private ActorsDAO actorsDAO;

    @Getter
    @Setter
    private Actor actor;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer actorId = Integer.parseInt(requestParameters.get("actorId"));
        loadActor(actorId);
    }

    @Transactional
    @LoggedInvocation
    public String updateActor(){
        actorsDAO.update(actor);
        return "actorInfo?actorId=" + actor.getId() + "&faces-redirect=true";
    }

    private void loadActor(Integer id){
        actor = actorsDAO.findOne(id);
    }
}
