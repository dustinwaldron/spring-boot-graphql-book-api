package com.dwaldron.springbootgraphqlbookapi.resolvers;

import com.dwaldron.springbootgraphqlbookapi.models.Author;
import com.dwaldron.springbootgraphqlbookapi.models.Book;
import com.dwaldron.springbootgraphqlbookapi.repositories.AuthorRepository;
import graphql.kickstart.tools.GraphQLResolver;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findById(book.getAuthor().getId()).get();
    }
}
