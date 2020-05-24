package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {

    private String Name;
    private String Year;
    private Integer Rating;

    private Integer ProducerId;
}
