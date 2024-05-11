package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Data
@NoArgsConstructor
public class HistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "message_id", nullable = false)
    private int messageId;
    @Basic
    @Column(name = "user_from", nullable = false, length = 32)
    private String userFrom;
    @Basic
    @Column(name = "user_to", nullable = false, length = 32)
    private String userTo;
    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Basic
    @Column(name = "message_text", nullable = false, length = -1)
    private String messageText;
    @Basic
    @Column(name = "type", nullable = false)
    private int type;
    @Basic
    @Column(name = "log_date", nullable = false)
    private LocalDateTime logDate;

    public HistoryEntity(int messageId, String userFrom, String userTo, LocalDateTime date, String messageText, int type, LocalDateTime logDate) {
        this.messageId = messageId;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.date = date;
        this.messageText = messageText;
        this.type = type;
        this.logDate = logDate;
    }
}
