package lt.vu.rest;

import lombok.Setter;
import lombok.Getter;
import lt.vu.entities.Movie;
import lt.vu.entities.Producer;
import lt.vu.persistence.MoviesDAO;
import lt.vu.persistence.ProducersDAO;
import lt.vu.rest.contracts.MovieDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/movies")
public class MoviesController {

    @Inject
    @Getter
    @Setter
    private MoviesDAO moviesDAO;

    @Inject
    @Getter
    @Setter
    private ProducersDAO producersDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Movie> movies = moviesDAO.getAllMovies();
        if (movies == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<MovieDTO> dtos = new ArrayList<MovieDTO>();

        for(Movie movie : movies){
            MovieDTO dto = new MovieDTO();
            dto.setName(movie.getName());
            dto.setYear(movie.getYear());
            dto.setRating(movie.getRating());
            dto.setProducerId(movie.getProducer().getId());
            dtos.add(dto);
        }

        return Response.ok(dtos).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Movie movie = moviesDAO.findOne(id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        MovieDTO dto = new MovieDTO();
        dto.setName(movie.getName());
        dto.setYear(movie.getYear());
        dto.setRating(movie.getRating());
        dto.setProducerId(movie.getProducer().getId());

        return Response.ok(dto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer movieId,
            MovieDTO dto) {
        try
        {
            Movie movieFromDB = moviesDAO.findOne(movieId);
            if (movieFromDB == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            movieFromDB.setName(dto.getName());
            movieFromDB.setRating(dto.getRating());
            movieFromDB.setYear(dto.getYear());

            //check if producer id is different
            Producer currentProducer = movieFromDB.getProducer();
            if(currentProducer.getId() != dto.getProducerId()){
                //get new producer
                Producer newProducer = producersDAO.findOne(dto.getProducerId());
                if(newProducer == null){
                    return Response.status(Response.Status.NOT_FOUND).build();
                }

                movieFromDB.setProducer(newProducer);
            }

            moviesDAO.update(movieFromDB);
            return Response.ok().build();

        } catch (OptimisticLockException ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(MovieDTO dto) {
        //validation example
        String validationMessage = "";
        if(dto.getName() == null || dto.getName().isEmpty())
            validationMessage += "Name cannot be empty.";

        if(dto.getYear() == null || dto.getYear().isEmpty())
            validationMessage += "Year cannot be empty.";

        if(dto.getRating() <= 0)
            validationMessage += "Rating cannot be 0 or negative.";

        if(dto.getProducerId() <= 0)
            validationMessage += "Producer id cannot be 0 or negative";

        //if validation not passed, thrown an error
        if(!validationMessage.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), validationMessage).build();

        Movie newMovie = new Movie();
        newMovie.setName(dto.getName());
        newMovie.setYear(dto.getYear());
        newMovie.setRating(dto.getRating());
        //check if producer id is different
        Producer producer = producersDAO.findOne(dto.getProducerId());
        if(producer == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        newMovie.setProducer(producer);

        moviesDAO.persist(newMovie);
        return Response.ok().build();
    }

}
