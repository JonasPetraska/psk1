package lt.vu.persistence;

import lt.vu.entities.Movie;
import lt.vu.entities.Producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class MoviesDAO {
    @PersistenceContext
    @Inject
    private EntityManager em;

    public List<Movie> getAllMovies() {
        return em.createNamedQuery("Movie.findAll", Movie.class).getResultList();
    }

    public void persist(Movie movie){
        em.persist(movie);
    }
    public Movie update(Movie movie){
        return em.merge(movie);
    }
    public Movie findOne(Integer id){
        return em.find(Movie.class, id);
    }

}
