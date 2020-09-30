package com.dwaldron.springbootgraphqlbookapi.contexts;

import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.servlet.context.*;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

    private final DataLoader authorLoader;

    public CustomGraphQLContextBuilder(DataLoader authorLoader) {
        this.authorLoader = authorLoader;
    }

    public DefaultGraphQLContext build() {
        return new DefaultGraphQLContext();
    }

    public GraphQLServletContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return DefaultGraphQLServletContext.createServletContext()
                .with(httpServletRequest)
                .with(httpServletResponse)
                .with(buildDataLoaderRegistry())
                .build();
    }

    public GraphQLWebSocketContext build(Session session, HandshakeRequest handshakeRequest) {
        return DefaultGraphQLWebSocketContext.createWebSocketContext()
                .with(session)
                .with(handshakeRequest)
                .with(buildDataLoaderRegistry())
                .build();
    }

    private DataLoaderRegistry buildDataLoaderRegistry() {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register("authorLoader", authorLoader);
        return registry;
    }
}
