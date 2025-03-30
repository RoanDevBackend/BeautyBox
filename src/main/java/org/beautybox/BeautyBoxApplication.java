package org.beautybox;

import lombok.RequiredArgsConstructor;
import org.beautybox.entity.Role;
import org.beautybox.repository.RoleRepo;
import org.beautybox.repository.UserRepo;
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

    final RoleRepo roleRepo;
    final UserRepo userRepo;
    final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if(!roleRepo.existsByName("ROLE_ADMIN")) {
            roleRepo.save(new Role("ROLE_ADMIN"));
        }
        if(!roleRepo.existsByName("ROLE_USER")) {
            roleRepo.save(new Role("ROLE_USER"));
        }
        if(!userRepo.existsByEmail("beautybox@gmail.com")) {
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
