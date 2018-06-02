var app = angular.module('app', ['angularFileUpload', 'datePicker']);

function GerarOrdemServicoCtrl($scope, $http, $upload) {

	$scope.ordemServico = {cliente:{}};
	$scope.clientes = new Array();
	$scope.retorno = null;
	$scope.campoPesquisaCliente = "";
	$scope.carregarMaisCliente = true;
	$scope.carregandoCliente = false;
	$scope.salvando = false;
	$scope.carregandoFotos = false;
	$scope.carregandoEndereco = false;
	$scope.usarPlanilhaEndereco = false;
	$scope.fotosAdicionada = new Array();
	$scope.planilha = null;

	$scope.onFileSelect = function($files) {
		angular.forEach($files, function(value, key){
			$scope.fotosAdicionada.push(value);
		});
	};

	$scope.onPlanilhaSelect = function($files) {
		$scope.planilha = $files;
	};

	$scope.apagarFoto = function(foto) {
		$scope.fotosAdicionada.splice($scope.fotosAdicionada.indexOf(foto), 1);
	};

	$scope.resetCarregando = function() {
		$scope.salvando = false;
		$scope.carregandoFotos = false;
		$scope.carregandoEndereco = false;
	}

	$scope.salvar = function() {
		delete $scope.ordemServico.cliente.marcado;
		$scope.salvando = true;
		$scope.carregandoFotos = true;
		$scope.carregandoEndereco = true;
		var http = $http({url: 'services/ordemservico', data: $scope.ordemServico, method: "POST", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			if(data.codigo > 0) {
				$scope.resetCarregando();
				$scope.retorno = data;
				return;
			}
			var idOs = data.data;
			if($scope.usarPlanilhaEndereco) {
				$upload.upload({
					url : 'services/ordemservico/'+idOs+'/enderecos',
					method: "POST",
					headers: {'token': $.cookie('token')},
					file: $scope.planilha,
					fileFormDataName: 'myEndereco'
				}).then(function(response) {
					$scope.carregandoEndereco = false;
				}, null, function(evt) {
					$scope.carregandoEndereco = false;
				}).xhr(function(xhr){
					$scope.carregandoEndereco = false;
				});
			} else {
				var httpEndereco = $http({url: 'services/ordemservico/'+idOs+'/endereco', data: $scope.ordemServico.cliente.endereco, method: "POST", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
				httpEndereco.success(function (data, status, headers, config) {
					$scope.carregandoEndereco = false;
				}).
				error(function (data, status, headers, config){
					$scope.carregandoEndereco = false;
					alert(data);
				});
			}

			$upload.upload({
				url : 'services/ordemservico/'+idOs+'/foto',
				method: "POST",
				headers: {'token': $.cookie('token')},
				file: $scope.fotosAdicionada,
				fileFormDataName: 'myFile'
			}).then(function(response) {
				$scope.carregandoFotos = false;
			}, null, function(evt) {
				$scope.carregandoFotos = false;
			}).xhr(function(xhr){
				$scope.carregandoFotos = false;
			});

        	$scope.salvando = false;
			$scope.retorno = {codigo:0,mensagem:'Ordem de serviço gerada com sucesso!'};
        }).
        error(function (data, status, headers, config){
        	$scope.resetCarregando();
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};

	$scope.atualizarClientes = function(nome) {
		$scope.carregarMaisCliente = true;
		$scope.carregandoCliente = true;
		var http = $http({url: 'services/cliente?inicio='+$scope.clientes.length+'&qtderegistro=6&nome='+$scope.campoPesquisaCliente, method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			if(data.data.length == 0) {
				$scope.carregarMaisCliente = false;
			} else {
				$scope.clientes = $scope.clientes.concat(data.data);
			}
			$scope.carregandoCliente = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregandoCliente = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};

	$scope.pesquisarClientes = function() {
		$scope.clientes = new Array();
		$scope.atualizarClientes($scope.campoPesquisaCliente);
	};

	$scope.abrirClientes = function() {
		$scope.campoPesquisaCliente = "";
		$scope.pesquisarClientes();
	};

	$scope.selecionarCliente = function(cliente) {
		angular.forEach($scope.clientes, function(value, key){
			value.marcado = false;
		});
		cliente.marcado = true;
		$scope.ordemServico.cliente = cliente;
		$scope.ordemServico.email = cliente.email;
	};

	$scope.setUsarPlanilhaEndereco = function(usar) {
		$scope.usarPlanilhaEndereco = usar;
	};
}
