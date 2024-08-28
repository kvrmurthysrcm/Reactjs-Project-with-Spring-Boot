package com.reactjs.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public InMemoryUserDetailsManager user(){
        return new InMemoryUserDetailsManager(
                User.withUsername("KVRM")
                        .password("{noop}password")
                        .authorities("read")
                        .build()
        );
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User
//                .withUsername("user")
//                .password("{noop}password")
//                .roles("USER")
//                .build();
//        UserDetails admin = User
//                .withUsername("admin")
//                .password("{noop}password")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers("/").permitAll()
                        //.requestMatchers("/api/").authenticated()
                        //.requestMatchers("/api/v1/").permitAll()
                        //.requestMatchers("/api/v1/employees").authenticated()
                        .anyRequest().authenticated())
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) deprecated..
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(withDefaults())
                //.httpBasic(withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
       return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}