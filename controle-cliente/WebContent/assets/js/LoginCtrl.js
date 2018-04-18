function LoginCtrl($scope, $http, $window) {
	
	$scope.login = {usuario:'',senha:''};
	$scope.esqueciMinhaSenha = false;
	$scope.retorno = null;

	$scope.onload = function() {
		$.mobile.loading("show");
		if($.cookie('token') != undefined) {			
			var http = $http({url: URL_ROOT + 'services/login/permissao?token='+$.cookie('token'), method: "GET", headers: {'Content-Type': 'application/json'}, params: {'nocache': new Date().getTime()}});
			http.success(function (data, status, headers, config) {
				if(data.codigo == 0) {				
					$window.location.href = 'lista_os.jsp';
				}
				$.mobile.loading("hide");
			});		
		} else {
			$.mobile.loading("hide");
		}
	};
	$scope.onload();
	
	$scope.entrar = function() {
		$.mobile.loading("show");
		$scope.login.manterConectado = true;
		var http = $http({url: URL_ROOT + 'services/login', method: "POST", data: $scope.login, headers: {'Content-Type': 'application/json'}});
		http.success(function (data, status, headers, config) {
			if(data.codigo == 0) {
				$.cookie('token', data.data, {expires: 9999999999, path: '/'});
				$.cookie('usuario', $scope.login.usuario, {expires: 9999999999, path: '/'});
				$window.location.href = 'lista_os.jsp';
			} else {
				$scope.retorno = data;
			}
			$.mobile.loading("hide");
		});
	};
	
	$scope.enviarSenha = function() {
		$.mobile.loading("show");
		var http = $http({url: URL_ROOT + 'services/login/recuperarsenha?email='+$scope.email, method: "POST", data: $scope.login, headers: {'Content-Type': 'application/json'}});
		http.success(function (data, status, headers, config) {
			$scope.retorno = data;
			$.mobile.loading("hide");
		});
	};
	
	$scope.mudarEsqueciMinhaSenha = function() {
		$scope.esqueciMinhaSenha = !$scope.esqueciMinhaSenha;
		$scope.retorno = null;
	};
	
}