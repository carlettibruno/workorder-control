var app = angular.module('app', ['angularFileUpload']);

function ClienteCtrl($scope, $http, $interval, $upload) {
	
	$scope.clientes = new Array();
	$scope.cliente = null;
	$scope.retorno = null;
	$scope.carregarMais = true;
	$scope.carregando = false;
	$scope.exibirImportar = false;
	$scope.importando = false;
	$scope.envio = null;
	$scope.intervalo = null;
	$scope.planilha = null;
	$scope.enviandoSenha = false;
	$scope.envioFinalizado = null;
	$scope.campoPesquisa = "";
	
	$scope.pesquisarAcao = function() {
		$scope.clientes = new Array();
		$scope.pesquisar();
	};
	
	$scope.pesquisar = function() {
		$scope.carregarMais = true;
		$scope.carregando = true;
		var http = $http({url: 'services/cliente?nome='+$scope.campoPesquisa+'&inicio='+$scope.clientes.length+'&qtderegistro=10', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			if(data.data.length == 0) {
				$scope.carregarMais = false;
			} else {
				$scope.clientes = $scope.clientes.concat(data.data);
			}
			$scope.carregando = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});		
	};
	$scope.pesquisar();
	
	$scope.salvar = function() {
		var idClientePost = "";
		var metodo = "";
		if($scope.cliente.idCliente > 0) {
			idClientePost = "/"+$scope.cliente.idCliente;
			metodo = "PUT";
		} else {
			metodo = "POST";
		}
		$scope.carregando = true;
		var http = $http({url: 'services/cliente'+idClientePost, method: metodo, data: $scope.cliente, headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
            $scope.retorno = data;
            if($scope.retorno.codigo == 0) {
            	if($scope.retorno.mensagem == null) {            		
            		$scope.retorno.mensagem = "Cliente \""+$scope.funcionario.nome+"\" gravado.";
            	}
	            $scope.cliente = {idCliente:0};
            }
            $scope.carregando = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	
	$scope.apagar = function(cliente) {
		if(window.confirm("Deseja realmente apagar o cliente \""+cliente.nome+"\"?")) {
			$scope.carregando = true;
			var http = $http({url: 'services/cliente/'+cliente.idCliente, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
				$scope.carregando = false;
	            $scope.retorno = data;
	            angular.forEach($scope.clientes, function(value, key){
	            	if(value.idCliente == cliente.idCliente) {            		
	            		$scope.clientes.splice(key, 1);
	            	}
	            });	            
	        }).
	        error(function (data, status, headers, config){
	        	$scope.carregando = false;
				$scope.retorno = {codigo:1,mensagem:data};
			});
		}
	};
	
	$scope.alterar = function(cliente) {
		$scope.cliente = cliente;
		angular.forEach($scope.grupos, function(value, key){
			angular.forEach($scope.cliente.grupos, function(valueGrupo, keyGrupo){
				if(valueGrupo.idGrupo == value.idGrupo) {
					$scope.cliente.grupos[keyGrupo] = value;
				}
			});
		});
		$scope.retorno = null;
	};

	$scope.importar = function() {
		$scope.exibirImportar = true;
		$scope.cliente = null;
	};	
	
	$scope.novo = function() {
		$scope.cliente = {idCliente: 0, aprovacaoFoto: false};
		$scope.retorno = null;
	};
	
	$scope.cancelar = function() {
		$scope.cliente = null;
	};
	
	$scope.onPlanilhaSelect = function($files) {
		$scope.planilha = $files;
	};
	
	$scope.importarClientes = function() {
		$scope.envioFinalizado = null;
		$scope.importando = true;
		var envio = {"entidade": "CLIENTE"};
		var http = $http({url: 'services/envio', method: "POST", data: envio, headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.envio = data.data;
			
			$scope.intervalo = $interval($scope.checkImportarClientes, 1000);
			
			$upload.upload({ 
				url : 'services/envio/'+data.data.idEnvio+'/cliente',
				method: "POST",
				headers: {'token': $.cookie('token')},
				file: $scope.planilha,
				fileFormDataName: 'myClientes'
			}).then(function(response) {
				$scope.importando = false;
				$interval.cancel($scope.intervalo);
				$scope.envioFinalizado = response.data.data;
			}, null, function(evt) {
			}).xhr(function(xhr){
			});
			
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};
	
	$scope.checkImportarClientes = function() {
		var http = $http({url: 'services/envio/'+$scope.envio.idEnvio+'?'+new Date().getTime(), method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			$scope.envio = data.data;
		});
	};
	
	$scope.gerarNovaSenha = function(cliente) {
		if(window.confirm("Gerar nova senha para o cliente \""+cliente.nome+"\"?")) {
			$scope.enviandoSenha = true;
			var http = $http({url: 'services/cliente/'+cliente.idCliente+'/gerarsenha?enviarEmail=true', method: "PUT", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
				$scope.enviandoSenha = false;
				alert(data.mensagem);
			}).
	        error(function (data, status, headers, config){
	        	$scope.enviandoSenha = false;
	        	alert(data.mensagem);
			});
		}
	};
	
}