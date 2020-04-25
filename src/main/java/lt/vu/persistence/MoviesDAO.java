package lt.vu.persistence;

import lt.vu.entities.Movie;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
