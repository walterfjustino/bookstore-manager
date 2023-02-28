package com.api.bookstoremanager.books.repository;


import com.api.bookstoremanager.books.entity.Book;
import com.api.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findByNameAndIsbnAndUser(String name, String isbn, User foundUser);
  Optional<Book> findByIdAndUser(Long id, User user);
  List<Book> findAllByUser(User user);
  void deleteByIdAndUser(Long id, User user);
}
