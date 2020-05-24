package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
//enable if xml response is needed
@XmlRootElement
public class MovieDTO {

    /*@Getter
    @Setter*/
    private String Name;

    //@XmlTransient
    private String Year;

    /*@XmlTransient
    public String getYear(){
        return Year;
    }

    public void setYear(String year){
        Year = year;
    }*/

    /*@Getter
    @Setter*/
    private Integer Rating;

    /*@Getter
    @Setter*/
    private Integer ProducerId;
}
