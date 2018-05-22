<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Funcionários</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="FuncionarioCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-user"></i>
              <h3> Funcionários</h3>
            </div>
            <!-- /widget-header -->
			<div id="divFuncionario" ng-show="funcionario != null" class="widget-content">
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Sucesso!</strong> {{retorno.mensagem}} <a href="funcionarios.jsp">Clique aqui</a> para listar os funcionários.
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
								<label class="control-label" for="username">Nome</label>
								<div class="controls">
									<input type="text" class="span3" id="nome" ng-required="true" value="Alfredo" ng-model="funcionario.nome">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label" for="username">Email</label>
								<div class="controls">
									<input type="text" class="span3" id="email" ng-required="true" ng-model="funcionario.email">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label" for="username">Grupos</label>
								<div class="controls">
									<select multiple="multiple" ng-model="funcionario.grupos" ng-options="grupo.nome for grupo in grupos">
									</select>
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
						</div>
					</div>
				</div>
				<div class="form-actions">
					<img src="img/ajax-loader.gif" ng-show="carregando">
					<button type="submit" class="btn btn-primary" ng-disabled="carregando">Salvar</button>
					<button class="btn" type="button" ng-click="cancelar()" ng-disabled="carregando">Cancelar</button>
				</div>
				</form>
			</div>
            <div id="divFuncionarios" ng-show="funcionario == null" class="widget-content">

            <div class="row">
            	<div class="span2">
					<a class="btn" href="#" ng-click="novo()"><i class="icon-plus"></i> Novo funcion&aacute;rio</a>
				</div>

				<div class="span6">
					<form class="form-horizontal" ng-submit="pesquisarAcao()">
						<fieldset>
							<div class="input-append">
								<input class="span2 m-wrap" ng-model="campoPesquisa" type="text" placeholder="nome, email, login...">
								<button class="btn" type="submit">Pesquisar</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>

			<table class="table table-striped table-bordered">
                <thead>
                  <tr>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Data modificação</th>
					<th>Último acesso</th>
					<th>Grupos</th>
					<th class="td-actions"> </th>
                  </tr>
                </thead>
                <tbody>
				  <tr ng-repeat="f in funcionarios">
					<td>{{f.nome}}</td>
					<td>{{f.email}}</td>
					<td>{{f.dataModificacao | date: 'dd/MM/yyyy HH:mm:ss'}}</td>
					<td>{{f.dataUltimoAcesso | date: 'dd/MM/yyyy HH:mm:ss'}}</td>
					<td><span ng-repeat="g in f.grupos">{{g.nome}}, </span></td>
					<td>
						<a href="#" ng-click="alterar(f)" class="shortcut" style="text-decoration: none;" title="Editar"><i class="shortcut-icon icon-edit"></i></a>
						&nbsp;
						<a href="#" ng-click="apagar(f)" class="shortcut" style="text-decoration: none;" title="Inativar"><i class="shortcut-icon icon-remove-sign"></i></a>
					</td>
				  </tr>
				</tbody>
			</table>
			<div class="row">
				<div class="span12">
					<button class=" span11 btn" ng-click="pesquisar();" ng-show="carregarMais"><img src="img/ajax-loader.gif" ng-show="carregando"> Carregar mais</button>
				</div>
			</div>
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
<script src="js/FuncionarioCtrl.js?v20150203"></script>
</body>
</html>
