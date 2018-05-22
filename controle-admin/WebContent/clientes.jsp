<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Clientes</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="ClienteCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-group"></i>
              <h3> Clientes</h3>
            </div>
            <!-- /widget-header -->
			<div id="divCliente" class="widget-content" ng-show="cliente != null">
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Sucesso!</strong> {{retorno.mensagem}} <a href="clientes.jsp">Clique aqui</a> para listar os clientes.
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
								<label class="control-label">Cpf/Cnpj</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.cpfCnpj">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Nome</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.nome">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Email</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.email">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Exigir aprovação das fotos?</label>
								<div class="controls">
									<input type="checkbox" class="span3" ng-model="cliente.aprovacaoFoto">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">Endereço</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.endereco">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Número</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.numero">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">CEP</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.cep">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Complemento</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.complemento">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Bairro</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.bairro">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Estado</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.estado">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group">
								<label class="control-label">Cidade</label>
								<div class="controls">
									<input type="text" class="span3" ng-model="cliente.endereco.cidade">
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
			<div class="widget-content" ng-show="exibirImportar">
				<div class="container">
					<div class="row">
						<div class="span6">
							<div class="control-group">
								<label class="control-label" for="username">Escolher arquivo de importação</label>
								<div class="controls">
									<input type="file" ng-file-select="onPlanilhaSelect($files)" />
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
						</div>
						<div class="span6" ng-show="envioFinalizado != null">
							Envio concluído. Total: <b>{{envioFinalizado.total}}</b>, carregado: <b>{{envioFinalizado.qtdeCarregada}}</b>.
						</div>
						<div class="span6" ng-show="importando">
							<div class="progress progress-striped active span5">
								<div class="bar" style="width: {{(envio.qtdeCarregada / envio.total) * 100}}%;"></div>
							</div>
							<br/>
							<br/>
							<center>
							<p>{{envio.qtdeCarregada}} de {{envio.total}} clientes</p>
							</center>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<button type="submit" ng-click="importarClientes()" class="btn btn-primary" id="btnIniciarImportacao" ng-disabled="importando">Iniciar importação</button>
					<button class="btn btnCancelar" ng-disabled="importando" ng-click="exibirImportar = false">Cancelar</button>
				</div>
			</div>
            <div class="widget-content" id="divClientes" ng-show="cliente == null && !exibirImportar" >

			<div class="alert alert-info" ng-show="enviandoSenha">
			  <img src="img/ajax-loader.gif"> Aguarde...
			</div>



            <div class="row">
            	<div class="span4">
					<a class="btn" href="#" ng-click="novo()"><i class="icon-plus"></i> Novo cliente</a>
					<a class="btn" href="#" ng-click="importar()"><i class="icon-download-alt"></i> Importar clientes</a>
				</div>

				<div class="span5">
					<form class="form-horizontal" ng-submit="pesquisarAcao()">
						<fieldset>
							<div class="input-append">
								<input class="span2 m-wrap" ng-model="campoPesquisa" type="text" placeholder="nome...">
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
                    <th>Cpf/Cnpj</th>
                    <th>Email</th>
					<th>&Uacute;ltimo acesso</th>
					<th class="td-actions"> </th>
                  </tr>
                </thead>
                <tbody>
				  <tr ng-repeat="cliente in clientes">
					<td>{{cliente.nome}}</td>
					<td>{{cliente.cpfCnpj}}</td>
					<td>{{cliente.email}}</td>
					<td>{{cliente.dataUltimoAcesso | date: 'dd/MM/yyyy HH:mm:ss'}}</td>
					<td>
						<a href="#" ng-click="alterar(cliente)" class="shortcut" style="text-decoration: none;" title="Editar"><i class="shortcut-icon icon-edit"></i></a>
						&nbsp;
						<a href="#" ng-click="apagar(cliente)" class="shortcut" style="text-decoration: none;" title="Inativar"><i class="shortcut-icon icon-remove-sign"></i></a>
						&nbsp;
						<a href="#" ng-click="gerarNovaSenha(cliente)" class="shortcut" style="text-decoration: none;" title="Gerar nova senha"><i class="shortcut-icon icon-key"></i></a>

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
<script src="js/ClienteCtrl.js?v20140819"></script>
</body>
</html>
