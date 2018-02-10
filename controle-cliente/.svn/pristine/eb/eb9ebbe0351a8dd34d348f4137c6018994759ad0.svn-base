function ListaOsCtrl($scope, $http, $window) {
	
	$scope.ordensServico = new Array();
	$scope.ordemServico = null;
	$scope.retorno = null;
	$scope.carregarMais = true;
	$scope.carregando = false;
	$scope.exibirImportar = false;
	$scope.fotos = null;
	$scope.historicos = null;
	$scope.enderecos = null;
	$scope.verFoto = false;
	$scope.foto = null;
	$scope.campoPesquisa = "";
	
	$scope.pesquisarAcao = function() {
		$scope.ordensServico = new Array();
		$scope.pesquisar();
	};
	
	$scope.pesquisar = function() {
		$scope.carregarMais = true;
		$scope.carregando = true;
		var http = $http({url: URL_ROOT + 'services/ordemservico?filtro='+$scope.campoPesquisa+'&inicio='+$scope.ordensServico.length+'&qtderegistro=6', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		http.success(function (data, status, headers, config) {
			if(data.data.length == 0) {
				$scope.carregarMais = false;
			} else {
				$scope.ordensServico = $scope.ordensServico.concat(data.data);
			}
			$scope.carregando = false;
        }).
        error(function (data, status, headers, config){
        	$scope.carregando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});		
	};
	$scope.pesquisar();
	
	$scope.visualizar = function(ordemServico) {
		$scope.fotos = null;
		$scope.enderecos = null;
		$scope.historicos = null;
		$scope.ordemServico = ordemServico;
		var httpFoto = $http({url: URL_ROOT + 'services/ordemservico/'+ordemServico.idOrdemServico+'/foto', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpFoto.success(function (data, status, headers, config) {
			$scope.fotos = data.data;
		});
		var httpHistorico = $http({url: URL_ROOT + 'services/ordemservico/'+ordemServico.idOrdemServico+'/historico', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpHistorico.success(function (data, status, headers, config) {
			$scope.historicos = data.data;
		});
		var httpEndereco = $http({url: URL_ROOT + 'services/ordemservico/'+ordemServico.idOrdemServico+'/endereco', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpEndereco.success(function (data, status, headers, config) {
			$scope.enderecos = data.data;
			
            angular.forEach($scope.enderecos, function(endereco, endkey){
            	angular.forEach(endereco.referenciasEntrega, function(ref, refkey){
	            	if(ref.tipoEntrega == 'CORREIOS') {
	            		var httpCorreios = $http({url: URL_ROOT + 'services/ordemservico/endereco/'+ref.codigoReferencia, method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
	            		httpCorreios.success(function (data, status, headers, config) {
	            			ref.eventos = data.data;
	            		});
            		}
            	});
            });			
		});
		if(ordemServico.notaFiscal != null && ordemServico.notaFiscal.idNotaFiscal > 0) {			
			var httpDetalhesNota = $http({url: URL_ROOT + 'services/ordemservico/'+ordemServico.idOrdemServico+'/notafiscal/'+ordemServico.notaFiscal.idNotaFiscal, method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
			httpDetalhesNota.success(function (data, status, headers, config) {
				ordemServico.notaFiscal.detalhesNota = data.data;
			});		
		}		
	};
	
	$scope.sair = function() {
		var http = $http({url: URL_ROOT + 'services/login/logout?token='+$.cookie('token'), method: "DELETE", headers: {'Content-Type': 'application/json'}});
		http.success(function (data, status, headers, config) {
			$window.location.href = 'index.jsp';
			$.cookie('token', null, {expires: 999, path: '/'});
		});		
	};	

	$scope.setVerFoto = function(verFoto, foto) {
		$scope.verFoto = verFoto;
		$scope.foto = foto;
	};

	$scope.proximaFoto = function() {
		var index = 0;
		if($scope.fotos.indexOf($scope.foto) < $scope.fotos.length - 1) {
			index = $scope.fotos.indexOf($scope.foto) + 1;
		}
		$scope.foto = $scope.fotos[index];
	};
	
	$scope.fotoAnterior = function() {
		var index = $scope.fotos.length - 1;
		if($scope.fotos.indexOf($scope.foto) > 0) {
			index = $scope.fotos.indexOf($scope.foto) - 1;
		}
		$scope.foto = $scope.fotos[index];
	};	
	
}