package com.dwaldron.springbootgraphqlbookapi.repositories;

import com.dwaldron.springbootgraphqlbookapi.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
