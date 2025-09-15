package org.example.bookshop.repository.book;

import org.example.bookshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(attributePaths = "categories")
    Page<Book> findAllByCategories_Id(Long categoryId, Pageable pageable);
}
