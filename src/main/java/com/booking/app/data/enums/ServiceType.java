    package com.booking.app.data.enums;

    /**
     * Enum representing different types of services available in the booking system.
     */
    public enum ServiceType {
        UBYTOVANIE("Ubytovanie"), // Accommodation
        PARKOVANIE("Parkovanie"), // Parking
        DOPRAVA("Doprava"),       // Transport
        STRAVOVANIE("Stravovanie");// Catering / Food services

        // Human-readable name for each service type (used in UI)
        private final String displayName;

        // Constructor to initialize the display name
        ServiceType(String displayName) {
            this.displayName = displayName;
        }

        // Getter for the display name
        public String getDisplayName() {
            return displayName;
        }
    }
