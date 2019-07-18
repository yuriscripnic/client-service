package yss.clientservice.entity.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class ConsolidatedWeather{
    private String applicable_date;
    private Integer min_temp;
    private Integer max_temp;
}
