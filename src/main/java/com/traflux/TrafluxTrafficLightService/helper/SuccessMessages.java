package com.traflux.TrafluxTrafficLightService.helper;

public class SuccessMessages {

    private static final String SUCCESS_LIGHT_CREATE = "Trafik Işığı Başarıyla Oluşturuldu";

    private static final String SUCCESS_LIGHT_UPDATE = "Trafik Işığı Başarıyla Güncellendi";

    private static final String SUCCESS_LIGHT_DELETE = "Trafik Işığı Başarıyla Silindi";

    public static String getSuccessLightCreate() { return SUCCESS_LIGHT_CREATE; }

    public static String getSuccessLightUpdate() {
        return SUCCESS_LIGHT_UPDATE;
    }

    public static String getSuccessLightDelete() {
        return SUCCESS_LIGHT_DELETE;
    }

}
