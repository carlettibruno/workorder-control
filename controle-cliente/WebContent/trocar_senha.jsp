<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
	<head>
		<jsp:include page="head.jsp"></jsp:include>
	</head>
	<body ng-controller="AlterarSenhaCtrl" ng-cloak>
		<div data-role="page" id="pageone" class="jqm-demos jqm-home">
			<div data-role="header" class="jqm-header">
				<h2>
					<img src="assets/images/grafstock-logo.jpg" alt="Grafstock Festas">
				</h2>
				<a href="lista_os.jsp" data-ajax="false" class="ui-btn ui-btn-icon-notext ui-corner-all ui-icon-home ui-nodisc-icon ui-alt-icon ui-btn-left">Voltar</a>
			</div>

			<div role="main" class="ui-content">
				<div class="alert alert-error" ng-show="retorno.codigo > 0">
					<strong>Falha!</strong> {{retorno.mensagem}}
				</div>
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
					<strong>Sucesso!</strong> {{retorno.mensagem}}
				</div>
				<form ng-submit="alterarSenha()">
					<input type="password" name="senha" id="senha" placeholder="Senha atual" ng-model="login.senha" />
					<input type="password" name="senha" id="senha" placeholder="Senha" ng-model="login.senhaNova" />
					<input type="password" name="confirmarSenha" id="confirmarSenha" placeholder="Confirmar Senha" ng-model="login.senhaNovaConfirmacao" />
					<button type="submit" class="ui-shadow ui-btn ui-corner-all">Alterar senha</button>
				</form>
			</div>
		</div>
	</body>
	<jsp:include page="scripts.jsp"></jsp:include>
	<script src="assets/js/AlterarSenhaCtrl.js?v20140819"></script>
</html>
