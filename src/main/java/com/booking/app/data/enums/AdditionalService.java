package com.booking.app.data.enums;

/**
 * Enum representing additional/optional services that the customer can select.
 */
public enum AdditionalService {
    UBYTOVANIE("Ubytovanie"),
    STRAVA_RANAJKY("Raňajky"),
    STRAVA_POLPENZIA("Polpenzia"),
    STRAVA_PLNA_PENZIA("Plná penzia"),
    PARKOVANIE("Parkovanie"),
    MASAZ("Masáž"),
    SPORTOVE_VYBAVENIE("Športové vybavenie"),
    VYLET("Výlet"),
    DOPRAVA("Doprava"),
    POZICOVNA_AUT("Požičovňa áut"),
    POZICOVNA_BICYKLOV("Požičovňa bicyklov"),
    LAST_MINUTE("Last minute");

    private final String displayName;

    AdditionalService(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
