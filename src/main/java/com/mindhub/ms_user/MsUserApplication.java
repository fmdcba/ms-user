package com.mindhub.ms_user;

import com.mindhub.ms_user.models.RoleType;
import com.mindhub.ms_user.models.UserEntity;
import com.mindhub.ms_user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MsUserApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MsUserApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			UserEntity user = new UserEntity("pepe", "pepe@pepe.com", passwordEncoder.encode("pass"), RoleType.USER);
			UserEntity admin = new UserEntity("pepeAdmin", "pepe@admin.com", passwordEncoder.encode("pass") , RoleType.ADMIN);

			userRepository.save(user);
			userRepository.save(admin);
		};
	}

}
