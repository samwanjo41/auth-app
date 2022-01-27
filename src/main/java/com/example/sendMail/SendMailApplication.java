package com.example.sendMail;

import com.example.sendMail.domain.MyUser;
import com.example.sendMail.domain.Role;
import com.example.sendMail.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
//@EnableCaching
public class SendMailApplication implements CommandLineRunner {

	@Autowired
	MyUserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SendMailApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ArrayList<Role> roles = new ArrayList<>();
	Role role = new Role();
	role.setName("ROLE_ADMIN");
	roles.add(role);

		MyUser user = new MyUser();
		user.setId(1L);
		user.setFirstName("Geor");
		user.setLastName("Kamal");
		user.setEmail("geolkamal@gmail.com");
		user.setPassword(bCryptPasswordEncoder.encode("password"));
		user.setRole(roles);
		userRepository.save(user);

	}
}
