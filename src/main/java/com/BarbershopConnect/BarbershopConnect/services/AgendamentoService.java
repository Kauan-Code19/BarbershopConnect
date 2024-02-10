package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.AgendamentoDTO;
import com.BarbershopConnect.BarbershopConnect.dto.AgendamentoResponseDTO;
import com.BarbershopConnect.BarbershopConnect.entities.*;
import com.BarbershopConnect.BarbershopConnect.repositories.AgendamentoRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.ClienteRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.TipoDoCorteRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.AtualizacaoNaoProcessadaException;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.DatabaseException;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.ResourceNotFoundException;
import com.BarbershopConnect.BarbershopConnect.services.interfaces.StatusAgendamentoService;
import com.BarbershopConnect.BarbershopConnect.services.status_services.AgendadoService;
import com.BarbershopConnect.BarbershopConnect.services.status_services.CanceladoService;
import com.BarbershopConnect.BarbershopConnect.services.status_services.ConcluidoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeariaRepository barbeariaRepository;
    private final TipoDoCorteRepository tipoDoCorteRepository;
    private final EmailService emailService;
    private final StatusAgendamentoService statusAgendamentoService;

    @Qualifier("agendado")
    private final AgendadoService agendadoService;
    @Qualifier("concluido")
    private final ConcluidoService concluidoService;
    @Qualifier("cancelado")
    private final CanceladoService canceladoService;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, BarbeariaRepository barbeariaRepository, TipoDoCorteRepository tipoDoCorteRepository, EmailService emailService, StatusAgendamentoService statusAgendamentoService, AgendadoService agendadoService, ConcluidoService concluidoService, CanceladoService canceladoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.barbeariaRepository = barbeariaRepository;
        this.tipoDoCorteRepository = tipoDoCorteRepository;
        this.emailService = emailService;
        this.statusAgendamentoService = statusAgendamentoService;
        this.agendadoService = agendadoService;
        this.concluidoService = concluidoService;
        this.canceladoService = canceladoService;
    }

    @Transactional
    public AgendamentoResponseDTO agendar (Long id, AgendamentoDTO agendamentoDTO) {
        if (!clienteRepository.existsById(id) || !barbeariaRepository.existsById(agendamentoDTO.getIdBarbearia()) ||
                !tipoDoCorteRepository.existsById(agendamentoDTO.getIdTipoDoCorte())) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        Cliente cliente = clienteRepository.getReferenceById(id);

        if (cliente.getAgendamento() != null) {
            throw new DatabaseException("Falha de integridade referencial");
        }

        Agendamento entity = new Agendamento();

        Barbearia barbearia = barbeariaRepository.getReferenceById(agendamentoDTO.getIdBarbearia());
        TipoDoCorte tipoDoCorte = tipoDoCorteRepository.getReferenceById(agendamentoDTO.getIdTipoDoCorte());

        LocalDateTime dataHora = LocalDateTime.now();

        dataHora = dataHora.withHour(agendamentoDTO.getHorarioEscolhido().getHour());
        dataHora = dataHora.withMinute(agendamentoDTO.getHorarioEscolhido().getMinute());
        dataHora = dataHora.withSecond(0);
        dataHora = dataHora.withNano(0);

        if (dataHora.getDayOfWeek() != agendamentoDTO.getDiaDaSemana()) {
            switch (agendamentoDTO.getDiaDaSemana()) {
                case MONDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                case TUESDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                case WEDNESDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                case THURSDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                case FRIDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                case SATURDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                case SUNDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            }
        }

        entity.setCliente(cliente);
        entity.setBarbearia(barbearia);
        entity.setTipoDoCorte(tipoDoCorte);
        entity.setDataHora(dataHora);

        entity = agendamentoRepository.save(entity);

        statusAgendamentoService.setStatus(entity.getId(), Optional.empty());

        enviarEmailAgendamentoParaBarbearia(entity);

        return new AgendamentoResponseDTO(entity);
    }

    @Transactional
    public AgendamentoResponseDTO atualizarAgendamento (Long id, AgendamentoDTO agendamentoDTO) {
        if (!clienteRepository.existsById(id) || !barbeariaRepository.existsById(agendamentoDTO.getIdBarbearia()) ||
                !tipoDoCorteRepository.existsById(agendamentoDTO.getIdTipoDoCorte())) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        Agendamento entity = agendamentoRepository.getReferenceById(id);

        if (entity.getStatus() == Status.CANCELADO || entity.getStatus() == Status.CONCLUIDO) {
            throw new AtualizacaoNaoProcessadaException("Agendamento não pode ser atualizado pois está cancelado ou concluído.");
        }

        TipoDoCorte tipoDoCorte = tipoDoCorteRepository.getReferenceById(agendamentoDTO.getIdTipoDoCorte());

        LocalDateTime dataHora = LocalDateTime.now();

        dataHora = dataHora.withHour(agendamentoDTO.getHorarioEscolhido().getHour());
        dataHora = dataHora.withMinute(agendamentoDTO.getHorarioEscolhido().getMinute());
        dataHora = dataHora.withSecond(0);
        dataHora = dataHora.withNano(0);

        if (dataHora.getDayOfWeek() != agendamentoDTO.getDiaDaSemana()) {
            switch (agendamentoDTO.getDiaDaSemana()) {
                case MONDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                case TUESDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                case WEDNESDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                case THURSDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                case FRIDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                case SATURDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                case SUNDAY -> dataHora = dataHora.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            }
        }

        entity.setTipoDoCorte(tipoDoCorte);
        entity.setDataHora(dataHora);

        statusAgendamentoService.setStatus(entity.getId(), Optional.empty());

        enviarEmailAtualizadoDoAgendamentoParaBarbearia(entity);

        return new AgendamentoResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarAgendamento (Long id) {
        try {
            Cliente cliente = clienteRepository.getReferenceById(id);

            Agendamento entity = cliente.getAgendamento();

            return new AgendamentoResponseDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Recurso não encontrado");
        }
    }

    private void enviarEmailAgendamentoParaBarbearia(Agendamento agendamento) {
        String emailBarbearia = agendamento.getBarbearia().getEmail();
        emailService.enviarEmailAgendamento(emailBarbearia, new AgendamentoResponseDTO(agendamento));
    }

    private void enviarEmailAtualizadoDoAgendamentoParaBarbearia(Agendamento agendamento) {
        String emailBarbearia = agendamento.getBarbearia().getEmail();
        emailService.enviarEmailAtualizadoDoAgendamento(emailBarbearia, new AgendamentoResponseDTO(agendamento));
    }

    @Transactional
    @Scheduled(fixedRate = 1800000)
    public void autoConcluirAgendamentos() {
        LocalDateTime now = LocalDateTime.now();

        List<Agendamento> agendamentosAgendados = agendamentoRepository.findByStatusAndDataHoraBefore(Status.AGENDADO, now);

        for (Agendamento agendamento : agendamentosAgendados) {
            concluidoService.setStatus(agendamento.getId(), Optional.empty());
        }
    }

    @Transactional
    @Scheduled(fixedRate = 900000)
    public void autoDeletarAgendamentosCancelados() {
        List<Agendamento> agendamentosCancelados = agendamentoRepository.findByStatus(Status.CANCELADO);

        for (Agendamento agendamento : agendamentosCancelados) {
            agendamentoRepository.deleteByIdCustom(agendamento.getId());
            System.out.println("Agendamento cancelado deletado: " + agendamento.getId());
        }
    }

    public Status confirmarAgendamento (Long id, Boolean b) {
        agendadoService.setStatus(id, Optional.of(b));
        return Status.AGENDADO;
    }

    public Status cancelarAgendamento (Long id, Boolean b) {
        canceladoService.setStatus(id, Optional.of(b));
        return Status.CANCELADO;
    }
}