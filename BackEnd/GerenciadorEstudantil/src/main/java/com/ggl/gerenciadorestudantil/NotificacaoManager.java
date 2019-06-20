package com.ggl.gerenciadorestudantil;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ggl.gerenciadorestudantil.entities.Evento;
import com.ggl.gerenciadorestudantil.services.EnvioEmail;
import com.ggl.gerenciadorestudantil.services.EventoService;

@Component
public class NotificacaoManager implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(NotificacaoManager.class);
	private Timer timer;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private EnvioEmail emailService;
	
	@Override
    public void run(String...args) throws Exception {
		timer = new Timer();
        timer.schedule(new TimerTask() {
			
        	@Override
            public void run() {
        		verificaEventos();
            }
        }, 5000, 60000 * 5); // repete a cada 5 minutos
    }
	
	public void verificaEventos() {
		List<Evento> eventos = eventoService.buscarEventosParaNotificar();		
		log.info("eventos encontrados: {}:", eventos.size());
		
		long tempoRestante = 0;
		long diasRestantes = 0;
		
		for (Evento e : eventos) {
			if ((e.getEnviouNotificacao() == null || !e.getEnviouNotificacao()) && e.getDisciplina().getCurso().getAluno().getReceber_notificacoes()) {
				tempoRestante = e.getData().getTime() - new Date().getTime();
				diasRestantes = TimeUnit.DAYS.convert(tempoRestante, TimeUnit.MILLISECONDS);
				
				if (diasRestantes <= 1 && diasRestantes > -1) {
					emailService.enviar(e.getDisciplina().getCurso().getAluno().getEmail(), "GGL Notificação - " + e.getTitulo(), "Email enviado automaticamente pelo sistema GGL. Se você não se cadastrou para receber este email por favor ignore.\n\n Notificação: " + e.getDescricao());
					e.setEnviouNotificacao(true);
					eventoService.persistir(e);
					System.out.println ("Enviou email para: " + e.toString());
				}	    
			}
		}
	}
}
