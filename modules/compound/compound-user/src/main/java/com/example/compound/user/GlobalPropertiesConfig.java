package com.example.compound.user;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class GlobalPropertiesConfig implements EnvironmentPostProcessor {

	private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Resource path = new ClassPathResource("application.yml");
		List<PropertySource<?>> propertySource = loadYaml(path);
		environment.getPropertySources().addLast(propertySource.get(0));
	}

	private List<PropertySource<?>> loadYaml(Resource path) {
		if (!path.exists()) {
			throw new IllegalArgumentException("Resource " + path + " does not exist");
		}

		try {
			return this.loader.load("custom-resource", path);
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to load yml configuration from " + path, ex);
		}
	}

}