package com.petshop.petshop_api.config;



import com.petshop.petshop_api.model.Roles;
import com.petshop.petshop_api.model.RoleName;
import com.petshop.petshop_api.repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RolesConfig {

    @Bean
    public CommandLineRunner cadastrarRoles(RolesRepository rolesRepository) {
        return args -> {
            for (RoleName roleName : RoleName.values()) {
                if (rolesRepository.findByName(roleName).isEmpty()) {
                    Roles role = new Roles();
                    role.setName(roleName);
                    rolesRepository.save(role);
                }
            }
        };
    }
}
