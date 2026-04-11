package org.example.fullystudentmanagement.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Submission {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private User student;

    private String answerText;

    private double grade;

    private String feedback;

    private LocalDateTime submittedAt;


}
