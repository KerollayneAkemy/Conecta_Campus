<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description"
	content="Conheça o Conecta Campus, projeto acadêmico criado por Kerollayne e Renato para aproximar estudantes e comunidade acadêmica.">
<title>Conecta Campus — Conheça o projeto</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/onboarding.css?v=1">
</head>
<body>
	<a class="skip-link" href="#conteudo">Ir para o conteúdo</a>
	<header class="onboarding-header">
		<a class="onboarding-brand" href="#inicio"
			aria-label="Conecta Campus, início"> <span
			class="onboarding-brand-mark"><i
				class="bi bi-mortarboard-fill" aria-hidden="true"></i></span> <span><strong>Conecta
					Campus</strong><small>Comunidade acadêmica</small></span>
		</a>
		<nav class="onboarding-nav" aria-label="Navegação principal">
			<a href="#projeto">O projeto</a> <a href="#criadores">Quem somos</a>
			<a class="nav-login" href="${pageContext.request.contextPath}/login">Entrar</a>
		</nav>
	</header>

	<main id="conteudo">
		<section class="onboarding-hero" id="inicio">
			<div class="hero-copy">
				<p class="hero-kicker">
					<i class="bi bi-stars" aria-hidden="true"></i> Tecnologia que
					aproxima pessoas
				</p>
				<h1>
					Uma comunidade acadêmica mais <span>conectada</span>, participativa
					e próxima.
				</h1>
				<p class="hero-description">O Conecta Campus nasceu para reunir
					comunicados, ideias, pesquisas e conversas importantes em um
					ambiente simples e acessível para toda a comunidade acadêmica.</p>
				<div class="hero-actions">
					<a class="button button-primary"
						href="${pageContext.request.contextPath}/cadastro">Criar minha
						conta <i class="bi bi-arrow-right" aria-hidden="true"></i>
					</a> <a class="button button-secondary"
						href="${pageContext.request.contextPath}/login"><i
						class="bi bi-box-arrow-in-right" aria-hidden="true"></i> Já tenho
						uma conta</a>
				</div>
				<p class="hero-note">
					<i class="bi bi-shield-check" aria-hidden="true"></i> Projeto
					acadêmico pensado com privacidade, inclusão e colaboração.
				</p>
			</div>

			<div class="hero-visual" aria-label="Recursos do Conecta Campus">
				<div class="visual-glow"></div>
				<div class="platform-card">
					<div class="platform-top">
						<span><i class="bi bi-mortarboard-fill"></i></span>
						<div>
							<strong>Conecta Campus</strong><small>Seu campus em um só
								lugar</small>
						</div>
					</div>
					<div class="platform-welcome">
						<small>BEM-VINDO À COMUNIDADE</small><strong>Informação
							que chega.<br>Participação que transforma.
						</strong>
					</div>
					<div class="platform-features">
						<span><i class="bi bi-megaphone"></i><small>Comunicados</small></span>
						<span><i class="bi bi-chat-square-text"></i><small>Fórum</small></span>
						<span><i class="bi bi-bar-chart"></i><small>Pesquisas</small></span>
					</div>
				</div>
				<div class="floating-card floating-card-top">
					<i class="bi bi-bell-fill"></i><span><strong>Novo
							comunicado</strong><small>Fique por dentro das novidades</small></span>
				</div>
				<div class="floating-card floating-card-bottom">
					<i class="bi bi-people-fill"></i><span><strong>Comunidade
							ativa</strong><small>Todos podem participar</small></span>
				</div>
			</div>
		</section>

		<section class="project-section" id="projeto">
			<div class="section-heading">
				<p class="section-kicker">Por que criamos</p>
				<h2>Uma ideia construída para resolver um problema real</h2>
				<p>Informações acadêmicas importantes costumam ficar espalhadas
					entre grupos, mensagens e canais diferentes. O Conecta Campus
					organiza essa comunicação e abre espaço para que os alunos também
					sejam ouvidos.</p>
			</div>
			<div class="project-grid">
				<article>
					<span class="feature-number">01</span><i class="bi bi-megaphone"></i>
					<h3>Informar</h3>
					<p>Centralizar comunicados e novidades para que informações
						importantes cheguem às pessoas certas.</p>
				</article>
				<article>
					<span class="feature-number">02</span><i class="bi bi-people"></i>
					<h3>Aproximar</h3>
					<p>Conectar alunos e equipe institucional em um espaço
						organizado, respeitoso e acessível.</p>
				</article>
				<article>
					<span class="feature-number">03</span><i class="bi bi-chat-heart"></i>
					<h3>Escutar</h3>
					<p>Criar oportunidades de participação por meio de fóruns,
						feedbacks, enquetes e pesquisas.</p>
				</article>
			</div>
		</section>

		<section class="story-section" id="criadores">
			<div class="story-card">
				<div class="story-copy">
					<p class="section-kicker">Quem está por trás</p>
					<h2>Olá! Somos Kerollayne e Renato.</h2>
					<p>
						Desenvolvemos o Conecta Campus como projeto do nosso curso de <strong>Formação
							em Tecnologia</strong>. Mais do que cumprir uma atividade, queríamos criar
						uma solução útil, com propósito e próxima da realidade acadêmica.
					</p>
					<p>Durante o desenvolvimento, reunimos conhecimentos de
						programação, banco de dados, experiência do usuário, segurança e
						trabalho em equipe para transformar uma ideia em uma plataforma
						funcional.</p>
					<div class="story-signature">
						<span></span>
						<p>Feito com dedicação, aprendizado e vontade de transformar.</p>
					</div>
				</div>
				<div class="creator-list" aria-label="Criadores do projeto">
					<div class="creator-card">
						<span class="creator-avatar">K</span>
						<div>
							<strong>Kerollayne</strong><small>Criadora e
								desenvolvedora</small>
						</div>
						<i class="bi bi-code-slash" aria-hidden="true"></i>
					</div>
					<div class="creator-card">
						<span class="creator-avatar creator-avatar-alt">R</span>
						<div>
							<strong>Renato</strong><small>Criador e desenvolvedor</small>
						</div>
						<i class="bi bi-lightbulb" aria-hidden="true"></i>
					</div>
				</div>
			</div>
		</section>

		<section class="values-section" aria-labelledby="valoresTitulo">
			<div class="section-heading compact">
				<p class="section-kicker">O que orienta o projeto</p>
				<h2 id="valoresTitulo">Tecnologia com propósito</h2>
			</div>
			<div class="values-list">
				<div>
					<i class="bi bi-universal-access-circle"></i><strong>Acessibilidade</strong><span>Uma
						experiência clara em computadores, tablets e celulares.</span>
				</div>
				<div>
					<i class="bi bi-shield-lock"></i><strong>Privacidade</strong><span>Respeito
						aos dados e escolhas de cada pessoa.</span>
				</div>
				<div>
					<i class="bi bi-diagram-3"></i><strong>Colaboração</strong><span>Uma
						comunidade se fortalece quando todos podem participar.</span>
				</div>
				<div>
					<i class="bi bi-arrow-repeat"></i><strong>Evolução</strong><span>Um
						projeto aberto a melhorias e novos aprendizados.</span>
				</div>
			</div>
		</section>

		<section class="onboarding-cta">
			<div>
				<p class="section-kicker">Faça parte</p>
				<h2>Pronto para conhecer o Conecta Campus?</h2>
				<p>Crie sua conta e participe da comunidade.</p>
			</div>
			<div class="cta-actions">
				<a class="button button-light"
					href="${pageContext.request.contextPath}/cadastro">Começar
					agora</a><a class="button button-ghost"
					href="${pageContext.request.contextPath}/login">Entrar</a>
			</div>
		</section>
	</main>

	<footer class="onboarding-footer">
		<a class="onboarding-brand" href="#inicio"><span
			class="onboarding-brand-mark"><i
				class="bi bi-mortarboard-fill"></i></span><span><strong>Conecta
					Campus</strong><small>Projeto acadêmico</small></span></a>
		<p>Desenvolvido por Kerollayne e Renato para o curso de Formação
			em Tecnologia.</p>
		<span>© 2026</span>
	</footer>
</body>
</html>
