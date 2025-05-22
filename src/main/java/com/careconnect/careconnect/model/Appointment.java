package com.careconnect.careconnect.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Getters and Setters
    public Long getId() { return id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}
