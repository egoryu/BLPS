package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Record")
@Data
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "header", nullable = false, length = 32)
    private String header;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "rating", columnDefinition = "int default 0")
    private int rating;

    @Column(name = "type", columnDefinition = "int default 0")
    private int type;

    /*@ManyToMany
    @JoinTable(
            name = "rubric_record",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "rubric_id")
    )
    private List<Rubric> rubrics = new ArrayList<>();*/

    public Record(User user, String header, String body, LocalDateTime createTime, int rating, int type) {
        this.user = user;
        this.header = header;
        this.body = body;
        this.createTime = createTime;
        this.rating = rating;
        this.type = type;
    }
}
