package com.terry.economicsimulator.graphql.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

	@Bean
	public RuntimeWiringConfigurer runtimeWiringConfigurer() {
		return wiringBuilder -> wiringBuilder
				.scalar(ExtendedScalars.DateTime)
				.scalar(ExtendedScalars.GraphQLBigDecimal)
				.scalar(ExtendedScalars.Date)
				.scalar(ExtendedScalars.Time)
				.scalar(ExtendedScalars.LocalTime)
				.scalar(ExtendedScalars.GraphQLShort);
	}
}
