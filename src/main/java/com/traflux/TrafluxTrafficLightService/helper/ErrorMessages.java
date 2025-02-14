package com.traflux.TrafluxTrafficLightService.helper;

public class ErrorMessages {
    private static final String LIGHT_NOT_FOUND = "Trafik Işığı Bulunamadı";

    private static final String GET_LIGHTS_FAILED = "Trafik Işıkları Getirilirken Hata Oluştu";

    private static final String GET_LIGHT_BY_ID_FAILED = "Trafik Işığı Getirilirken Hata Oluştu";

    private static final String INVALID_LIGHT_STATUS = "Lütfen Geçerli Bir Trafik Işığı Durumu Giriniz";

    private static final String NULL_LIGHT_STATUS_OR_DURATION = "Trafik Işığı Durumu veya Süresi Boş Olamaz";

    private static final String CREATE_LIGHT_FAILED = "Trafik Işığı Oluşturulurken Hata Oluştu";

    private static final String UPDATE_LIGHT_FAILED = "Trafik Işığı Güncellenirken Hata Oluştu";

    private static final String DELETE_LIGHT_FAILED = "Trafik Işığı Silinirken Hata Oluştu";

    private static final String ESP32_CONNECTION_FAILED = "ESP32 Bağlantısı Başarısız veya Erişilemez";

    public static String getLightNotFound() {
        return LIGHT_NOT_FOUND;
    }

    public static String getGetLightsFailed() {
        return GET_LIGHTS_FAILED;
    }

    public static String getGetLightByIdFailed() {
        return GET_LIGHT_BY_ID_FAILED;
    }

    public static String getInvalidLightStatus() { return INVALID_LIGHT_STATUS; }

    public static String getNullLightStatusOrDuration() { return NULL_LIGHT_STATUS_OR_DURATION; }

    public static String getCreateLightFailed() { return CREATE_LIGHT_FAILED; }

    public static String getUpdateLightFailed() { return UPDATE_LIGHT_FAILED; }

    public static String getDeleteLightFailed() { return DELETE_LIGHT_FAILED; }

    public static String getEsp32ConnectionFailed() { return ESP32_CONNECTION_FAILED; }
}
