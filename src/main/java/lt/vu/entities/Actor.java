package lt.vu.entities;

import lombok.*;
import org.apache.ibatis.annotations.Many;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Actor.findAll", query = "select a from Actor as a"),
        @NamedQuery(name = "Actor.findById", query = "select a from Actor as a where a.id = :id"),
        @NamedQuery(name = "Actor.findByFirstNameAndLastName", query = "select a from Actor as a where a.firstName = :firstName and a.lastName = :lastName")
})
@Table(name = "ACTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(max = 20)
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Size(max = 20)
    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Transient
    private transient String name;

    @ManyToMany(mappedBy = "actorList")
    private List<Movie> movieList = new ArrayList<Movie>();

    public String getName(){
        return firstName + " " + lastName;
    }
}
