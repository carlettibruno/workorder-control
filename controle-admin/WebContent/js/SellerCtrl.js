var app = angular.module('app', []);

function SellerCtrl($scope, $http) {
	
	$scope.sellers = new Array();
	$scope.seller = null;
	$scope.retorno = null;
	$scope.salvando = false;
	
	$scope.pesquisar = function() {
		var http = $http({url: 'services/sellers', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			$scope.sellers = data.data;
        }).
        error(function (data, status, headers, config){
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	$scope.pesquisar();	
	
	$scope.apagar = function(seller) {
		if(window.confirm("Deseja inativar o vendedor \""+seller.nome+"\"?")) {
			var http = $http({url: 'services/sellers/'+seller.idSeller, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
				$scope.retorno = data;
				var index = $scope.sellers.indexOf(seller);
				$scope.sellers.splice(index, 1);
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
		if($scope.seller.idSeller > 0) {
			idPost = "/"+$scope.seller.idSeller;
			metodo = "PUT";
		} else {
			metodo = "POST";
		}
		var http = $http({url: 'services/sellers'+idPost, method: metodo, data: $scope.seller, headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.salvando = false;
            $scope.retorno = data;
            if($scope.retorno.codigo == 0) {
	            $scope.retorno.mensagem = "Vendedor \""+$scope.seller.nome+"\" salvo";
	            $scope.seller = {idSeller:0};
            }
        }).
        error(function (data, status, headers, config){
        	$scope.salvando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	
	$scope.alterar = function(seller) {
		$scope.seller = seller;
	};

	$scope.novo = function() {
		$scope.seller = {idSeller: 0};
		$scope.retorno = null;
	};
	
	$scope.cancelar = function() {
		$scope.seller = null;
	};	
	
}