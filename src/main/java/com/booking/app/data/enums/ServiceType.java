package com.booking.app.data.enums;

/**
 * Enum representing main types of services available in the booking system.
 */
public enum ServiceType {
    ROMANTICKY_VIKEND("Romantický víkend"),
    WELLNESS_VIKEND("Wellness víkend"),
    GASTRONOMICKY_POBYT("Gastronomický pobyt"),
    KULTURNY_POBYT("Kultúrny pobyt"),
    AKTIVNY_ODPOCINOK("Aktívny oddych"),
    RODINNY_POBYT("Rodinný pobyt"),
    FIREMNY_TEAMBULDING("Firemný teambuilding"),
    LUXUSNY_POBYT("Luxusný pobyt"),
    LAST_MINUTE("Last minute ponuka");

    private final String displayName;

    ServiceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
