<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
	<head>
		<jsp:include page="head.jsp"></jsp:include>
	</head>
	<body ng-controller="LoginCtrl" onload="jQuery('#username').focus();" ng-cloak>		
		<div data-role="page" id="pageone"  class="jqm-demos jqm-home">
			<div data-role="header" class="jqm-header">
				<h2>
					<img src="assets/images/grafstock-logo.jpg" alt="Grafstock Festas">
				</h2>
				<a href="#" data-ajax="false" ng-click="mudarEsqueciMinhaSenha();" ng-show="esqueciMinhaSenha" class="ui-btn ui-btn-icon-notext ui-corner-all ui-icon-back ui-nodisc-icon ui-alt-icon ui-btn-left">Voltar</a>
			</div>
			<div role="main" class="ui-content">
				<div class="alert alert-error" ng-show="retorno.codigo > 0">
					<strong>Falha!</strong> {{retorno.mensagem}}
				</div>
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
					<strong>Sucesso!</strong> {{retorno.mensagem}}
				</div>			
			  	<form ng-submit="entrar()" ng-show="!esqueciMinhaSenha">
					<input type="text" name="usuario" id="username" placeholder="Usuário" ng-model="login.usuario">
					<input type="password" name="senha" id="senha" placeholder="Senha" ng-model="login.senha">
					<button href="#" type="submit" class="ui-shadow ui-btn ui-corner-all">Entrar</button>
					<a href="#" data-ajax="false" ng-click="mudarEsqueciMinhaSenha();" class="ui-shadow ui-btn ui-corner-all">Esqueci minha senha</a>
				</form>
				<form ng-submit="enviarSenha()" ng-show="esqueciMinhaSenha">
					<input type="text" name="email" id="email" placeholder="Email" ng-model="email">
					<button href="#" type="submit" data-ajax="false" class="ui-shadow ui-btn ui-corner-all">Enviar senha por email</button>
				</form>
			</div>
		</div> 
	</body>
	<jsp:include page="scripts.jsp"></jsp:include>
	<script src="assets/js/LoginCtrl.js?v20180403"></script>
</html>