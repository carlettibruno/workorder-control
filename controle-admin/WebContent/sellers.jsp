<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="app">
<head>
<title>Administração - Vendedores</title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body ng-cloak>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="main" ng-controller="SellerCtrl">
  <div class="main-inner">
    <div class="container">
      <div class="row">
        <div class="span12">
          <div class="widget">
            <div class="widget-header"> <i class="icon-user"></i>
              <h3> Vendedores</h3>
            </div>
            <!-- /widget-header -->
			<div class="widget-content" ng-show="seller != null">
				<div class="alert alert-success" ng-show="retorno.codigo == 0">
				  <button type="button" class="close" data-dismiss="alert">&times;</button>
				  <strong>Sucesso!</strong> {{retorno.mensagem}}. <a href="sellers.jsp">Clique aqui</a> para listar os vendedores.
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
								<label class="control-label" for="seller">Nome</label>
								<div class="controls">
									<input type="text" class="span3" id="seller" ng-model="seller.nome">
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
            <div class="widget-content" ng-show="seller == null">
			<a class="btn" ng-click="novo()"><i class="icon-plus"></i> Novo vendedor</a>
			<table class="table table-striped table-bordered">
                <thead>
                  <tr>
                    <th>Vendedor</th>
					<th class="td-actions"> </th>
                  </tr>
                </thead>
                <tbody>
				  <tr ng-repeat="seller in sellers">
					<td>{{seller.nome}}</td>
					<td>
						<a href="#" class="shortcut" ng-click="alterar(seller)" style="text-decoration: none;" title="Editar"><i class="shortcut-icon icon-edit"></i></a>
						&nbsp;
						<a href="#" class="shortcut" ng-click="apagar(seller)" style="text-decoration: none;" title="Inativar"><i class="shortcut-icon icon-remove-sign"></i></a>
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
<script src="js/SellerCtrl.js?v20190620"></script>
</body>
</html>
