package com.websemantique.graphql.configuration;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Configuration
public class GraphQLScalarConfiguration {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(GraphQLScalarType.newScalar()
                        .name("LocalDateTime")
                        .description("Custom scalar for handling LocalDateTime in format 'yyyy-MM-ddTHH:mm:ss'")
                        .coercing(new Coercing<LocalDateTime, String>() {
                            @Override
                            public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) throws CoercingSerializeException {
                                if (dataFetcherResult instanceof LocalDateTime dateTime) {
                                    return FORMATTER.format(dateTime);
                                }
                                throw new CoercingSerializeException("Expected a LocalDateTime object.");
                            }

                            @Override
                            public LocalDateTime parseLiteral(Value<?> input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
                                if (input instanceof StringValue stringValue) {
                                    try {
                                        return LocalDateTime.parse(stringValue.getValue(), FORMATTER);
                                    } catch (DateTimeParseException e) {
                                        throw new CoercingParseLiteralException(
                                                "Invalid LocalDateTime literal. Expected 'yyyy-MM-ddTHH:mm:ss'.", e);
                                    }
                                }
                                throw new CoercingParseLiteralException("Expected a StringValue for LocalDateTime literal.");
                            }
                        }).build());

    }
}
