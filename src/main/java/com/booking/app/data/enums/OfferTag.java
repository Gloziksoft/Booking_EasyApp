package com.booking.app.data.enums;

/**
 * Enum representing optional tags or attributes for a booking offer.
 * These are marketing/description tags, max 3–5 per offer.
 */
public enum OfferTag {
    WELLNESS("Wellness"),
    BAZEN("Bazén"),
    WIFI("WiFi"),
    SPA("SPA"),
    KULTURA("Kultúra");

    private final String displayName;

    OfferTag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
