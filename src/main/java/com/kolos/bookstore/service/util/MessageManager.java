package com.kolos.bookstore.service.util;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class MessageManager  {

    private static final String BUNDLE_NAME = "messages";
    private final ThreadLocal<ResourceBundle> resourceBundle = ThreadLocal.withInitial(() -> ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()));

    public String getMessage(String key) {
        return resourceBundle.get().getString(key);
    }

    public void setLocale(String lang) {
        resourceBundle.set(ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang)));
    }
}
