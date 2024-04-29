package com.aadityadesigners.tutorials.bootstrap;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfig {

  // Configure security filter chain
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(request -> request
            .requestMatchers("/api/**").authenticated()
            .requestMatchers("/public/api/**").permitAll()
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  /**
   * JwtAuthenticationConverter jwtAuthenticationConverter() {
   * JwtGrantedAuthoritiesConverter converter = new
   * JwtGrantedAuthoritiesConverter();
   * converter.setAuthorityPrefix("SCOPE_"); // Make sure this matches the prefix
   * in your hasAuthority check
   * converter.setAuthoritiesClaimName("scope"); // This is the default claim name
   * for scopes in JWT
   * 
   * JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
   * jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
   * return jwtConverter;
   * }
   **/
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());
    return jwtConverter;
  }

  private static class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    public CustomJwtGrantedAuthoritiesConverter() {
      defaultConverter.setAuthorityPrefix("SCOPE_");
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
      Pattern SCOPE_PREFIX_PATTERN = Pattern.compile("^.*/");

      Collection<GrantedAuthority> authorities = defaultConverter.convert(jwt);
      return authorities.stream()
          .map(grantedAuthority -> {
            String authority = grantedAuthority.getAuthority();
            System.out.println("### authority: " + authority);
            // remove the prefix like 'identifier/' from the scopes.
            Matcher matcher = SCOPE_PREFIX_PATTERN.matcher(authority);
            if (matcher.find()) {
              System.out.println("### matcher: " + authority.substring(matcher.end()));
              return new SimpleGrantedAuthority("SCOPE_" + authority.substring(matcher.end()));
            }
            return grantedAuthority;
          })
          .collect(Collectors.toList());
    }
  }
}
