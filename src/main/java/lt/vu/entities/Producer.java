package lt.vu.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Producer.findAll", query = "select p from Producer as p"),
        @NamedQuery(name = "Producer.findById", query = "select p from Producer as p where p.id = :id"),
        @NamedQuery(name = "Producer.findByFirstNameAndLastName", query = "select p from Producer as p where p.firstName = :firstName and p.lastName = :lastName")
})
@Table(name = "PRODUCER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName"})
public class Producer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Size(max = 20)
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Size(max = 20)
    @Column(name = "LASTNAME")
    private String lastName;

    @OneToMany(mappedBy = "producer")
    private List<Movie> movieList = new ArrayList<Movie>();
}
