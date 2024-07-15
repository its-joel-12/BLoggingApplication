package com.joel.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name = "comm_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentIdGenerator")
    @SequenceGenerator(name = "commentIdGenerator", sequenceName = "comment_id_sequence", allocationSize = 1, initialValue = 10001)
    private Long commentId;

    @Column(name = "comm_content")
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;
}
