package yss.clientservice.entity.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class Location {
    private String title;	 // Name of the location
    private Integer woeid; //Where On Earth ID
    private Integer distance;	  //Metres	Only returned on a lattlong search
}
