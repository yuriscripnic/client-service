package yss.clientservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import yss.clientservice.entity.Client;
import yss.clientservice.entity.GeoData;
import yss.clientservice.entity.rest.*;
import yss.clientservice.repository.GeoDataRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GeoDataService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String IP_INFORMATION_URL = "https://ipvigilante.com/json/";
    private static final String LOCATION_FROM_LAT_LONG_URL = "https://www.metaweather.com/api/location/search/?lattlong=";
    private static final String WEATHER_FROM_LOCATION_URL = "https://www.metaweather.com/api/location/";
    private final RestTemplate restTemplate = new RestTemplate();

    private final GeoDataRepository geoDataRepository;

    public GeoDataService(GeoDataRepository geoDataRepository) {
        this.geoDataRepository = geoDataRepository;
    }

    public LatLongData getLatLongFromIP(String ip) {
        IPData ipdata = restTemplate.getForObject(IP_INFORMATION_URL + ip, IPData.class);
        logger.info("IPData:{}", ipdata.toString());
        return ipdata.getData();
    }

    public Optional<Location> getLocationFromLatLong(LatLongData latlong) {
        String pos = String.format("%s,%s", latlong.getLatitude(), latlong.getLongitude());
        Location[] locations = restTemplate.getForObject(
                LOCATION_FROM_LAT_LONG_URL + pos, Location[].class);
        logger.info("Locations for LatLong:{} :{}", latlong.toString(), locations);
        return nearestLocation(locations);
    }

    public Optional<ConsolidatedWeather> getWeatherFromLocation(Location location) {
        Weather weather = restTemplate.getForObject(
                WEATHER_FROM_LOCATION_URL + location.getWoeid(), Weather.class);
        logger.info("Weather for Location:{} :{}", location.toString(), weather.toString());
        return closestConsolidatedWeatherData(weather);
    }

    public void saveClientGeoData(Client client, String ip) {
        LatLongData latlong = getLatLongFromIP(ip);
        Location location = getLocationFromLatLong(latlong).get();
        ConsolidatedWeather weather = getWeatherFromLocation(location).get();
        logger.info("From ip:{} latlong:{} location:{} weather:{}", ip, latlong, location, weather);
        GeoData data = new GeoData();
        data.setClient_id(client.getId());
        data.setIp(ip);
        data.setLocation(location.getWoeid().toString());
        data.setMaxTemp(weather.getMax_temp());
        data.setMinTemp(weather.getMin_temp());
        geoDataRepository.save(data);
    }

    public List<GeoData> findAll() {
        return geoDataRepository.findAll();
    }

    private Optional<Location> nearestLocation(Location[] locations) {
        Arrays.sort(locations, Comparator.comparing(Location::getDistance));
        return Optional.ofNullable(locations[0]);
    }

    private Optional<ConsolidatedWeather> closestConsolidatedWeatherData(Weather weathers) {
        return Optional.ofNullable(weathers.getConsolidated_weather()[0]);
    }


}
