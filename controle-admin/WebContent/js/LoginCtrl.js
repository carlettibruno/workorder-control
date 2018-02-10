var app = angular.module('app', []);

function LoginCtrl($scope, $http, $window) {
	
	$scope.login = null;
	$scope.esqueciMinhaSenha = false;
	$scope.retorno = null;
	$scope.carregandoFormulario = false;

	$scope.onload = function() {
		var http = $http({url: 'services/login/permissao?token='+$.cookie('token'), method: "GET", headers: {'Content-Type': 'application/json'}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			if(data.codigo == 0) {				
				$window.location.href = 'inicio.jsp';
			}
		});		
	};
	$scope.onload();
	
	$scope.entrar = function() {
		$scope.carregandoFormulario = true;
		var http = $http({url: 'services/login', method: "POST", data: $scope.login, headers: {'Content-Type': 'application/json'}});
		http.success(function (data, status, headers, config) {
			if(data.codigo == 0) {
				$.cookie('token', data.data, {expires: 999, path: '/'});
				$.cookie('usuario', $scope.login.usuario, {expires: 999, path: '/'});
				$window.location.href = 'inicio.jsp';
			} else {
				$scope.retorno = data;
			}
			$scope.carregandoFormulario = false;
		});
	};
	
	$scope.enviarSenha = function() {
		$scope.carregandoFormulario = true;
		var http = $http({url: 'services/login/recuperarsenha?email='+$scope.email, method: "POST", data: $scope.login, headers: {'Content-Type': 'application/json'}});
		http.success(function (data, status, headers, config) {
			$scope.retorno = data;
			$scope.carregandoFormulario = false;
		});
	};
	
	$scope.mudarEsqueciMinhaSenha = function() {
		$scope.esqueciMinhaSenha = !$scope.esqueciMinhaSenha;
		$scope.retorno = null;
	};
	
}