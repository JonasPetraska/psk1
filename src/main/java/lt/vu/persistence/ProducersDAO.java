package lt.vu.persistence;

import lt.vu.entities.Producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ProducersDAO {
    @PersistenceContext
    @Inject
    private EntityManager em;

    public List<Producer> getAllProducers(){
        return em.createNamedQuery("Producer.findAll", Producer.class).getResultList();
    }

    public void persist(Producer producer){
        em.persist(producer);
    }

    public Producer update(Producer producer){
        return em.merge(producer);
    }

    public Producer findOne(Integer id){
        return em.find(Producer.class, id);
    }
}
