package com.dwaldron.springbootgraphqlbookapi.resolvers;

import com.dwaldron.springbootgraphqlbookapi.exceptions.NotFoundException;
import com.dwaldron.springbootgraphqlbookapi.models.Author;
import com.dwaldron.springbootgraphqlbookapi.models.Book;
import com.dwaldron.springbootgraphqlbookapi.repositories.AuthorRepository;
import com.dwaldron.springbootgraphqlbookapi.repositories.BookRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;

public class Mutation implements GraphQLMutationResolver {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        authorRepository.save(author);
        return author;
    }

    public Book newBook(String title, String isbn, int pageCount, Long authorId) {
        Book book = new Book(title, isbn, pageCount, new Author(authorId));
        bookRepository.save(book);
        return book;
    }

    public Boolean deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new NotFoundException("The author to be deleted was not found", bookId);
        }
        bookRepository.deleteById(bookId);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new NotFoundException("The book to be updated was not found", bookId);
        }
        book.setPageCount(pageCount);
        bookRepository.save(book);
        return book;
    }
}
