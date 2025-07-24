package com.gloziksoft.booking.models.dto;

import java.time.LocalDateTime;

public class ReservationDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long userId;
    private String start;
    private String end;

    // --- Gettre a settre ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    // getter a setter pre start
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    // getter a setter pre end
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
