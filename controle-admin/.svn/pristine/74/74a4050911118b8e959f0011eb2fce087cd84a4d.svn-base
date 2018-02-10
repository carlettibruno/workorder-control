var app = angular.module('app', []);

function FuncionarioCtrl($scope, $http) {
	
	$scope.funcionarios = new Array();
	$scope.funcionario = null;
	$scope.retorno = null;
	$scope.carregarMais = true;
	$scope.carregando = false;
	$scope.grupos = new Array();
	$scope.campoPesquisa = "";
	
	$scope.pesquisarAcao = function() {
		$scope.funcionarios = new Array();
		$scope.pesquisar();
	};	
	
	$scope.pesquisar = function() {
		$scope.carregarMais = true;
		$scope.carregando = true;
		var http = $http({url: 'services/funcionario?filtro='+$scope.campoPesquisa+'&inicio='+$scope.funcionarios.length+'&qtderegistro=10', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			if(data.data.length == 0) {
				$scope.carregarMais = false;
			} else {
				$scope.funcionarios = $scope.funcionarios.concat(data.data);
			}
			$scope.carregando = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});		
	};
	$scope.pesquisar();
	
	$scope.carregarGrupos = function() {
		var http = $http({url: 'services/funcionario/grupos', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			$scope.grupos = data.data;
        });		
	};
	$scope.carregarGrupos();
	
	$scope.salvar = function() {
		var idUsuarioPost = "";
		var metodo = "";
		if($scope.funcionario.idUsuario > 0) {
			idUsuarioPost = "/"+$scope.funcionario.idUsuario;
			metodo = "PUT";
		} else {
			metodo = "POST";
		}
		$scope.carregando = true;
		var http = $http({url: 'services/funcionario'+idUsuarioPost, method: metodo, data: $scope.funcionario, headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
            $scope.retorno = data;
            if($scope.retorno.codigo == 0) {
            	if($scope.retorno.mensagem == null) {            		
            		$scope.retorno.mensagem = "Funcionário \""+$scope.funcionario.nome+"\" gravado.";
            	}
            	$scope.funcionario = {idUsuario:0};            	
            }
            $scope.carregando = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	
	$scope.apagar = function(funcionario) {
		if(window.confirm("Deseja realmente apagar o funcionario \""+funcionario.nome+"\"?")) {
			$scope.carregando = true;
			var http = $http({url: 'services/funcionario/'+funcionario.idUsuario, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
				$scope.carregando = false;
	            $scope.retorno = data;
	            angular.forEach($scope.funcionarios, function(value, key){
	            	if(value.idUsuario == funcionario.idUsuario) {            		
	            		$scope.funcionarios.splice(key, 1);
	            	}
	            });	            
	        }).
	        error(function (data, status, headers, config){
	        	$scope.carregando = false;
				$scope.retorno = {codigo:1,mensagem:data};
			});
		}
	};
	
	$scope.alterar = function(funcionario) {
		$scope.funcionario = funcionario;
		angular.forEach($scope.grupos, function(value, key){
			angular.forEach($scope.funcionario.grupos, function(valueGrupo, keyGrupo){
				if(valueGrupo.idGrupo == value.idGrupo) {
					$scope.funcionario.grupos[keyGrupo] = value;
				}
			});
		});
		$scope.retorno = null;
	};
	
	$scope.novo = function() {
		$scope.funcionario = {idUsuario: 0};
		$scope.retorno = null;
	};
	
	$scope.cancelar = function() {
		$scope.funcionario = null;
	};	
		
}