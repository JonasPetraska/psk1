package lt.vu.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Movie.findAll", query = "select m from Movie as m"),
        @NamedQuery(name = "Movie.findById", query = "select m from Movie as m where m.id = :id"),
        @NamedQuery(name = "Movie.findByName", query = "select m from Movie as m where m.name = :name")
})
@Table(name = "MOVIE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "year"})
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(max = 30)
    @Column(name = "NAME")
    private String name;

    @Size(max = 4)
    @Column(name = "YEAR")
    private String year;

    @JoinColumn(name = "PRODUCER_ID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producer producer;

    @JoinTable(name = "MOVIE_ACTOR", joinColumns = {
                @JoinColumn(name = "MOVIE_ID", referencedColumnName = "ID")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "ACTOR_ID", referencedColumnName = "ID")
            })
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Actor> actorList = new ArrayList<Actor>();

}
