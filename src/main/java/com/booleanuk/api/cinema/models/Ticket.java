package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIncludeProperties({"name", "email"})
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    @JsonIncludeProperties({"screenNumber", "startsAt"})
    private Screening screening;

    @Column(nullable = false)
    private Integer numSeats;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime updatedAt;

    public Ticket(Customer customer, Screening screening, Integer numSeats) {
        this.customer = customer;
        this.screening = screening;
        this.numSeats = numSeats;
    }

    public Ticket(int id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneId.systemDefault());
        this.updatedAt = OffsetDateTime.now(ZoneId.systemDefault());
    }

    @PreUpdate
    protected void onUpdate() {
        this.createdAt = this.getCreatedAt();
        this.updatedAt = OffsetDateTime.now(ZoneId.systemDefault());
    }
}
