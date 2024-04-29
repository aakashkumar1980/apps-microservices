package com.aadityadesigners.tutorials.bootstrap;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import com.aadityadesigners.tutorials.utils.AppUtility;

public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    int port = AppUtility.getServerPortFromCloud();

    if (port != -1) {
      Map<String, Object> map = new HashMap<>();
      map.put("server.port", port);

      MapPropertySource propertySource = new MapPropertySource("custom", map);
      environment.getPropertySources().addFirst(propertySource);
    }
  }
}
