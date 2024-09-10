package com.kolos.bookstore.service.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MassageManager {
    INSTANCE;

    private static final String BUNDLE_NAME = "messages";
    private final ThreadLocal<ResourceBundle> resourceBundle = ThreadLocal.withInitial(() -> ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()));

    public String getMessage(String key) {
        return resourceBundle.get().getString(key);
    }

    public void setLocale(String lang) {
        resourceBundle.set(ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang)));
    }
}
