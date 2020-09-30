package com.dwaldron.springbootgraphqlbookapi.resolvers;

import com.dwaldron.springbootgraphqlbookapi.models.Author;
import com.dwaldron.springbootgraphqlbookapi.models.Book;
import com.dwaldron.springbootgraphqlbookapi.repositories.AuthorRepository;
import com.dwaldron.springbootgraphqlbookapi.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

public class Query implements GraphQLQueryResolver {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        Iterable<Book> books = bookRepository.findAll();
        return books;
    }

    public Long countBooks() {
        return bookRepository.count();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Long countAuthors() {
        return authorRepository.count();
    }
}
