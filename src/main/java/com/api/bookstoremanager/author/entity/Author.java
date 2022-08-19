package com.api.bookstoremanager.author.entity;

import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.entity.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "author")
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "integer default 0")
    private Integer age;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

}
