package com.reactjs.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.reactjs.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final RsaKeyProperties rsaKeys;
   // private final AuthenticationLoggingFilter authenticationLoggingFilter = null;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    //public SecurityConfig(RsaKeyProperties rsaKeys, AuthenticationLoggingFilter authenticationLoggingFilter) {
    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
       // this.authenticationLoggingFilter = authenticationLoggingFilter;
    }

//    @Bean
//    public InMemoryUserDetailsManager user(){
//        System.out.println("Inside React APP user()@SecurityConfig:: .....................");
//        return new InMemoryUserDetailsManager(
//                User.withUsername("KVRM")
//                        .password("{noop}password")
//                        .authorities("read")
//                        .roles("USER", "ADMIN")
//                        .build()
//        );
//    }

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
        System.out.println("Inside React APP securityFilterChain()@SecurityConfig:: ....................." + httpSecurity.toString());

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                // csrf -> csrf.disable()
                .authorizeHttpRequests( (auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/loginn").permitAll()
                        .requestMatchers("/token").permitAll()
                        //.requestMatchers("/static/*").permitAll()
                        //.requestMatchers("/api/").authenticated()
                        .anyRequest().authenticated())
                // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) deprecated..
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                //.httpBasic(withDefaults())
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        System.out.println("Inside React APP jwtDecoder()@SecurityConfig:: .....................");
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        System.out.println("Inside React APP jwtEncoder()@SecurityConfig:: .....................");
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        System.out.println("NimbusJwtEncoder(jwks) = " + new NimbusJwtEncoder(jwks) );
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

}