<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Etapas</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="EtapaCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-user"></i>
              <h3> Etapas</h3>
            </div>
            <!-- /widget-header -->
			<div class="widget-content" ng-show="etapa != null">
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Sucesso!</strong> {{retorno.mensagem}}. <a href="etapas.jsp">Clique aqui</a> para listar as etapas.
				</div>
				<div class="alert alert-error" ng-show="retorno.codigo > 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Falha!</strong> {{retorno.mensagem}}
				</div>
				<form ng-submit="salvar()">
				<div class="container">
					<div class="row">
						<div class="span6">
							<div class="control-group">
								<label class="control-label" for="etapa">Etapa</label>
								<div class="controls">
									<input type="text" class="span3" id="etapa" ng-model="etapa.nome">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label" for="automatica">Autom&aacute;tica</label>
								<div class="controls">
									<input type="checkbox" id="automatica" ng-model="etapa.automatica">
									<p class="help-block">Etapa ser&aacute; apenas bipada e n&atilde;o ter&aacute; tela no terminal.</p>
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
						</div>
						<div class="span5">
							<div class="control-group">
								<label class="control-label" for="etapaEntrega">Etapa entrega</label>
								<div class="controls">
									<input type="checkbox" id="etapaEntrega" ng-model="etapa.etapaEntrega">
									<p class="help-block">Indica que será a etapa usada para entrega de um serviço.</p>
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label" for="etapaInicial">Etapa inicial</label>
								<div class="controls">
									<input type="checkbox" id="etapaInicial" ng-model="etapa.etapaInicial">
									<p class="help-block">Indica que será a etapa usada ao criar uma ordem de serviço.</p>
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
						</div>
					</div>
				</div>
				<div class="form-actions">
					<img src="img/ajax-loader.gif" ng-show="salvando"> <button type="submit" class="btn btn-primary" ng-disabled="salvando">Salvar</button><button class="btn" type="button" ng-click="cancelar()"  ng-disabled="salvando">Cancelar</button>
				</div>
				</form>
			</div>
            <div class="widget-content" ng-show="etapa == null">
			<a class="btn" ng-click="novo()"><i class="icon-plus"></i> Nova etapa</a>
			<table class="table table-striped table-bordered">
                <thead>
                  <tr>
                    <th>Etapa</th>
					<th>Etapa entrega</th>
					<th>Etapa inicial</th>
					<th>Última atualização</th>
					<th>IP (IP Externo)</th>
					<th>Disponível</th>
					<th class="td-actions"> </th>
                  </tr>
                </thead>
                <tbody>
				  <tr ng-repeat="etapa in etapas">
					<td>{{etapa.nome}}</td>
					<td><span ng-show="etapa.etapaEntrega">Sim</span></td>
					<td><span ng-show="etapa.etapaInicial">Sim</span></td>
					<td>{{etapa.dataUltimaAtualizacao | date: 'dd/MM/yyyy HH:mm:ss'}}</td>
					<td>
						<span ng-show="etapa.ip == etapa.ipExterno && etapa.ipExterno == null">{{etapa.ip}}</span>
						<span ng-show="etapa.ip != etapa.ipExterno && etapa.ipExterno != null">{{etapa.ip}} ({{etapa.ipExterno}})</span>
					</td>
					<td>
						<img src="img/online_status.png" ng-show="etapa.disponivel" />
						<img src="img/offline_status.png" ng-show="!etapa.disponivel" />
					</td>
					<td>
						<a href="#" class="shortcut" ng-click="alterar(etapa)" style="text-decoration: none;" title="Editar"><i class="shortcut-icon icon-edit"></i></a>
						&nbsp;
						<a href="#" class="shortcut" ng-click="apagar(etapa)" style="text-decoration: none;" title="Inativar"><i class="shortcut-icon icon-remove-sign"></i></a>
					</td>
				  </tr>
				</tbody>
			</table>
            </div>
            <!-- /widget-content -->
          </div>
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
<script src="js/EtapaCtrl.js?v20140819"></script>
</body>
</html>
