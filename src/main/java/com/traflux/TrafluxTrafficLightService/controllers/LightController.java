package com.traflux.TrafluxTrafficLightService.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.traflux.TrafluxTrafficLightService.entities.LightModel;
import com.traflux.TrafluxTrafficLightService.enums.LightStatus;
import com.traflux.TrafluxTrafficLightService.helper.ErrorMessages;
import com.traflux.TrafluxTrafficLightService.helper.SuccessMessages;
import com.traflux.TrafluxTrafficLightService.services.LightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/light")
public class LightController {
    private final LightService lightService;

    @Autowired
    public LightController(LightService lightService) {
        this.lightService = lightService;
    }

    @GetMapping("/getlights")
    public ResponseEntity<?> getAllTrafficLights() {
        try {
            List<LightModel> trafficLights = lightService.getAllTrafficLights();
            return ResponseEntity.status(HttpStatus.OK).body(trafficLights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessages.getGetLightsFailed());
        }
    }

    @GetMapping("/getlight/{lightId}")
    public ResponseEntity<?> getTrafficLight(@PathVariable Long lightId) {
        try {
            LightModel trafficLight = lightService.getTrafficLight(lightId);
            return ResponseEntity.status(HttpStatus.OK).body(trafficLight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessages.getGetLightByIdFailed());
        }
    }

    @PostMapping("/createlight")
    public ResponseEntity<String> saveTrafficLight(@RequestBody LightModel lightRequest) {
        try {
            lightService.saveTrafficLight(lightRequest.getLightStatus(), lightRequest.getDuration());
            return ResponseEntity.status(HttpStatus.CREATED).body(SuccessMessages.getSuccessLightCreate());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessages.getCreateLightFailed());
        }
    }

    @PutMapping("/updatelight/{lightId}")
    public ResponseEntity<?> updateTrafficLightById(@PathVariable Long lightId, @RequestBody LightModel lightRequest) {
        try {
            LightModel trafficLight = lightService.updateTrafficLightById(lightId, lightRequest.getLightStatus(), lightRequest.getDuration());
            return ResponseEntity.status(HttpStatus.OK).body(trafficLight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessages.getUpdateLightFailed());
        }
    }

    @DeleteMapping("/deletelight/{lightId}")
    public ResponseEntity<String> deleteTrafficLightById(@PathVariable Long lightId) {
        try {
            lightService.deleteTrafficLightById(lightId);
            return ResponseEntity.status(HttpStatus.OK).body(SuccessMessages.getSuccessLightDelete());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessages.getDeleteLightFailed());
        }
    }

    @PostMapping("/vehicle-green")
    public ResponseEntity<String> vehicleGreen(@RequestParam(required = false) Integer duration) {
        try {
            String response = lightService.sendVehicleGreenRequest(duration);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/pedestrian-green")
    public ResponseEntity<String> pedestrianGreen(@RequestParam(required = false) Integer duration) {
        try {
            String response = lightService.sendPedestrianGreenRequest(duration);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/start-blinking-red")
    public ResponseEntity<String> startBlinkingRed() {
        try {
            String response = lightService.startBlinkingRedRequest();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/stop-blinking-red")
    public ResponseEntity<String> stopBlinkingRed() {
        try {
            String response = lightService.stopBlinkingRedRequest();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/start-blinking-yellow")
    public ResponseEntity<String> startBlinkingYellow() {
        try {
            String response = lightService.startBlinkingYellowRequest();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/stop-blinking-yellow")
    public ResponseEntity<String> stopBlinkingYellow() {
        try {
            String response = lightService.stopBlinkingYellowRequest();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidEx) {
            // Hatalı olan alanın adını alıyoruz
            String fieldName = invalidEx.getPath().get(0).getFieldName();

            // Eğer sadece "lightStatus" alanı hatalıysa özel hata mesajı döndür
            if ("lightStatus".equals(fieldName) && invalidEx.getTargetType() == LightStatus.class) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ErrorMessages.getInvalidLightStatus());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body.");
    }
}
