package com.traflux.TrafluxTrafficLightService.services;

import com.traflux.TrafluxTrafficLightService.entities.LightModel;
import com.traflux.TrafluxTrafficLightService.enums.LightStatus;
import com.traflux.TrafluxTrafficLightService.helper.ErrorMessages;
import com.traflux.TrafluxTrafficLightService.repositories.LightRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.EnumSet;
import java.util.List;

@Service
public class LightService {

    @Value("${traflux.esp32.cloudflare.url}")
    String ESP32_URL;

    @Value("${traflux.esp32.header.app.secret}")
    String ESP32_APP_SECRET;

    private final LightRepository lightRepository;

    private WebClient webClient;

    public LightService(LightRepository lightRepository) {
        this.lightRepository = lightRepository;
    }

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .defaultHeader("X-API-KEY", ESP32_APP_SECRET)  // Header ekleme
                .build();
    }

    public List<LightModel> getAllTrafficLights() {
        List<LightModel> lights = lightRepository.findAll();
        return lights;
    }

    public LightModel getTrafficLight(Long lightId) {
        LightModel trafficLight = lightRepository.findById(lightId)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.getLightNotFound()));

        return trafficLight;
    }

    public LightModel saveTrafficLight(LightStatus lightStatus, Long duration) {
        if (lightStatus == null || duration == null) {
            throw new RuntimeException(ErrorMessages.getNullLightStatusOrDuration());
        }

        // Enum doğrulaması
        if (!EnumSet.allOf(LightStatus.class).contains(lightStatus)) {
            throw new RuntimeException(ErrorMessages.getInvalidLightStatus());
        }

        LightModel trafficLight = new LightModel();
        trafficLight.setLightStatus(lightStatus);
        trafficLight.setDuration(duration);
        return lightRepository.save(trafficLight);
    }

    public LightModel updateTrafficLightById(Long lightId, LightStatus lightStatus, Long duration) {
        LightModel trafficLight = lightRepository.findById(lightId)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.getLightNotFound()));

        if (lightStatus == null || duration == null) {
            throw new RuntimeException(ErrorMessages.getNullLightStatusOrDuration());
        }

        trafficLight.setLightStatus(lightStatus);
        trafficLight.setDuration(duration);

        return lightRepository.save(trafficLight);
    }

    public void deleteTrafficLightById(Long lightId) {
        LightModel trafficLight = lightRepository.findById(lightId)
                .orElseThrow(() -> new RuntimeException(ErrorMessages.getLightNotFound()));

        lightRepository.delete(trafficLight);
    }

    // Vehicle Green Işığı Aç (Opsiyonel duration)
    public String sendVehicleGreenRequest(Integer duration) {
        String url = ESP32_URL + "/vehicle-green" + (duration != null ? "?duration=" + duration : "");

        return webClient.post()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // HTML hata sayfası gelmişse özel hata mesajı dön
                                    if (errorBody.contains("<html") || errorBody.contains("<!DOCTYPE html")) {
                                        return Mono.error(new RuntimeException(ErrorMessages.getEsp32ConnectionFailed()));
                                    }
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }

    // Pedestrian Green Işığı Aç (Opsiyonel duration)
    public String sendPedestrianGreenRequest(Integer duration) {
        String url = ESP32_URL + "/pedestrian-green" + (duration != null ? "?duration=" + duration : "");

        return webClient.post()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // HTML hata sayfası gelmişse özel hata mesajı dön
                                    if (errorBody.contains("<html") || errorBody.contains("<!DOCTYPE html")) {
                                        return Mono.error(new RuntimeException(ErrorMessages.getEsp32ConnectionFailed()));
                                    }
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }

    public String startBlinkingRedRequest() {
        String url = ESP32_URL + "/start-blinking-red";

        return webClient.post()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // HTML hata sayfası gelmişse özel hata mesajı dön
                                    if (errorBody.contains("<html") || errorBody.contains("<!DOCTYPE html")) {
                                        return Mono.error(new RuntimeException(ErrorMessages.getEsp32ConnectionFailed()));
                                    }
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }

    public String stopBlinkingRedRequest() {
        String url = ESP32_URL + "/stop-blinking-red";

        return webClient.post()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // HTML hata sayfası gelmişse özel hata mesajı dön
                                    if (errorBody.contains("<html") || errorBody.contains("<!DOCTYPE html")) {
                                        return Mono.error(new RuntimeException(ErrorMessages.getEsp32ConnectionFailed()));
                                    }
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }

    public String startBlinkingYellowRequest() {
        String url = ESP32_URL + "/start-blinking-yellow";

        return webClient.post()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // HTML hata sayfası gelmişse özel hata mesajı dön
                                    if (errorBody.contains("<html") || errorBody.contains("<!DOCTYPE html")) {
                                        return Mono.error(new RuntimeException(ErrorMessages.getEsp32ConnectionFailed()));
                                    }
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }

    public String stopBlinkingYellowRequest() {
        String url = ESP32_URL + "/stop-blinking-yellow";

        return webClient.post()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    // HTML hata sayfası gelmişse özel hata mesajı dön
                                    if (errorBody.contains("<html") || errorBody.contains("<!DOCTYPE html")) {
                                        return Mono.error(new RuntimeException(ErrorMessages.getEsp32ConnectionFailed()));
                                    }
                                    return Mono.error(new RuntimeException(errorBody));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }
}
