package com.dwaldron.springbootgraphqlbookapi.repositories;

import com.dwaldron.springbootgraphqlbookapi.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
