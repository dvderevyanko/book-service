package com.book.adapter.output.entity;

import com.book.domain.model.BookType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @OneToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BookType type;
    @Column(name = "total_rate", nullable = false)
    private Integer totalRate;
    @Column(name = "rate_count", nullable = false)
    private Integer rateCount;
    @Column(name = "average_rate", nullable = false)
    private Integer averageRate;
}