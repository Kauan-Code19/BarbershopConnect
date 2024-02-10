package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.AgendamentoResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.base.url}")
    private String urlBase;

    public void enviarEmailAgendamento(String destinatario, AgendamentoResponseDTO agendamento) {
        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject("Confirmação de Agendamento");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String conteudoEmail = "Cliente: " + agendamento.getCliente().getNome() +
                    "<br>E-mail do Cliente: " + agendamento.getCliente().getEmail() +
                    "<br>Contato do Cliente: " + agendamento.getCliente().getContato() +
                    "<br><br>Tipo de Corte: " + agendamento.getTipoDoCorte().getNome() +
                    "<br>Descrição do Corte: " + agendamento.getTipoDoCorte().getDescricao() +
                    "<br>Preço do Corte: " + agendamento.getTipoDoCorte().getPreco() +
                    "<br><br>Data e Hora Escolhida: " + agendamento.getLocalDateTime().format(formatter) +
                    "<br><br>Confirme ou cancele o agendamento:<br>" +
                    "<a href=\"" + urlBase + "/confirmar-agendamento/" + agendamento.getId() + "\">Confirmar</a><br>" +
                    "<a href=\"" + urlBase + "/cancelar-agendamento/" + agendamento.getId() + "\">Cancelar</a>";

            mimeMessageHelper.setText(conteudoEmail, true);

            javaMailSender.send(mensagem);
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void enviarEmailAtualizadoDoAgendamento(String destinatario, AgendamentoResponseDTO agendamento) {
        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject("Confirmação de Agendamento");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String conteudoEmail = "Agendamento Atualizado: " + "O agendamento foi atualizado. Por favor, revise as informações." +
                    "<br><br>Cliente: " + agendamento.getCliente().getNome() +
                    "<br>E-mail do Cliente: " + agendamento.getCliente().getEmail() +
                    "<br>Contato do Cliente: " + agendamento.getCliente().getContato() +
                    "<br><br>Tipo de Corte: " + agendamento.getTipoDoCorte().getNome() +
                    "<br>Descrição do Corte: " + agendamento.getTipoDoCorte().getDescricao() +
                    "<br>Preço do Corte: " + agendamento.getTipoDoCorte().getPreco() +
                    "<br><br>Data e Hora Escolhida: " + agendamento.getLocalDateTime().format(formatter) +
                    "<br><br>Confirme ou cancele o agendamento:<br>" +
                    "<a href=\"" + urlBase + "/confirmar-agendamento/" + agendamento.getId() + "\">Confirmar</a><br>" +
                    "<a href=\"" + urlBase + "/cancelar-agendamento/" + agendamento.getId() + "\">Cancelar</a>";

            mimeMessageHelper.setText(conteudoEmail, true);

            javaMailSender.send(mensagem);
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
