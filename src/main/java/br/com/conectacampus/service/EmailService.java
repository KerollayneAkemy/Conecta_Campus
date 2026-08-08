package br.com.conectacampus.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.net.ssl.SSLSocketFactory;

public class EmailService {

	public boolean enviarRecuperacaoSenha(String destino, String nome, String link) {
		String host = configuracao("SMTP_HOST");
		String usuario = configuracao("SMTP_USER");
		String senha = configuracao("SMTP_PASSWORD");
		String remetente = valorOuPadrao(configuracao("SMTP_FROM"), usuario);
		int porta = porta();

		if (vazio(host) || vazio(usuario) || vazio(senha) || vazio(remetente) || destino.contains("\r") || destino.contains("\n")) {
			System.err.println("SMTP não configurado. Verifique SMTP_HOST, SMTP_USER, SMTP_PASSWORD e SMTP_FROM no Tomcat.");
			return false;
		}

		String seguranca = valorOuPadrao(System.getenv("SMTP_SECURITY"), porta == 465 ? "SSL" : "STARTTLS").toUpperCase();

		try (Socket socketInicial = abrirSocket(host, porta, seguranca)) {
			socketInicial.setSoTimeout(15000);
			BufferedReader entrada = leitor(socketInicial);
			BufferedWriter saida = escritor(socketInicial);
			esperar(entrada, 220);
			comando(saida, entrada, "EHLO conectacampus.local", 250);

			Socket socket = socketInicial;
			if ("STARTTLS".equals(seguranca)) {
				comando(saida, entrada, "STARTTLS", 220);
				socket = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(socketInicial, host, porta, true);
				socket.setSoTimeout(15000);
				entrada = leitor(socket);
				saida = escritor(socket);
				comando(saida, entrada, "EHLO conectacampus.local", 250);
			}

			comando(saida, entrada, "AUTH LOGIN", 334);
			comando(saida, entrada, codificar(usuario), 334);
			comando(saida, entrada, codificar(senha), 235);
			comando(saida, entrada, "MAIL FROM:<" + remetente + ">", 250);
			comando(saida, entrada, "RCPT TO:<" + destino + ">", 250);
			comando(saida, entrada, "DATA", 354);

			String conteudo = "From: Conecta Campus <" + remetente + ">\r\n"
					+ "To: " + destino + "\r\n"
					+ "Subject: Redefinição de senha - Conecta Campus\r\n"
					+ "MIME-Version: 1.0\r\n"
					+ "Content-Type: text/plain; charset=UTF-8\r\n\r\n"
					+ "Olá, " + nome + ".\r\n\r\n"
					+ "Recebemos uma solicitação para redefinir sua senha. Use o link abaixo em até 30 minutos:\r\n"
					+ link + "\r\n\r\n"
					+ "Se você não solicitou esta alteração, ignore este e-mail.\r\n"
					+ ".\r\n";
			saida.write(conteudo);
			saida.flush();
			esperar(entrada, 250);
			comando(saida, entrada, "QUIT", 221);

			return true;

		} catch (IOException e) {
			System.err.println("Não foi possível enviar o e-mail de recuperação: " + e.getMessage());

			return false;
		}
	}

	public boolean enviarComunicado(String destino, String nome, String titulo, String mensagem, String link) {
		String host = configuracao("SMTP_HOST");
		String usuario = configuracao("SMTP_USER");
		String senha = configuracao("SMTP_PASSWORD");
		String remetente = valorOuPadrao(configuracao("SMTP_FROM"), usuario);
		int porta = porta();

		if (vazio(host) || vazio(usuario) || vazio(senha) || vazio(remetente) || vazio(destino)
				|| destino.contains("\r") || destino.contains("\n")) {
			System.err.println("SMTP não configurado para enviar notificações de comunicados.");

			return false;
		}

		String assuntoSeguro = valorOuPadrao(titulo, "Novo comunicado").replace("\r", " ").replace("\n", " ");
		String nomeSeguro = valorOuPadrao(nome, "usuário");
		String mensagemSegura = valorOuPadrao(mensagem, "Acesse o Conecta Campus para conferir o comunicado.")
				.replace("\r\n", "\n").replace("\r", "\n").replace("\n.", "\n..");
		String seguranca = valorOuPadrao(configuracao("SMTP_SECURITY"), porta == 465 ? "SSL" : "STARTTLS").toUpperCase();

		try (Socket socketInicial = abrirSocket(host, porta, seguranca)) {
			socketInicial.setSoTimeout(15000);
			BufferedReader entrada = leitor(socketInicial);
			BufferedWriter saida = escritor(socketInicial);
			esperar(entrada, 220);
			comando(saida, entrada, "EHLO conectacampus.local", 250);

			Socket socket = socketInicial;
			if ("STARTTLS".equals(seguranca)) {
				comando(saida, entrada, "STARTTLS", 220);
				socket = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(socketInicial, host, porta, true);
				socket.setSoTimeout(15000);
				entrada = leitor(socket);
				saida = escritor(socket);
				comando(saida, entrada, "EHLO conectacampus.local", 250);
			}

			comando(saida, entrada, "AUTH LOGIN", 334);
			comando(saida, entrada, codificar(usuario), 334);
			comando(saida, entrada, codificar(senha), 235);
			comando(saida, entrada, "MAIL FROM:<" + remetente + ">", 250);
			comando(saida, entrada, "RCPT TO:<" + destino + ">", 250);
			comando(saida, entrada, "DATA", 354);

			String conteudo = "From: Conecta Campus <" + remetente + ">\r\n"
					+ "To: " + destino + "\r\n"
					+ "Subject: Novo comunicado: " + assuntoSeguro + "\r\n"
					+ "MIME-Version: 1.0\r\n"
					+ "Content-Type: text/plain; charset=UTF-8\r\n\r\n"
					+ "Olá, " + nomeSeguro + ".\r\n\r\n"
					+ "Um novo comunicado foi publicado no Conecta Campus:\r\n\r\n"
					+ assuntoSeguro + "\r\n"
					+ mensagemSegura.replace("\n", "\r\n") + "\r\n\r\n"
					+ "Leia o comunicado completo:\r\n" + link + "\r\n\r\n"
					+ "Você recebeu este e-mail porque ativou as notificações de comunicados. "
					+ "A preferência pode ser alterada no seu perfil.\r\n"
					+ ".\r\n";

			saida.write(conteudo);
			saida.flush();
			esperar(entrada, 250);
			comando(saida, entrada, "QUIT", 221);

			return true;

		} catch (IOException e) {
			System.err.println("Não foi possível enviar a notificação de comunicado para " + destino + ": " + e.getMessage());

			return false;
		}
	}

	private Socket abrirSocket(String host, int porta, String seguranca) throws IOException {
		Socket conexao = new Socket();
		conexao.connect(new InetSocketAddress(enderecoIpv4(host), porta), 15000);

		if ("SSL".equals(seguranca)) {

			return ((SSLSocketFactory) SSLSocketFactory.getDefault())
					.createSocket(conexao, host, porta, true);
		}

		return conexao;
	}

	private BufferedReader leitor(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
	}

	private BufferedWriter escritor(Socket socket) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
	}

	private void comando(BufferedWriter saida, BufferedReader entrada, String texto, int esperado) throws IOException {
		saida.write(texto + "\r\n");
		saida.flush();
		esperar(entrada, esperado);
	}

	private void esperar(BufferedReader entrada, int esperado) throws IOException {
		String linha;
		do {

			linha = entrada.readLine();

			if (linha == null || linha.length() < 3) throw new IOException("Resposta SMTP inválida.");
		} 

		while (linha.length() > 3 && linha.charAt(3) == '-');

		if (!linha.startsWith(String.valueOf(esperado))) throw new IOException("Servidor SMTP recusou a operação: " + linha);
	}

	private int porta() {
		try { 

			return Integer.parseInt(valorOuPadrao(configuracao("SMTP_PORT"), "465"));

		} catch (NumberFormatException e) { 
			return 465; 
		}
	}

	private String codificar(String valor) { return Base64.getEncoder().encodeToString(valor.getBytes(StandardCharsets.UTF_8)); }

	private boolean vazio(String valor) { return valor == null || valor.isBlank(); }

	private String valorOuPadrao(String valor, String padrao) { return vazio(valor) ? padrao : valor; }

	private String configuracao(String nome) {
		String valor = System.getenv(nome);
		valor = vazio(valor) ? System.getProperty(nome) : valor;

		return valor == null ? null : valor.trim();
	}

	private InetAddress enderecoIpv4(String host) throws IOException {
		String ipConfigurado = configuracao("SMTP_IP");

		if (!vazio(ipConfigurado)) {

			return InetAddress.getByName(ipConfigurado);
		}

		for (InetAddress endereco : InetAddress.getAllByName(host)) {

			if (endereco instanceof Inet4Address) {

				return endereco;
			}
		}

		throw new IOException("Não foi encontrado um endereço IPv4 para " + host + ".");
	}
}
