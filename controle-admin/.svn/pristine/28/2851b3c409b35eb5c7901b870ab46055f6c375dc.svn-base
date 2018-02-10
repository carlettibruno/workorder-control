var app = angular.module('app', []);

function AlterarSenhaCtrl($scope, $http) {
	
	$scope.login = null;
	$scope.status = null;
	
	$scope.alterarSenha = function() {
		var http = $http({url: 'services/login/trocarsenha', data: $scope.login, method: "PUT", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.status = data;
			$scope.login = null;
        });		
	};
	
}