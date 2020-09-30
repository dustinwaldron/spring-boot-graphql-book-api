package com.dwaldron.springbootgraphqlbookapi;

import com.dwaldron.springbootgraphqlbookapi.exceptions.GraphQLErrorAdapter;
import com.dwaldron.springbootgraphqlbookapi.models.Author;
import com.dwaldron.springbootgraphqlbookapi.models.Book;
import com.dwaldron.springbootgraphqlbookapi.repositories.AuthorRepository;
import com.dwaldron.springbootgraphqlbookapi.repositories.BookRepository;
import com.dwaldron.springbootgraphqlbookapi.resolvers.BookResolver;
import com.dwaldron.springbootgraphqlbookapi.resolvers.Mutation;
import com.dwaldron.springbootgraphqlbookapi.resolvers.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringBootGraphqlBookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootGraphqlBookApiApplication.class, args);
	}

	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

	@Bean
	public BookResolver authorResolver(AuthorRepository authorRepository) {
		return new BookResolver(authorRepository);
	}

	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Query(authorRepository, bookRepository);
	}

	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Mutation(authorRepository, bookRepository);
	}

	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return (args) -> {
			Author author = new Author("George", "Orwell");
			authorRepository.save(author);

			bookRepository.save(new Book("1984", "8422615797", 1984, author));
		};
	}

}
