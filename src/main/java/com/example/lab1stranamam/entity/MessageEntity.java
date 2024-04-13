package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
public class MessageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Basic
    @Column(name = "message_text", nullable = false, length = -1)
    private String messageText;
    @Basic
    @Column(name = "type", nullable = false)
    private int type;
    @ManyToOne
    @JoinColumn(name = "user_from", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByFrom;
    @ManyToOne
    @JoinColumn(name = "user_to", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByTo;

    public MessageEntity(LocalDateTime date, String messageText, int type, UsersEntity usersByFrom, UsersEntity usersByTo) {
        this.date = date;
        this.messageText = messageText;
        this.type = type;
        this.usersByFrom = usersByFrom;
        this.usersByTo = usersByTo;
    }
}
