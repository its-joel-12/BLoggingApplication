package com.joel.blog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postIdGenerator")
    @SequenceGenerator(name = "postIdGenerator", sequenceName = "post_id_sequence", allocationSize = 1, initialValue = 1001)
    private Long postId;

    private String postTitle;

    @Column(length = 1000)
    private String postContent;

    @Column(name = "post_img_name")
    private String postImageName;

    @Column(name = "post_date")
    //@JsonFormat(pattern = "dd-MM-yyyy")
    private Date postAddedDate;

    @ManyToOne
    @JoinColumn(name = "fk_cat_id")
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;
}
