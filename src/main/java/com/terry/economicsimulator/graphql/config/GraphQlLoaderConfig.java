package com.terry.economicsimulator.graphql.config;

import com.terry.economicsimulator.graphql.dataloaders.ResourceDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.BatchLoaderRegistry;

import static reactor.core.publisher.Mono.fromCompletionStage;

@Configuration
public class GraphQlLoaderConfig {
  GraphQlLoaderConfig(
      BatchLoaderRegistry registry,
      ResourceDataLoader resourceDataLoader) {

    registerLoader(registry, resourceDataLoader, ResourceDataLoader.NAME);
  }

  public static <F, M> void registerLoader(BatchLoaderRegistry registry, MappedBatchLoader<F, M> loader, String keyName) {
    BatchLoaderRegistry.RegistrationSpec<F, M> registrar = registry.forName(keyName);
    registrar.registerMappedBatchLoader((key, env) ->
        fromCompletionStage(loader.load(key)));
  }
}
