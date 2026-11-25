package com.petshop.petshop_api.config;

import com.petshop.petshop_api.model.RoleName;
import com.petshop.petshop_api.model.Roles;
import com.petshop.petshop_api.model.Tutor;
import com.petshop.petshop_api.repository.RolesRepository;
import com.petshop.petshop_api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Inicializador temporário para criar um usuário administrador no ambiente local.
 * Remover esta classe após concluir os testes da área administrativa.
 */
@Configuration
public class AdminInitializer implements CommandLineRunner {

    private final TutorRepository tutorRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean enabled;
    private final String name;
    private final String contact;
    private final String address;
    private final String email;
    private final String cpf;
    private final String password;

    public AdminInitializer(
            TutorRepository tutorRepository,
            RolesRepository rolesRepository,
            PasswordEncoder passwordEncoder,
            @Value("${ADMIN_INITIALIZER_ENABLED:true}") boolean enabled,
            @Value("${ADMIN_INITIAL_NAME:Administrador AmigoPet}") String name,
            @Value("${ADMIN_INITIAL_CONTACT:11999999999}") String contact,
            @Value("${ADMIN_INITIAL_ADDRESS:Endereço de desenvolvimento}") String address,
            @Value("${ADMIN_INITIAL_EMAIL:admin@amigopet.local}") String email,
            @Value("${ADMIN_INITIAL_CPF:99999999999}") String cpf,
            @Value("${ADMIN_INITIAL_PASSWORD:Admin@123}") String password) {
        this.tutorRepository = tutorRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.enabled = enabled;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (!enabled) {
            return;
        }

        var tutorByCpf = tutorRepository.findByCpf(cpf);
        var tutorByEmail = tutorRepository.findByEmail(email);

        if (tutorByCpf.isPresent() && tutorByEmail.isPresent()
                && !tutorByCpf.get().getId().equals(tutorByEmail.get().getId())) {
            throw new IllegalStateException(
                    "Não foi possível inicializar o administrador: CPF e e-mail já pertencem a tutores diferentes."
            );
        }

        Tutor admin = tutorByCpf.orElseGet(() -> tutorByEmail.orElseGet(() -> {
            Tutor novoTutor = new Tutor();
            novoTutor.setNome(name);
            novoTutor.setContato(contact);
            novoTutor.setEndereco(address);
            novoTutor.setEmail(email);
            novoTutor.setCpf(cpf);
            novoTutor.setSenha(passwordEncoder.encode(password));
            return novoTutor;
        }));

        Roles adminRole = rolesRepository.findByName(RoleName.ADMIN)
                .orElseGet(() -> rolesRepository.save(new Roles(RoleName.ADMIN)));

        if (!admin.getRoles().contains(adminRole)) {
            admin.getRoles().add(adminRole);
            tutorRepository.save(admin);
        }
    }
}
