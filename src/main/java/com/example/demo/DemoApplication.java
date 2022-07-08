package com.example.demo;

import com.example.demo.entity.auth.AuthUser;
import com.example.demo.enums.AuthRole;
import com.example.demo.properties.OpenApiProperties;
import com.example.demo.properties.ServerProperties;
import com.example.demo.repository.auth.AuthUserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Date;

@EnableConfigurationProperties({
		OpenApiProperties.class,
		ServerProperties.class
})

@OpenAPIDefinition
@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaRepositories
public class DemoApplication {
	private final AuthUserRepository authUserRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofSeconds(60))
				.setReadTimeout(Duration.ofSeconds(60))
				.build();
	}

//	@Bean
	CommandLineRunner runner() {
		return (args) -> {
			authUserRepository.deleteAll();
			String encode = passwordEncoder.encode("123");
			System.out.println("encode = " + encode);

			AuthUser admin = AuthUser.childBuilder()
					.phoneNumber("+998943123858")
					.password(encode)
					.firstName("Sobirjon")
					.lastName("Eshniyazov")
					.email("sobirjoneshniyozov@gmail.com")
					.roles(AuthRole.ADMIN)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.date(new Date())
					.build();
			authUserRepository.save(admin);
		};
	}
}