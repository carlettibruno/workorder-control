<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administra��o - Gerar OS</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-controller="GerarOrdemServicoCtrl" ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-print"></i>
              <h3> Gerar OS</h3>
            </div>
            <!-- /widget-header -->
            <div class="widget-content">
            <form ng-submit="salvar()">
			<div class="alert alert-success" ng-show="retorno.codigo == 0 && !salvando && !carregandoFotos && !carregandoEndereco">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <strong>Sucesso!</strong> Servi�o gerado. <a href="servicos.jsp">Clique aqui</a> para listar os servi�os.
			</div>
			<div class="alert alert-error" ng-show="retorno.codigo > 0">
			  <button type="button" class="close" data-dismiss="alert">&times;</button>
			  <strong>Falha!</strong> {{retorno.mensagem}}
			</div>
			<div class="container">
				<div class="row">
					<div class="span4">
						<div class="control-group">											
							<label class="control-label" for="nroservico">N�mero da ordem de servi�o</label>
							<div class="controls">
								<input type="text" class="span3" id="nroservico" ng-required="true" ng-model="ordemServico.numero">
								<p class="help-block">Entrar com o n�mero da ordem de servi�o gerada.</p>
							</div> <!-- /controls -->				
						</div> <!-- /control-group -->					
						<div class="control-group">											
							<label class="control-label" for="cliente">Cliente</label>
							<div class="controls">
								<input type="text" class="span3" readonly="readonly" id="cliente" ng-required="true" ng-model="ordemServico.cliente.nome"> <button href="#modalClientes" data-toggle="modal" class="btn" ng-click="abrirClientes()" type="button" title="Ver clientes"><i class="icon-list"></i></button>
							</div> <!-- /controls -->				
						</div> <!-- /control-group -->					
						
						<div class="control-group">											
							<label class="control-label" for="email">Email</label>
							<div class="controls">
								<input type="text" class="span3" id="email" ng-model="ordemServico.email">
							</div> <!-- /controls -->				
						</div> <!-- /control-group -->	
						<div class="control-group">											
							<label class="control-label" for="descricao">Descri��o do servi�o</label>
							<div class="controls">
								<input type="text" class="span3" id="descricao" ng-required="true" ng-model="ordemServico.descricao">
								<p class="help-block">Breve descri��o do servi�o que ser� visualizada pelo cliente.</p>
							</div> <!-- /controls -->				
						</div> <!-- /control-group -->
						<div class="control-group">											
							<label class="control-label" for="descricao">Previs�o de entrega</label>
							<div class="controls">
								<input type="date" ng-model="ordemServico.previsaoEntrega" placeholder="dd/MM/yyyy" min="2013-01-01" max="2020-12-31" />
								<p class="help-block">Data prevista de entrega para o cliente.</p>
							</div> <!-- /controls -->				
						</div> <!-- /control-group -->
					</div>

					<div class="span4">
						<div class="control-group">
							<button type="button" class="btn btn-invert" ng-show="usarPlanilhaEndereco" ng-click="setUsarPlanilhaEndereco(false)"><i class="icon-home"></i> Usar endere�o cliente</button>
							<button type="button" class="btn btn-invert" ng-show="!usarPlanilhaEndereco" ng-click="setUsarPlanilhaEndereco(true)"><i class="icon-table"></i> Usar lista de endere�os</button>
						</div>
						<div class="control-group" ng-show="usarPlanilhaEndereco">											
							<label class="control-label" for="username">Lista de endere�os</label>
							<div class="controls">
								<input type="file" class="span3" ng-file-select="onPlanilhaSelect($files)">
								<p class="help-block">Inserir lista dos endere�os de destino do servi�o.</p>
							</div> <!-- /controls -->				
						</div> <!-- /control-group -->						
						<div class="control-group" ng-show="!usarPlanilhaEndereco">
							<div class="control-group">
								<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.titulo" placeholder="T�tulo">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->													
							<div class="control-group">
								<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.endereco" placeholder="Endere�o">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							<div class="control-group">
								<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.cep" placeholder="CEP">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							<div class="control-group">
							<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.complemento" placeholder="Complemento">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							<div class="control-group">
								<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.bairro" placeholder="Bairro">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							<div class="control-group">
								<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.estado" placeholder="Estado">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->							
							<div class="control-group">
								<div class="controls">
									<input type="text" class="span3" ng-model="ordemServico.cliente.endereco.cidade" placeholder="Cidade">
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->	
						</div>					
					</div>

					<div class="span4">
						<div class="control-group">											
							<label class="control-label" for="username">Fotos</label>
							<div class="controls">
								<input type="file" class="span3" ng-file-select="onFileSelect($files)" multiple>
							</div> <!-- /controls -->
							<table class="span3 table table-striped table-bordered">
								<tbody>
								<tr ng-repeat="foto in fotosAdicionada">
									<td>{{foto.name}}</td>
									<td><a href="#" ng-click="apagarFoto(foto)" class="shortcut" style="text-decoration: none;" title="Inativar"><i class="shortcut-icon icon-remove-sign"></i></a></td>
								</tr>
								</tbody>
							</table>
						</div> <!-- /control-group -->					
					</div>				
				</div>
			</div>
			<div class="form-actions">
				<img src="img/ajax-loader.gif" ng-show="salvando || carregandoFotos || carregandoEndereco">
				<button type="submit" class="btn btn-primary" ng-disabled="salvando || carregandoFotos || carregandoEndereco">Gerar OS</button>
				<a class="btn" href="servicos.jsp"  ng-disabled="salvando || carregandoFotos || carregandoEndereco">Cancelar</a>
				<span ng-show="salvando || carregandoFotos || carregandoEndereco">Aguarde... </span>
				<span ng-show="salvando">Salvando OS.</span>
				<span ng-show="!salvando && carregandoEndereco">Carregando endere�os.</span>
				<span ng-show="!salvando && !carregandoEndereco && carregandoFotos">Carregando fotos.</span>
			</div>
			</form>
            </div>
            
			<div id="modalClientes" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button>
					<h3 id="myModalLabel">Clientes</h3>
				</div>
				
				<div class="modal-body">
                	<form ng-submit="pesquisarClientes()" class="form-horizontal">
						<div class="control-group">
			                <div class="controls">
			                	<div class="input-append">
									<input class="span2 m-wrap" type="text" ng-model="campoPesquisaCliente" placeholder="Nome cliente...">
			                     	<button class="btn" type="submit">Pesquisar</button>
			                    </div>
			                </div>
		                </div>               		
                    </form>
					
					<table class="table table-striped table-bordered">
		                <thead>
		                  <tr>
		                  	<th></th>
		                    <th>Clientes</th>
		                  </tr>
		                </thead>
		                <tbody>
		                	<tr ng-repeat="cliente in clientes" ng-click="selecionarCliente(cliente)">
		                		<td>
		                			<i class="icon-check-empty" ng-show="!cliente.marcado"></i>
		                			<i class="icon-check" ng-show="cliente.marcado"></i>
		                		</td>
		                		<td>{{cliente.nome}}</td>
		                	</tr>
		                </tbody>
		        	</table>
					<div class="row">
						<div class="span5">
							<button class=" span5 btn" ng-click="atualizarClientes(campoPesquisaCliente);" ng-show="carregarMaisCliente"><img src="img/ajax-loader.gif" ng-show="carregandoCliente"> Carregar mais</button>
						</div>
					</div>		        	
				</div>									
				<div class="modal-footer">
					<div style="float: left;">Cliente selecionado: <b>{{ordemServico.cliente.nome}}</b></div>
					<button class="btn btn-info" data-dismiss="modal" aria-hidden="true">Fechar</button>
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
<script src="js/GerarOrdemServicoCtrl.js?v20150203"></script> 
</body>
</html>