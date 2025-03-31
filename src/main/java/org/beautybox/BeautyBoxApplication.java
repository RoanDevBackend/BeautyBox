package org.beautybox;

import lombok.RequiredArgsConstructor;
import org.beautybox.entity.Role;
import org.beautybox.repository.RoleRepository;
import org.beautybox.repository.UserRepository;
import org.beautybox.request.UserRegisterRequest;
import org.beautybox.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BeautyBoxApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeautyBoxApplication.class, args);
    }

    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final UserService userService;

    @Override
    public void run(String... args){
        if(!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if(!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if(!userRepository.existsByEmail("beautybox@gmail.com")) {
            UserRegisterRequest registerRequest = new UserRegisterRequest();
            registerRequest.setEmail("beautybox@gmail.com");
            registerRequest.setName("BeautyBox");
            registerRequest.setPassword("Admin@1234");
            registerRequest.setGender("Undefined");
            registerRequest.setPhone("Undefined");
            if(userService.register(registerRequest)){
                System.out.println("Register admin account success! ");
            }
        }
    }
}
