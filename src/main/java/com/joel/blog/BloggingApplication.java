package com.joel.blog;

import com.joel.blog.config.AppConstants;
import com.joel.blog.model.Role;
import com.joel.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class BloggingApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BloggingApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role();
        roleAdmin.setRoleId(AppConstants.ROLE_ADMIN);
        roleAdmin.setRoleName("ROLE_ADMIN");

        Role roleNormal = new Role();
        roleNormal.setRoleId(AppConstants.ROLE_NORMAL);
        roleNormal.setRoleName("ROLE_NORMAL");

        List<Role> roles = List.of(roleAdmin, roleNormal);

        List<Role> allRoles = roleRepo.saveAll(roles);

        allRoles.forEach(r -> System.out.println(r.getRoleName()));
    }
}
