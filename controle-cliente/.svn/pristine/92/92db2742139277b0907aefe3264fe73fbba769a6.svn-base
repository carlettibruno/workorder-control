function AlterarSenhaCtrl($scope, $http) {
	
	$scope.login = null;
	$scope.retorno = null;
	
	$scope.alterarSenha = function() {
		var http = $http({url: URL_ROOT + 'services/login/trocarsenha', data: $scope.login, method: "PUT", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.retorno = data;
			$scope.login = null;
        });		
	};
	
}