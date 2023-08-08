
package naf.norsys.reservation.config;

import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.security.JwtAuthorizationFilter;
import naf.norsys.reservation.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.DAYS;
import static naf.norsys.reservation.model.GenericEnum.RoleName.DEV;
import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${cors.allowed-origins}")
    private List<String> corsAllowedOrigins;

    @Value("${cors.allowed-heathers}")
    private List<String> corsAllowedHeathers;

    @Value("${cors.allowed-methods}")
    private List<String> corsAllowedMethods;

    @Configuration
    @Order(1)
    public static class JwtSecurityConfiguration {

        @Value("${jwt.auth.white-list}")
        private List<String> jwtAuthWhiteList;

        private final JwtProvider jwtProvider;
        private final HandlerExceptionResolver handlerExceptionResolver;

        public JwtSecurityConfiguration(JwtProvider jwtProvider, HandlerExceptionResolver handlerExceptionResolver) {
            this.jwtProvider = jwtProvider;
            this.handlerExceptionResolver = handlerExceptionResolver;
        }

        @Bean
        public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {

            AntPathRequestMatcher[] antPathMatchers = jwtAuthWhiteList.stream()
                    .map(AntPathRequestMatcher::new)
                    .toArray(AntPathRequestMatcher[]::new);
            http
                    .cors(Customizer.withDefaults())
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement((session) -> session.sessionCreationPolicy(STATELESS))
                    .securityMatcher("/api/v1/**")
                    .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers(antPathMatchers).permitAll()
                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(new JwtAuthorizationFilter(jwtProvider, jwtAuthWhiteList, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public static class FormSecurityConfiguration {

        @Value("${form.auth.white-list}")
        private List<String> formAuthWhiteList;

        @Value("${form.auth.block-list}")
        private List<String> formAuthBlockList;

        @Value("${form.login.remember-me.key}")
        private String rememberMeKey;

        @Value("${form.login.remember-me.token-validity-in-days}")
        private int rememberMeTokenValidityInDays;

        @Value("${form.success.url}")
        private String formSuccessUrl;

        @Value("${form.logout.cookies-to-clear}")
        private String[] formLogoutCookiesToClear;

        @Bean
        public SecurityFilterChain formFilterChain(HttpSecurity http) throws Exception {
            AntPathRequestMatcher[] formWhiteListAntPathMatchers = formAuthWhiteList.stream()
                    .map(AntPathRequestMatcher::new)
                    .toArray(AntPathRequestMatcher[]::new);
            AntPathRequestMatcher[] formBlockListAntPathMatchers = formAuthBlockList.stream()
                    .map(AntPathRequestMatcher::new)
                    .toArray(AntPathRequestMatcher[]::new);


            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement((session) -> session.sessionCreationPolicy(IF_REQUIRED))
                    .securityMatcher(Stream.concat(formAuthWhiteList.stream(), formAuthBlockList.stream()).toArray(String[]::new))
                    .authorizeHttpRequests((authorize) -> {
                        try {
                            authorize
                                    .requestMatchers(formWhiteListAntPathMatchers).permitAll()
                                    .requestMatchers(formBlockListAntPathMatchers).hasRole(DEV.name())
                                    .anyRequest().authenticated();
                        } catch (Exception e) {
                            throw new BusinessException(e.getMessage(), e.getCause(), null, null);
                        }
                    })
                    .formLogin((form) -> form.defaultSuccessUrl(formSuccessUrl))
                    .rememberMe(rememberMe -> rememberMe
                            .key(rememberMeKey)
                            .tokenValiditySeconds((int) DAYS.toSeconds(rememberMeTokenValidityInDays))
                    )
                    .logout((logout) -> logout
                            .logoutSuccessUrl(formSuccessUrl)
                            .invalidateHttpSession(true)
                            .deleteCookies(formLogoutCookiesToClear)
                    );

            return http.build();
        }

    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsAllowedOrigins);
        //or any domain that you want to restrict to
        configuration.setAllowedHeaders(corsAllowedHeathers);
        configuration.setAllowedMethods(corsAllowedMethods);
        configuration.setAllowCredentials(Boolean.TRUE);
        //Add the method support as you like
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
