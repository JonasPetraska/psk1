package lt.vu.mybatis.dao;

import java.util.List;
import lt.vu.mybatis.model.Movie;
import org.mybatis.cdi.Mapper;

@Mapper
public interface MovieMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.MOVIE
     *
     * @mbg.generated Sun Apr 26 01:46:49 EEST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.MOVIE
     *
     * @mbg.generated Sun Apr 26 01:46:49 EEST 2020
     */
    int insert(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.MOVIE
     *
     * @mbg.generated Sun Apr 26 01:46:49 EEST 2020
     */
    Movie selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.MOVIE
     *
     * @mbg.generated Sun Apr 26 01:46:49 EEST 2020
     */
    List<Movie> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.MOVIE
     *
     * @mbg.generated Sun Apr 26 01:46:49 EEST 2020
     */
    int updateByPrimaryKey(Movie record);
}