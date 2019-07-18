package yss.clientservice.entity.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class Weather {
    private ConsolidatedWeather[] consolidated_weather;
}
