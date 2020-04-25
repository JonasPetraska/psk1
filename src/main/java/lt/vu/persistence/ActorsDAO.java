package lt.vu.persistence;

import lt.vu.entities.Actor;
import lt.vu.entities.Producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class ActorsDAO {
    @PersistenceContext(synchronization = SynchronizationType.SYNCHRONIZED)
    @Inject
    private EntityManager em;

    public List<Actor> getAllActors(){
        return em.createNamedQuery("Actor.findAll", Actor.class).getResultList();
    }

    public Actor findOne(Integer id){ return em.find(Actor.class, id);}

    public List<Actor> findByFirstNameAndLastName(String firstName, String lastName){
        TypedQuery<Actor> query = em.createNamedQuery("Actor.findByFirstNameAndLastName", Actor.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    public void persist(Actor actor){
        em.persist(actor);
    }

    public Actor update(Actor actor){
        return em.merge(actor);
    }

    public void remove(Actor actor){
        em.remove(actor);
    }

}
