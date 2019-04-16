package com.ggl.gerenciadorestudantil.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ggl.gerenciadorestudantil.entities.Aluno;

public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * Converte e gera um JwtUser com base nos dados de um aluno.
	 * 
	 * @param funcionario
	 * @return JwtUser
	 */
	public static JwtUser create(Aluno aluno) {
		return new JwtUser(aluno.getId(), aluno.getNome(), aluno.getSenha(),
				mapToGrantedAuthorities("USER"));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(String perfil) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil));
		return authorities;
	}

}