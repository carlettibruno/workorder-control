
var app = angular.module('app', []);

function SessaoCtrl($scope, $http, $window) {
	$scope.nomeUsuario = $.cookie('usuario');
	$scope.permissaoFuncionario = {modulo:"FUNCIONARIO"};
	$scope.permissaoCliente = {modulo:"CLIENTE"};
	$scope.permissaoGerarOs = {modulo:"GERAR_OS"};
	$scope.permissaoServicos = {modulo:"ORDEM_SERVICO"};
	$scope.permissaoEtapas = {modulo:"ETAPA"};
	$scope.permissaoConfiguracao = {modulo:"CONFIGURACAO"};
	$scope.permissaoSeller = {modulo:"SELLER"};
	
	$scope.sair = function() {
		var http = $http({url: 'services/login/logout?token='+$.cookie('token'), method: "DELETE", headers: {'Content-Type': 'application/json'}});
		http.success(function (data, status, headers, config) {
			$window.location.href = 'index.html';
			$.cookie('token', null, {expires: 999, path: '/'});
		});		
	};
	
	$scope.onload = function() {
		var http = $http({url: 'services/login/permissao?token='+$.cookie('token'), method: "GET", headers: {'Content-Type': 'application/json'}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			if(data.codigo == 3) {
				$scope.sair();
			}            	
            angular.forEach(data.data, function(value, key){
        		$scope.popularPermissao($scope.permissaoFuncionario, value);
        		$scope.popularPermissao($scope.permissaoCliente, value);
        		$scope.popularPermissao($scope.permissaoGerarOs, value);
        		$scope.popularPermissao($scope.permissaoServicos, value);
        		$scope.popularPermissao($scope.permissaoEtapas, value);
				$scope.popularPermissao($scope.permissaoConfiguracao, value);
				$scope.popularPermissao($scope.permissaoSeller, value);
            });			
		});
	};
	$scope.onload();
	
	$scope.popularPermissao = function(permissao, permissaoService) {
    	if(permissao.modulo == permissaoService.modulo) {
    		permissao.exibir = true;
    		if(permissaoService.funcionalidade & 1) {
    			permissao.consultar = true;
    		}
    		if(permissaoService.funcionalidade & 2) {
    			permissao.inserir = true;
    		}
    		if(permissaoService.funcionalidade & 4) {
    			permissao.editar = true;
    		}
    		if(permissaoService.funcionalidade & 8) {
    			permissao.excluir = true;
    		}
    	}		
	};
	
}