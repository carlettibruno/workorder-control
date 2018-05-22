<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Alterar senha</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="AlterarSenhaCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-key"></i>
              <h3> Alterar senha</h3>
            </div>
            <!-- /widget-header -->
            <div class="widget-content">
			<div class="alert alert-success" ng-show="status.codigo == 0">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <strong>Sucesso!</strong> Senha alterada.
			</div>
			<div class="alert alert-error"  ng-show="status.codigo > 0">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <strong>Falha!</strong> {{status.mensagem}}
			</div>
			<form ng-submit="alterarSenha()">
			<div class="container">
				<div class="row">
					<div class="span6">
						<div class="control-group">
							<label class="control-label" for="username">Senha atual</label>
							<div class="controls">
								<input type="password" class="span3" placeholder="Senha atual" ng-model="login.senha" />
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="username">Nova senha</label>
							<div class="controls">
								<input type="password" class="span3" placeholder="Nova senha" ng-model="login.senhaNova" />
							</div> <!-- /controls -->
						</div> <!-- /control-group -->
						<div class="control-group">
							<label class="control-label" for="username">Redigite a nova senha</label>
							<div class="controls">
								<input type="password" class="span3" placeholder="Redigite a nova senha" ng-model="login.senhaNovaConfirmacao" />
							</div> <!-- /controls -->
						</div> <!-- /control-group -->
					</div>
            </div>
            <!-- /widget-content -->
          </div>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">Alterar senha</button> <button class="btn" href="inicio.jsp">Cancelar</button>
			</div>
			</form>
          <!-- /widget -->
        </div>
        <!-- /span6 -->
      </div>
      <!-- /row -->
    </div>
    <!-- /container -->
  </div>
  <!-- /main-inner -->
</div>
<!-- /main -->
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<jsp:include page="scripts.jsp"></jsp:include>
<script src="js/AlterarSenhaCtrl.js?v20140819"></script>
</body>
</html>
