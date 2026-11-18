package com.petshop.petshop_api.service;

import com.petshop.petshop_api.dto.request.tutor.TutorPerfilRequest;
import com.petshop.petshop_api.dto.response.animal.AnimalTutorResponseDTO;
import com.petshop.petshop_api.dto.response.tutor.TutorResponseDTO;
import com.petshop.petshop_api.exception.RecursoEmUsoException;
import com.petshop.petshop_api.exception.RecursoNaoEncontradoException;
import com.petshop.petshop_api.model.Animal;
import com.petshop.petshop_api.model.Tutor;
import com.petshop.petshop_api.repository.AgendamentoRepository;
import com.petshop.petshop_api.repository.AnimalRepository;
import com.petshop.petshop_api.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final AnimalRepository animalRepository;
    private final AgendamentoRepository agendamentoRepository;

    public TutorService(TutorRepository tutorRepository,
                        AnimalRepository animalRepository,
                        AgendamentoRepository agendamentoRepository) {
        this.tutorRepository = tutorRepository;
        this.animalRepository = animalRepository;
        this.agendamentoRepository = agendamentoRepository;
    }


    public TutorResponseDTO buscarTutorPorCpf(String cpf) {

        Tutor tutorEncontrado = tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tutor não encontrado!, cpf incorreto! "
                ));

        return new TutorResponseDTO(
                tutorEncontrado.getNome(),
                tutorEncontrado.getContato(),
                tutorEncontrado.getEndereco(),
                tutorEncontrado.getCpf()
        );
    }


    @Transactional
    public void removerPorCpf(String cpf) {

        Tutor tutorEncontrado = tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tutor não encontrado!, cpf incorreto! "
                ));

        if (animalRepository.existsByTutorId(tutorEncontrado.getId())
                || agendamentoRepository.existsByTutorId(tutorEncontrado.getId())) {

            throw new RecursoEmUsoException(
                    "Não é possível remover o tutor, pois existem animais ou agendamentos vinculados a ele."
            );
        }

        tutorRepository.deleteByCpf(tutorEncontrado.getCpf());

    }


    public List<AnimalTutorResponseDTO> animaisVinculados(String cpf) {

        Tutor tutorEncontrado = tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tutor não encontrado!, cpf incorreto! "
                ));

        List<Animal> vinculados = tutorEncontrado.getAnimals();

       return vinculados.stream()
               .map(animal -> new AnimalTutorResponseDTO(
                       animal.getId(),
                       animal.getNome(),
                       animal.getRaca(),
                       animal.getPeso()
               ))
               .toList();
    }

    public TutorResponseDTO meuPerfil(Integer idTutor) {

        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tutor não existente"
                ));

        return new TutorResponseDTO(
                tutor.getNome(),
                tutor.getContato(),
                tutor.getEndereco(),
                tutor.getCpf()
        );
    }

    public TutorResponseDTO updatePerfil(Integer idTutor, TutorPerfilRequest request){

        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tutor não existente"
                ));

        tutor.setNome(request.nome());
        tutor.setContato(request.contato());
        tutor.setEndereco(request.endereco());

        tutorRepository.save(tutor);

        return new TutorResponseDTO(
                tutor.getNome(),
                tutor.getContato(),
                tutor.getEndereco(),
                tutor.getCpf()
        );
    }
}
