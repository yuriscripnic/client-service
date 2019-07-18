package yss.clientservice.entity.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class LatLongData {
    private String country_name;
    private String city_name;
    private String latitude;
    private String longitude;
}