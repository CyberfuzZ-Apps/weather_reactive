package ru.job4j.weather;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс WeatherService
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 25));
        weathers.put(2, new Weather(2, "SPb", 20));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 10));
        weathers.put(5, new Weather(5, "Kiev", 7));
        weathers.put(6, new Weather(6, "Minsk", 5));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> findHottest() {
        return Mono.justOrEmpty(Collections.max(
                weathers.values(),
                Comparator.comparingInt(Weather::getTemperature)));
    }

    public Flux<Weather> greatThen(Integer temp) {
        return Flux.fromIterable(weathers.values())
                .filter(w -> w.getTemperature() > temp);
    }
}
