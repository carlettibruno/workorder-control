var app = angular.module('app', []);

function ConfiguracaoCtrl($scope, $http) {

	$scope.configuracoes = new Array();
	$scope.carregando = false;
	$scope.retorno = null;
	
	$scope.carregar = function() {
		$scope.carregando = true;
		var http = $http({url: 'services/configuracao', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			$scope.configuracoes = data.data;
			$scope.carregando = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});		
	};
	$scope.carregar();

	$scope.salvar = function() {
		$scope.carregando = true;
		angular.forEach($scope.configuracoes, function(value, key){
			var http = $http({url: 'services/configuracao/'+value.idConfiguracao, data: value, method: "PUT", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
				$scope.retorno = data;
				$scope.carregando = false;
	        }).
	        error(function (data, status, headers, config){
	        	$scope.carregando = false;
				$scope.retorno = {codigo:1,mensagem:data};
			});
		});
	};
	
}