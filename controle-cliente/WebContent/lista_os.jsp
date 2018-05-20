<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>
	<head>
		<jsp:include page="head.jsp"></jsp:include>
	</head>
	<body ng-controller="ListaOsCtrl" ng-cloak>
		<div data-role="page" id="pageone" class="jqm-demos jqm-home">
			<div ng-show="ordemServico == null">
				<div data-role="header" class="jqm-header" style="display: none; border: 0;" id="divPesquisa">
					<form ng-submit="pesquisarAcao();">
						<input type="text" id="txtCampoPesquisa" ng-model="campoPesquisa" placeholder="Pesquisar" style="padding-left: 40px;" />
						<a href="#" id="btnFecharPesquisa" ng-click="campoPesquisa = ''; pesquisarAcao();" class="jqm-search-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-delete ui-nodisc-icon ui-alt-icon ui-btn-left"></a>
						<button type="submit" class="jqm-search-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-arrow-r ui-nodisc-icon ui-alt-icon ui-btn-right"></button>
					</form>
				</div>
				  <div data-role="header" class="jqm-header">
					<h2><img src="assets/images/grafstock-logo.jpg" alt="Grafstock Festas"></h2>
					<a href="#" class="jqm-navmenu-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-bars ui-nodisc-icon ui-alt-icon ui-btn-left">Menu</a>
					<a href="#" id="btnPesquisar" class="jqm-search-link ui-btn ui-btn-icon-notext ui-corner-all ui-icon-search ui-nodisc-icon ui-alt-icon ui-btn-right">Search</a>
				  </div>

				  <div role="main" class="ui-content jqm-content">
					<ul data-role="listview" data-inset="true">
						<li ng-repeat="os in ordensServico">
							<a href="#" ng-click="visualizar(os)">
								<img ng-src="{{os.foto.caminhoCompletoThumb}}" class="ui-li-thumb">
								<h2 ng-show="os.status != 'Aberto'">OS {{os.numero}} <span class="osFechada">({{os.status}})</span></h2>
								<h2 ng-show="os.status == 'Aberto'">OS {{os.numero}} <span class="osAberta">({{os.status}})</span></h2>
								<p>{{os.descricao}}</p>
								<p class="ui-li-aside">{{os.dataCriacao | date: 'dd/MM/yyyy'}}</p>
							</a>
						</li>
					</ul>
					<a href="#" ng-click="pesquisar()" class="ui-shadow ui-btn ui-corner-all">Carregar mais...</a>
				  </div>

			    <div data-role="panel" class="jqm-navmenu-panel" data-position="left" data-display="overlay" data-theme="a">
			    	<ul class="jqm-list ui-alt-icon ui-nodisc-icon">
						<li data-icon="home"><a href="lista_os.jsp" data-ajax="false">Home</a></li>
						<li ><a href="trocar_senha.jsp" data-ajax="false">Alterar senha</a></li>
						<li ><a href="#" ng-click="sair()" data-ajax="false">Sair</a></li>
					</ul>
				</div>
			</div>

			<div ng-show="ordemServico != null && !verFoto">

			  <div data-role="header" class="jqm-header">
				<h2><img src="assets/images/grafstock-logo.jpg" alt="Grafstock Festas"></h2>
				<a href="#" ng-click="ordemServico = null" data-ajax="false" class="ui-btn ui-btn-icon-notext ui-corner-all ui-icon-back ui-nodisc-icon ui-alt-icon ui-btn-left">Voltar</a>
				<a href="lista_os.jsp" data-ajax="false" class="ui-btn ui-btn-icon-notext ui-corner-all ui-icon-home ui-nodisc-icon ui-alt-icon ui-btn-right">Home</a>
			  </div>

			  <div role="main" class="ui-content">
				<ul data-role="listview" data-inset="true">
					<li>
						<img ng-src="{{ordemServico.foto.caminhoCompletoThumb}}" class="ui-li-thumb">
						<h2 ng-show="ordemServico.status != 'Aberto'">OS {{ordemServico.numero}} <span class="osFechada">({{ordemServico.status}})</span></h2>
						<h2 ng-show="ordemServico.status == 'Aberto'">OS {{ordemServico.numero}} <span class="osAberta">({{ordemServico.status}})</span></h2>
						<p>{{ordemServico.descricao}}</p>
						<p ng-hide="ordemServico.previsaoEntrega == null || ordemServico.previsaoEntrega == ''">Previsão entrega <b>{{ordemServico.previsaoEntrega | date: 'dd/MM/yyyy'}}</b></p>
						<p class="ui-li-aside">{{ordemServico.dataCriacao | date: 'dd/MM/yyyy'}}</p>
					</li>
					<li data-role="list-divider">
						<h1>Fotos</h1>
						<li>
							<div class="row" ng-show="fotos.length > 0">
								<img src="assets/images/ajax-loader.gif" ng-show="fotos == null">
								<a ng-repeat="foto in fotos" href="#" ng-click="setVerFoto(true, foto)" data-ajax="false">
									<img ng-src="{{foto.caminhoCompletoThumb}}" class="ui-li-thumb col-lg-3" style="margin-top: 1%;">
								</a>
							</div>
							<div class="row" ng-show="fotos.length == 0">
								<div class="col-lg-4">
									Não disponível.
								</div>
							</div>
						</li>
					</li>

					<li data-role="list-divider">
						<h1>Informa&ccedil;&otilde;es da nota</h1>
						<li>
							<div class="row" ng-show="ordemServico.notaFiscal != null">
								<div class="col-lg-4">Número</div>
								<div class="col-lg-8"><b>{{ordemServico.notaFiscal.numero}}</b></div>
								<div class="col-lg-4">Valor</div>
								<div class="col-lg-8"><b>R$ {{ordemServico.notaFiscal.valor}}</b></div>
								<div class="col-lg-4">Paga</div>
								<div class="col-lg-8" ng-show="ordemServico.notaFiscal.paga"><b>Sim</b></div>
								<div class="col-lg-8" ng-show="!ordemServico.notaFiscal.paga"><b>Não</b></div>
							</div>
							<div class="row" ng-repeat="detalheNota in ordemServico.notaFiscal.detalhesNota">
								<div class="col-lg-4" ng-show="{{$index == 0}}">Vencimentos</div>
								<div class="col-lg-4" ng-show="{{$index > 0}}">&nbsp;</div>
								<div class="col-lg-8"><b>{{detalheNota.dataVencimento | date: 'dd/MM/yyyy'}}</b></div>
							</div>
							<div class="row" ng-show="ordemServico.notaFiscal == null">
								<div class="col-lg-4">
									Não ainda disponível, trabalho em produção.
								</div>
							</div>
						</li>
					</li>

					<li data-role="list-divider">
						<h1>Endereços de entrega</h1>
						<li ng-show="enderecos == null">
							<div class="row">
								<div class="col-lg-12">
									<img src="assets/images/ajax-loader.gif">
								</div>
							</div>
						</li>
						<li ng-show="enderecos.length == 0">
							<div class="row">
								<div class="col-lg-12">
									Não disponível.
								</div>
							</div>
						</li>
						<li ng-repeat="endereco in enderecos">
							<div class="row" ng-click="endereco.show = !endereco.show">
								<div class="col-lg-12">
									<h2>
										<span ng-show="!endereco.show">+&nbsp;&nbsp;</span>
										<span ng-show="endereco.show">-&nbsp;&nbsp;</span>
										<span class="osAberta" ng-show="endereco.status == 'Pendente'">({{endereco.status}})</span>
										<span class="osFechada" ng-show="endereco.status != 'Pendente'">({{endereco.status}})</span>
										<span>{{endereco.endereco}}, {{endereco.numero}}<span ng-show="endereco.complemento != ''">, {{endereco.complemento}}</span></span>
									</h2>
								</div>
							</div>
							<div class="row" ng-show="endereco.show">
								<div class="col-lg-12" ng-show="endereco.referenciasEntrega.length == 0">Não disponível.</div>
								<div ng-repeat="re in endereco.referenciasEntrega" style="margin:2%; border-top: 1px solid #666;">
									<div class="row">
										<div class="col-lg-3">Tipo entrega</div>
										<div class="col-lg-7"><b>{{re.tipoEntrega}}</b></div>
									</div>
									<div class="row">
										<div class="col-lg-3">Data</div>
										<div class="col-lg-7"><b>{{re.dataCriacao | date: 'dd/MM/yyyy HH:mm:ss'}}</b></div>
									</div>
									<div class="row" ng-show="re.codigoReferencia != ''">
										<div class="col-lg-3">Referência</div>
										<div class="col-lg-7"><b>{{re.codigoReferencia}}</b></div>
										<div class="col-lg-10" ng-show="re.tipoEntrega == 'CORREIOS'">
											<div ng-repeat="e in re.eventos" style="padding: 2%;">
												<div><i>{{e.titulo}}</i></div>
												<div><b>{{e.descricao}}</b></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</li>
					</li>

					<li data-role="list-divider">
						<h1>Hist&oacute;rico</h1>
						<li ng-show="historicos == null">
							<div class="row">
								<div class="col-lg-12">
									<img src="assets/images/ajax-loader.gif">
								</div>
							</div>
						</li>
						<li ng-show="historicos.length == 0">
							<div class="row">
								<div class="col-lg-12">
									Não disponível.
								</div>
							</div>
						</li>
						<li ng-repeat="historico in historicos">
							<div class="row">
								<div class="col-lg-12">
									<h2>{{historico.etapa.nome}} <span ng-show="historico.status != 'Em andamento'" class="osFechada">({{historico.status}})</span><span ng-show="historico.status == 'Em andamento'" class="osAberta">({{historico.status}})</span></h2>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-4">Início</div>
								<div class="col-lg-8"><b>{{historico.dataInicio | date: 'dd/MM/yyyy HH:mm:ss'}}</b></div>
							</div>
							<div class="row">
								<div class="col-lg-4" ng-show="historico.status == 'Em andamento'">Executando</div>
								<div class="col-lg-4" ng-show="historico.status != 'Em andamento'">Término</div>
								<div class="col-lg-8">
									<b>
										<i ng-show="historico.status == 'Em andamento'">Há {{historico.diferencaDatas}}</i>
										<span ng-show="historico.status != 'Em andamento'">{{historico.dataFim | date: 'dd/MM/yyyy HH:mm:ss'}}</span>
									</b>
								</div>
							</div>
						</li>
					</li>
				</ul>
		  	</div>

			</div>

			<div ng-show="ordemServico != null && verFoto">
			  <div data-role="header" class="jqm-header">
				<h2><img src="assets/images/grafstock-logo.jpg" alt="Grafstock Festas"></h2>
				<a href="#" ng-click="setVerFoto(false, null)" data-ajax="false" class="ui-btn ui-btn-icon-notext ui-corner-all ui-icon-back ui-nodisc-icon ui-alt-icon ui-btn-left">Voltar</a>
				<a href="lista_os.jsp" data-ajax="false" class="ui-btn ui-btn-icon-notext ui-corner-all ui-icon-home ui-nodisc-icon ui-alt-icon ui-btn-right">Home</a>
			  </div>

			  <div role="main" class="ui-content">
				<div class="article" style="text-align: center;">
					<p><img ng-src="{{foto.caminhoCompleto}}" style="max-width: 100%; max-height: 100%;"></p>
					<p>
						<a href="#right-panel" ng-click="fotoAnterior()" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-mini"><</a>
						<b>{{fotos.indexOf(foto) + 1}}</b> de {{fotos.length}}
						<a href="#right-panel" ng-click="proximaFoto()" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-mini">></a>
					</p>
				</div><!-- /article -->
			  </div>
			</div>
		</div>

	</body>
	<jsp:include page="scripts.jsp"></jsp:include>
	<script src="assets/js/ListaOsCtrl.js?v20140819"></script>
	<script>
		$(document).ready(function() {
			$("#btnPesquisar").click(function() {
				$("#divPesquisa").css("display", "block").toolbar({ position: "fixed" });
				$.mobile.resetActivePageHeight();
				$("#txtCampoPesquisa").focus();
			});
			$("#btnFecharPesquisa").click(function() {
				$("#divPesquisa").css("display", "none").toolbar("destroy");
				$.mobile.resetActivePageHeight();
			});
		});
	</script>
</html>
