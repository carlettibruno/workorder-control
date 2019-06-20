<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Serviços</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="ServicoCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-truck"></i>
              <h3> Serviços</h3>
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
							<br/>
							<center>
							<p><img src="img/ajax-loader.gif"> aguarde carregando informações...</p>
							</center>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<button type="submit" ng-click="importarServicos()" class="btn btn-primary" id="btnIniciarImportacao" ng-disabled="importando">Iniciar importação</button>
					<button class="btn btnCancelar" ng-disabled="importando" ng-click="exibirImportar = false">Cancelar</button>
				</div>
			</div>

            <!-- /widget-header -->
			<div id="divVisualizarServico" class="widget-content" ng-show="ordemServico != null && !exibirImportar">
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Sucesso!</strong> {{retorno.mensagem}} <a href="servicos.jsp">Clique aqui</a> para listar os serviços.
				</div>
				<div class="alert alert-error" ng-show="retorno.codigo > 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Falha!</strong> {{retorno.mensagem}}
				</div>
				<div class="container">
					<div class="row">
						<div class="span5">
							<p>Cliente <b>{{ordemServico.cliente.nome}}</b></p>
							<p>Email <b>{{ordemServico.email}}</b></p>
							<p>N&uacute;mero da ordem de servi&ccedil;o <b>{{ordemServico.numero}}</b></p>
							<p >OS provisória <b><span ng-show="ordemServico.temporary">Sim</span><span ng-show="!ordemServico.temporary">Não</span></b></p>
							<p ng-show="visualizar">Descri&ccedil;&atilde;o do servi&ccedil;o <b>{{ordemServico.descricao}}</b></p>
							<p ng-show="visualizar">Previsão de entrega <b>{{ordemServico.previsaoEntrega | date : 'dd/MM/yyyy'}}</b></p>

							<div class="control-group" ng-show="!visualizar">
								<label class="control-label" for="descricao">Descri&ccedil;&atilde;o do servi&ccedil;o</label>
								<div class="controls">
									<input type="text" class="span3" id="descricao" ng-model="ordemServico.descricao">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group" ng-show="!visualizar">
								<label class="control-label" for="previsao">Previsão de entrega</label>
								<div class="controls">
                  				<input mask="39/19/2099" ng-model="ordemServico.previsaoEntregaTxt" id="previsao">
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							<div class="control-group" ng-show="!visualizar">
								<label class="control-label" for="descricao">Alterar status</label>
								<div class="controls">
									<select ng-model="etapa" ng-options="etapa.nome for etapa in etapas"></select>
								</div> <!-- /controls -->
							</div> <!-- /control-group -->

							<br>
							<label class="control-label"><i class="icon-list"></i> Hist&oacute;rico <b>({{ordemServico.diferencaDatas}})	</b></label>

							<div class="row" ng-repeat="historico in historicos">
								<div class="span6">
									<h4>{{historico.etapa.nome}} <span ng-show="historico.status != 'Em andamento'" class="osFechada">({{historico.status}})</span><span ng-show="historico.status == 'Em andamento'" class="osAberta">({{historico.status}})</span></h4>
								</div>
								<div class="span1">In&iacute;cio</div>
								<div class="span4"><b>{{historico.dataInicio | date: 'dd/MM/yyyy HH:mm:ss'}}</b></div>
								<div class="span1" ng-show="historico.status == 'Em andamento'">Execução</div>
								<div class="span1" ng-show="historico.status != 'Em andamento'">Término</div>
								<div class="span4">
									<i ng-show="historico.status == 'Em andamento'"><b>Há {{historico.diferencaDatas}}</b></i>
									<span ng-show="historico.status != 'Em andamento'"><b>{{historico.dataFim | date: 'dd/MM/yyyy HH:mm:ss'}}</b></span>
								</div>
							</div>
						</div>

						<div class="span7">
							<div class="span7">
								<label class="control-label"><i class="icon-picture"></i> Fotos</label>
								<div class="controls span6" ng-show="fotos.length == 0">
									Não disponível
								</div>
								<div class="span1 thumbnail" ng-repeat="foto in fotos" ng-class="{thumbaprovado: foto.aprovada, thumbreprovado: !foto.aprovada}">
									<img class="span1" ng-src="{{foto.caminhoCompletoThumb}}" href="#modalImagem" role="button" data-toggle="modal" ng-click="setVerFoto(true, foto)" title="{{foto.nome}}" />
									<i ng-show="!visualizar" ng-click="apagarFoto(foto)" class="icon-trash" style="float:left; cursor: pointer;" title="Excluir imagem"></i>
									<div ng-show="!visualizar" style="width: 60%; float:left; font-size: 0.6em; padding: 1px; text-overflow: ellipsis; overflow: hidden;" title="{{foto.nome}}">{{foto.nome}}</div>
									<i ng-show="!visualizar" href="#modalImagem" role="button" data-toggle="modal" class="icon-search" style="float: right;  cursor: pointer;" title="Ver imagem" ng-click="setVerFoto(true, foto)"></i>
								</div>
							</div>
							<div class="control-group span6" ng-show="!visualizar">
								<label class="control-label" for="username">Fotos</label>
								<div class="controls">
									<input type="file" class="span3" ng-file-select="onFileSelect($files)" multiple><br>
								</div> <!-- /controls -->
								<table class="span3 table table-striped table-bordered">
									<tbody>
									<tr ng-repeat="foto in fotosAdicionada">
										<td>{{foto.name}}</td>
										<td><a href="#" ng-click="apagarFotoAdicionada(foto)" class="shortcut" style="text-decoration: none;" title="Apagar"><i class="shortcut-icon icon-remove-sign"></i></a></td>
									</tr>
									</tbody>
								</table>
							</div> <!-- /control-group -->

							<div class="control-group span7">
								<label class="control-label"><i class="icon-road"></i> Endereços de entrega &nbsp;&nbsp;&nbsp;&nbsp;<a href="#modalEndereco" ng-click="criarEndereco()" data-toggle="modal" ng-show="!visualizar"><i class="icon-plus"></i> Adicionar</a></label>
								<div class="controls span5" ng-show="enderecos.length == 0">
									Não disponível
								</div>
								<div class="controls span6">
									<input type="file" class="span3" ng-file-select="onPlanilhaSelect($files)" ng-show="!visualizar">
									<div class="accordion" id="accordion2">
										<div class="accordion-group" ng-repeat="endereco in enderecos">
											<div class="accordion-heading">
												<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#endereco{{endereco.idEndereco}}">
													<span>{{endereco.titulo}} - {{endereco.endereco}}<span ng-show="endereco.complemento != ''">, {{endereco.complemento}}</span></span>
													<span class="osAberta" ng-show="endereco.status == 'Pendente'">({{endereco.status}})</span>
													<span class="osFechada" ng-show="endereco.status != 'Pendente'">({{endereco.status}})</span>
													<span ng-show="!visualizar">
														<button href="#modalEndereco" data-toggle="modal" ng-click="alterarEndereco(endereco)">
															<i class="icon-edit" title="Alterar"></i>
														</button>
													</span>
													<span ng-show="!visualizar">
														<button href="#modalEndereco" ng-click="apagarEndereco(endereco)">
															<i class="icon-trash" title="Excluir"></i>
														</button>
													</span>
												</a>
											</div>
											<div id="endereco{{endereco.idEndereco}}" class="accordion-body collapse">
												<div class="accordion-inner">
													<div ng-show="endereco.referenciasEntrega.length == 0">Não disponível.</div>
													<div ng-repeat="re in endereco.referenciasEntrega">
														{{re.tipoEntrega}} ({{re.dataCriacao | date: 'dd/MM/yyyy HH:mm:ss'}}) <span ng-show="re.codigoReferencia != ''"><b>{{re.codigoReferencia}}</b></span>
														<table ng-show="re.tipoEntrega != 'PARTICULAR'">
															<tr ng-repeat="e in re.eventos">
																<td>{{e.titulo}}</td>
																<td><b>{{e.descricao}}</b></td>
															</tr>
														</table>
													</div>
                          <div>
                            <a href="#modalReferenciaEntrega" data-toggle="modal" ng-click="selecionarEnderecoEntrega(endereco)">Adicionar referência de entrega</a>
                          </div>
												</div>
											</div>
										</div>
									</div>
								</div> <!-- /controls -->
							</div> <!-- /control-group -->

							<div class="control-group span7">
								<label class="control-label">
									<i class="icon-money"></i> Informações da nota &nbsp;&nbsp;&nbsp;&nbsp;
									<a href="#modalNotaFiscal" data-toggle="modal" ng-show="!visualizar && ordemServico.notaFiscal == null"><i class="icon-magic"></i> Criar</a>
									<a href="#modalNotaFiscal" data-toggle="modal" ng-show="!visualizar && ordemServico.notaFiscal != null"><i class="icon-edit"></i> Alterar</a>
								</label>
								<div class="controls span5" ng-show="ordemServico.notaFiscal == null">
									Não disponível
								</div>
								<div class="controls span5" ng-show="ordemServico.notaFiscal != null">
									<div class="span1">N&uacute;mero</div>
									<div class="span3"><b>{{ordemServico.notaFiscal.numero}}<span ng-show="ordemServico.notaFiscal.numero == null">-</span></b></div>
									<div class="span1">Valor</div>
									<div class="span3"><b>R$ {{ordemServico.notaFiscal.valor}}</b></div>
									<div class="span1">Paga</div>
									<div class="span3" ng-show="ordemServico.notaFiscal.paga"><b>Sim</b></div>
									<div class="span3" ng-show="!ordemServico.notaFiscal.paga"><b>Não</b></div>
								</div> <!-- /controls -->
								<div class="controls span5" ng-repeat="detalheNota in ordemServico.notaFiscal.detalhesNota">
									<div class="span1"><span ng-show="{{$index == 0}}">Vencimentos</span><span ng-show="{{$index > 0}}">&nbsp;</span></div>
									<div class="span3"><b>{{detalheNota.dataVencimentoTxt}}</b></div>
								</div>
							</div> <!-- /control-group -->

						</div>
					</div>
				</div>
				<div class="form-actions">
					<img src="img/ajax-loader.gif" ng-show="salvando || carregandoFotos || carregandoEndereco">
					<button class="btn btn-primary" type="button" ng-click="alterar(ordemServico)" ng-show="visualizar">Editar serviço</button>
					<button class="btn btn-primary" type="button" ng-click="salvar(ordemServico)" ng-show="!visualizar" ng-disabled="salvando || carregandoFotos || carregandoEndereco">Salvar</button>
					<button class="btn" type="button" ng-click="ordemServico = null;" ng-disabled="salvando || carregandoFotos || carregandoEndereco">Voltar</button>

					<span ng-show="salvando || carregandoFotos || carregandoEndereco || carregandoEtapa">Aguarde... </span>
					<span ng-show="salvando && !carregandoEndereco && !carregandoEtapa && !carregandoFotos">Salvando OS.</span>
					<span ng-show="carregandoEndereco">Carregando endereços.</span>
					<span ng-show="!carregandoEndereco && carregandoEtapa">Carregando etapa.</span>
					<span ng-show="!carregandoEndereco && !carregandoEtapa && carregandoFotos">Carregando fotos.</span>

				</div>
			</div>

      <div id="modalReferenciaEntrega" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button>
					<h3 id="myModalLabel">Referência entrega</h3>
        </div>
        <div class="modal-body">
          <div class="control-group">
            <label class="control-label" for="txtCodigoReferencia">Código de referência</label>
            <div class="controls">
              <input type="text" class="span3" id="txtCodigoReferencia" ng-model="codigoReferencia">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-info" data-dismiss="modal" aria-hidden="true" ng-click="adicionarReferenciaEntrega()" type="button">Adicionar</button>
					<button class="btn" data-dismiss="modal" aria-hidden="true" type="button">Cancelar</button>
        </div>
      </div>

			<div id="modalImagem" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button>
					<h3 id="myModalLabel" style="color: #d9534f;" ng-show="!foto.aprovada">{{foto.nome}} <i class="icon-thumbs-down"></i> reprovada</h3>
					<h3 id="myModalLabel" style="color: #5cb85c;" ng-show="foto.aprovada">{{foto.nome}} <i class="icon-thumbs-up"></i> aprovada</h3>
				</div>
				<div class="modal-body">
					<center>
						<img ng-src="{{foto.caminhoCompleto}}" style="max-width: 100%; max-height: 100%;" />
					</center>
				</div>
				<div class="modal-footer">
					<button class="btn" ng-click="fotoAnterior()"><</button>
					<b>{{fotos.indexOf(foto) + 1}}</b> de {{fotos.length}}
					<button class="btn" ng-click="proximaFoto()">></button>
					<button class="btn btn-info" data-dismiss="modal" aria-hidden="true" ng-click="setVerFoto(false, null)">Fechar</button>
					<button class="btn btn-danger" style="float: left;" ng-show="!visualizar" ng-click="apagarFoto(foto)"><i class="icon-trash"></i> Excluir</button>
					<button class="btn btn-success" style="float: left;" ng-show="!foto.aprovada" ng-click="manterAprovacao(foto)"><i class="icon-thumbs-up"></i> Aprovar</button>
					<button class="btn btn-warning" style="float: left;" ng-show="foto.aprovada" ng-click="manterAprovacao(foto)"><i class="icon-thumbs-down"></i> Reprovar</button>
					<img style="float: left;" src="img/ajax-loader.gif" ng-show="carregandoAprovacao">
				</div>
			</div>

			<div id="modalEndereco" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button>
					<h3 id="myModalLabel">Endereço</h3>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="control-group">
							<label class="control-label" for="txtEndereco">Endereço</label>
							<div class="controls">
								<input type="text" class="span3" id="txtEndereco" ng-model="endereco.endereco">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="txtTitulo">Título</label>
							<div class="controls">
								<input type="text" class="span3" id="txtTitulo" ng-model="endereco.titulo">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="txtCep">CEP</label>
							<div class="controls">
								<input type="text" class="span3" id="txtCep" ng-model="endereco.cep">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="txtComplemento">Complemento</label>
							<div class="controls">
								<input type="text" class="span3" id="txtComplemento" ng-model="endereco.complemento">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="txtBairro">Bairro</label>
							<div class="controls">
								<input type="text" class="span3" id="txtBairro" ng-model="endereco.bairro">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="txtEstado">Estado</label>
							<div class="controls">
								<input type="text" class="span3" id="txtEstado" ng-model="endereco.estado">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->

						<div class="control-group">
							<label class="control-label" for="txtCidade">Cidade</label>
							<div class="controls">
								<input type="text" class="span3" id="txtCidade" ng-model="endereco.cidade">
							</div> <!-- /controls -->
						</div> <!-- /control-group -->
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-info" data-dismiss="modal" aria-hidden="true" ng-click="manterEndereco()" type="button">Ok</button>
					<button class="btn" data-dismiss="modal" aria-hidden="true" type="button">Fechar</button>
				</div>
			</div>

			<div id="modalNotaFiscal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i></button>
					<h3 id="myModalLabel">Nota fiscal</h3>
				</div>
				<div class="modal-body">
					<div class="control-group">
						<label class="control-label" for="numeroNota">Número</label>
						<div class="controls">
							<input type="text" class="span3" id="numeroNota" ng-model="ordemServico.notaFiscal.numero">
						</div> <!-- /controls -->
					</div> <!-- /control-group -->
					<div class="control-group">
						<label class="control-label" for="paga">Paga</label>
						<div class="controls">
							<input type="checkbox" class="span3" id="paga" ng-model="ordemServico.notaFiscal.paga">
						</div> <!-- /controls -->
					</div> <!-- /control-group -->
					<div class="control-group">
						<label class="control-label" for="valorNota">Valor</label>
						<div class="controls">
							<input type="text" class="span3" id="valorNota" ng-required="true" ng-model="ordemServico.notaFiscal.valor">
						</div> <!-- /controls -->
					</div> <!-- /control-group -->

					<div class="control-group">
						<label class="control-label">Vencimentos</label>
						<div class="controls" ng-repeat="detalheNota in ordemServico.notaFiscal.detalhesNota">
							<input mask="39/19/2099" ng-model="detalheNota.dataVencimentoTxt" id="dtVencimento"><button type="button" class="btn btn-danger" title="Remover vencimento" ng-click="removerDetalheNota(detalheNota)"><i class="icon-trash"></i></button>
						</div> <!-- /controls -->
					</div> <!-- /control-group -->

					<button type="button" class="btn" ng-click="criarDetalheNota(ordemServico.notaFiscal)"><i class="icon-plus"></i> Adicionar vencimento</button>
				</div>
				<div class="modal-footer">
					<button class="btn btn-info" data-dismiss="modal" aria-hidden="true">Ok</button>
					<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
				</div>
			</div>

            <div id="divServicos" class="widget-content" ng-show="ordemServico == null && !exibirImportar">

	            <div class="row">
	            	<div class="span4">
						<a class="btn" href="gerar_os.jsp"><i class="icon-plus"></i> Criar OS</a>
						<a class="btn" href="#" ng-click="importar()"><i class="icon-download-alt"></i> Importar informações</a>
					</div>

					<div class="span5">
						<form class="form-horizontal" ng-submit="pesquisarAcao()">
							<fieldset>
								<div class="input-append">
									<input class="span3 m-wrap" ng-model="campoPesquisa" type="text" placeholder="nro os, cliente, descrição...">
									<button class="btn" type="submit">Pesquisar</button>
								</div>
							</fieldset>
						</form>
					</div>
				</div>

				<table class="table table-striped table-bordered">
					<thead>
					  <tr>
					  	<th class="td-actions"> </th>
						<th>OS</th>
						<th>Cliente</th>
						<th>Descri&ccedil;&atilde;o</th>
						<th>Status</th>
						<th>Data cria&ccedil;&atilde;o</th>
						<th>Provisória</th>
						<th class="td-actions"> </th>
					  </tr>
					</thead>
					<tbody>
					  <tr ng-repeat="os in ordensServico">
					  	<td><a href="#" ng-click="visualizarOs(os)" class="shortcut" style="text-decoration: none;" title="Detalhes"><i class="shortcut-icon icon-search"></i></a></td>
						<td>{{os.numero}}</td>
						<td>{{os.cliente.nome}}</td>
						<td>{{os.descricao}}</td>
						<td>{{os.status}} ({{os.nomeEtapaAtual}})</td>
						<td>{{os.dataCriacao | date: 'dd/MM/yyyy HH:mm:ss'}}</td>
						<td><span ng-show="os.temporary">Sim</span><span ng-show="!os.temporary">Não</span></td>
						<td>
							<a href="#" ng-click="alterar(os)" class="shortcut" style="text-decoration: none;" title="Editar"><i class="shortcut-icon icon-edit"></i></a>
							&nbsp;
							<a href="#" ng-click="apagar(os)" class="shortcut" style="text-decoration: none;" title="Inativar"><i class="shortcut-icon icon-remove-sign"></i></a>
						</td>
					  </tr>
					</tbody>
				</table>
				<div class="row">
					<div class="span12">
						<button class="span11 btn" ng-click="pesquisar();" ng-show="carregarMais"><img src="img/ajax-loader.gif" ng-show="carregando"> Carregar mais</button>
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
<script src="js/ServicoCtrl.js?v20190111"></script>
</body>
</html>
