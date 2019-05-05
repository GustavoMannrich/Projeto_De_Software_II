package com.ggl.gerenciadorestudantil.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ggl.gerenciadorestudantil.entities.Aluno;
import com.ggl.gerenciadorestudantil.security.JwtUserFactory;
import com.ggl.gerenciadorestudantil.services.AlunoService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AlunoService alunoService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Aluno> aluno = alunoService.buscarPorEmail(username);

		if (aluno.isPresent()) {
			return JwtUserFactory.create(aluno.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}