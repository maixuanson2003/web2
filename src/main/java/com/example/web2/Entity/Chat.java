package com.example.web2.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name="actor_id",nullable = false)
    private actor actors;
    @ManyToOne
    @JoinColumn(name="Conversation_id",nullable = false)
    private Conversations Conversation;
    @Column(name = " userSend")
    private String userSend;
    @Column(name = "create_at")
    private String create_at;
    @Column(name = "content")
    private String Content;
    @Column(name = "sendtoUser")
    private String userRceive;
}