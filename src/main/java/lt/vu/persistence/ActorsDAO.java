package lt.vu.persistence;

import lt.vu.entities.Actor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import java.util.List;

@ApplicationScoped
public class ActorsDAO {
    @PersistenceContext(synchronization = SynchronizationType.SYNCHRONIZED)
    @Inject
    private EntityManager em;

    public List<Actor> getAllActors(){
        return em.createNamedQuery("Actor.findAll", Actor.class).getResultList();
    }

    public Actor findById(Integer id){
        return em.find(Actor.class, id);
    }

    public void persist(Actor actor){
        em.persist(actor);
    }

}
