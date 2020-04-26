package lt.vu.mybatis.dao;

import lt.vu.mybatis.model.Actor;
import lt.vu.mybatis.model.Movie;
import org.apache.ibatis.annotations.Param;
import org.mybatis.cdi.Mapper;

import java.util.List;

@Mapper
public interface MovieActorMapper {
    int insert(@Param("actorId") Integer actorId, @Param("movieId") Integer movieId);
    List<Movie> selectActorMovies(@Param("actorId") Integer actorId);
    List<Actor> selectMovieActors(@Param("movieId") Integer movieId);
}
