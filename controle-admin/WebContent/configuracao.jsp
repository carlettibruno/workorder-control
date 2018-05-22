<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Configurações</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="ConfiguracaoCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-cogs"></i>
              <h3> Configurações</h3>
            </div>
            <!-- /widget-header -->
            <div class="widget-content">
			<div class="alert alert-success" ng-show="retorno.codigo == 0">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <strong>Sucesso!</strong> Configurações alteradas.
			</div>
			<div class="alert alert-error" ng-show="retorno.codigo > 0">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <strong>Falha!</strong> {{retorno.mensagem}}
			</div>
            <form ng-submit="salvar()">
			<div class="container">
				<div class="row">
					<div class="span6" ng-repeat="config in configuracoes">
						<div class="control-group">
							<label class="control-label" >{{config.chave}}</label>
							<div class="controls">
								<input type="text" class="span3" ng-model="config.valor">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->
					</div>
            </div>
            <!-- /widget-content -->
          </div>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">Alterar configurações</button>
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
<script src="js/ConfiguracaoCtrl.js?v20140819"></script>
</body>
</html>
