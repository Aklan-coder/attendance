package com.softcorridor.attendance;

import com.softcorridor.attendance.enums.Role;
import com.softcorridor.attendance.model.Client;
import com.softcorridor.attendance.model.User;
import com.softcorridor.attendance.repository.ClientRepository;
import com.softcorridor.attendance.repository.UserRepository;
import com.softcorridor.attendance.util.AttendanceUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class AttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, ClientRepository clientRepository) {
        return args -> {

            if (!userRepository.isPhoneExist("07035821876") && (!userRepository.isEmailExist("info@softcorridor.com"))) {
                User user = new User();

                user.setFirstname("Jante");
                user.setLastname("Adebowale");
                user.setPhone("07035821876");
                user.setPassword(passwordEncoder.encode("12345"));
                user.setRole(Role.ADMIN);
                user.setEmail("info@softcorridor.com");
                user.setUsername("info@softcorridor.com");
                user.setId(UUID.randomUUID().toString());
                user.setCreatedBy(user.getId());
                user.setModifiedBy(user.getId());
                user.setModifiedAt(AttendanceUtil.now());
                userRepository.registerUser(user);
            }

        };
    }


}
