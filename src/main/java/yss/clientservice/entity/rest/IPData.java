package yss.clientservice.entity.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter
public class IPData {
   private String status;
   private LatLongData data;
}


