var app = angular.module('app', []);

function EtapaCtrl($scope, $http) {
	
	$scope.etapas = new Array();
	$scope.etapa = null;
	$scope.retorno = null;
//	$scope.etapasEntrega = new Array();
	$scope.salvando = false;
	
	$scope.pesquisar = function() {
		var http = $http({url: 'services/etapa', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			$scope.etapas = data.data;
        }).
        error(function (data, status, headers, config){
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	$scope.pesquisar();	
	
	$scope.apagar = function(etapa) {
		if(window.confirm("Deseja inativar a etapa \""+etapa.nome+"\"?")) {
			var http = $http({url: 'services/etapa/'+etapa.idEtapa, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
	            $scope.retorno = data;
	            angular.forEach($scope.etapas, function(value, key){
	            	if(value.idEtapa == etapa.idEtapa) {            		
	            		$scope.etapas.splice(key, 1);
	            	}
	            });	            
	        }).
	        error(function (data, status, headers, config){
				$scope.retorno = {codigo:1,mensagem:data};
			});			
		}
	};
	
	$scope.salvar = function() {
		$scope.salvando = true;
		var idPost = "";
		var metodo = "";
		if($scope.etapa.idEtapa > 0) {
			idPost = "/"+$scope.etapa.idEtapa;
			metodo = "PUT";
		} else {
			metodo = "POST";
		}
		var http = $http({url: 'services/etapa'+idPost, method: metodo, data: $scope.etapa, headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.salvando = false;
            $scope.retorno = data;
            if($scope.retorno.codigo == 0) {
	            $scope.retorno.mensagem = "Etapa \""+$scope.etapa.nome+"\" gravada";
	            $scope.etapa = {idEtapa:0};
            }
        }).
        error(function (data, status, headers, config){
        	$scope.salvando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	
	$scope.alterar = function(etapa) {
		$scope.etapa = etapa;
	};

	$scope.novo = function() {
		$scope.etapa = {idEtapa: 0};
		$scope.retorno = null;
	};
	
	$scope.cancelar = function() {
		$scope.etapa = null;
	};	
	
}