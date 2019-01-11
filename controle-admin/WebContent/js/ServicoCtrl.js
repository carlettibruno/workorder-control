var app = angular.module('app', ['angularFileUpload', 'datePicker', 'ngMask']);

function ServicoCtrl($scope, $http, $upload, $interval, $timeout) {

	$scope.ordensServico = new Array();
	$scope.ordemServico = null;
	$scope.retorno = null;
	$scope.carregarMais = true;
	$scope.carregando = false;
	$scope.exibirImportar = false;
	$scope.visualizar = false;
	$scope.fotos = null;
	$scope.historicos = null;
	$scope.enderecos = null;
	$scope.foto = null;
	$scope.endereco = null;
	$scope.fotosAdicionada = new Array();
	$scope.salvando = false;
	$scope.carregandoFotos = false;
	$scope.carregandoEndereco = false;
	$scope.carregandoEtapa = false;
	$scope.etapas = null;
	$scope.etapa = null;
	$scope.planilha = null;
	$scope.importando = false;
	$scope.envio = null;
	$scope.carregandoAprovacao = false;
	$scope.campoPesquisa = "";
	$scope.envioFinalizado = null;
	$scope.planilha = null;

	$scope.reset = function() {
		$scope.retorno = null;
		$scope.historicos = null;
		$scope.fotos = null;
		$scope.foto = null;
		$scope.endereco = null;
		$scope.enderecos = null;
		$scope.fotosAdicionada = new Array();
	};

	$scope.criarEndereco = function() {
		$scope.endereco = null;
	};

	$scope.alterarEndereco = function(endereco) {
		$scope.endereco = endereco;
	};

	$scope.manterEndereco = function() {
		if($scope.enderecos.indexOf($scope.endereco) == -1) {
			$scope.enderecos.push($scope.endereco);
		}
	};

	$scope.onFileSelect = function($files) {
		angular.forEach($files, function(value, key){
			$scope.fotosAdicionada.push(value);
		});
	};

	$scope.onPlanilhaSelect = function($files) {
		$scope.planilha = $files;
	};

	$scope.apagarFotoAdicionada = function(foto) {
		$scope.fotosAdicionada.splice($scope.fotosAdicionada.indexOf(foto), 1);
	};

	$scope.apagarEndereco = function(endereco) {
		if(window.confirm("Deseja apagar o endereço \""+endereco.endereco+"\"?")) {
			if(endereco.idEndereco == null) {
				var indexOf = $scope.enderecos.indexOf(endereco);
        		$scope.enderecos.splice(indexOf, 1);
        		alert("Endereço \""+endereco.endereco+"\" excluído com sucesso!");
			} else {
				var http = $http({url: 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/endereco/'+endereco.idEndereco, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
				http.success(function (data, status, headers, config) {
		            angular.forEach($scope.enderecos, function(value, key){
		            	if(value.idEndereco == endereco.idEndereco) {
		            		$scope.enderecos.splice(key, 1);
		            	}
		            });
		            alert("Endereço \""+endereco.endereco+"\" excluído com sucesso!");
		        }).
		        error(function (data, status, headers, config){
		        	alert(data);
				});
			}
		}
	};

	$scope.pesquisarAcao = function() {
		$scope.ordensServico = new Array();
		$scope.pesquisar();
	};

	$scope.pesquisar = function() {
		$scope.carregarMais = true;
		$scope.carregando = true;
		var http = $http({url: 'services/ordemservico?filtro='+$scope.campoPesquisa+'&inicio='+$scope.ordensServico.length+'&qtderegistro=15', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
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

	$scope.salvar = function() {
		$scope.salvando = true;
		$scope.ordemServico.previsaoEntrega = moment($scope.ordemServico.previsaoEntregaTxt, 'DD/MM/YYYY');
		delete $scope.ordemServico.previsaoEntregaTxt;
		if($scope.ordemServico.notaFiscal != null) {
			angular.forEach($scope.ordemServico.notaFiscal.detalhesNota, function(value, key) {
				value.dataVencimento = moment(value.dataVencimentoTxt, 'DD/MM/YYYY');
				delete value.dataVencimentoTxt;
			});
		}
		var http = $http({url: 'services/ordemservico/' + $scope.ordemServico.idOrdemServico, data: $scope.ordemServico, method: "PUT", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.ordemServico.previsaoEntregaTxt = $scope.ordemServico.previsaoEntrega.format('DD/MM/YYYY');
			if($scope.ordemServico.notaFiscal != null) {
				angular.forEach($scope.ordemServico.notaFiscal.detalhesNota, function(value, key) {
					value.dataVencimentoTxt = moment(value.dataVencimento).format('DD/MM/YYYY');
				});
			}
			$scope.carregandoFotos = true;
			$scope.verificaSalvamentoOs();
			angular.forEach($scope.enderecos, function(value, key){
				if(value.idEndereco == null) {
					var httpEndereco = $http({url: 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/endereco', data: value, method: "POST", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
				}
			});

			if($scope.etapa != null) {
				$scope.carregandoEtapa = true;
				var httpEtapa = $http({url: 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/historico?proximoIdEtapa='+$scope.etapa.idEtapa, method: "POST", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
				httpEtapa.success(function (data, status, headers, config) {
					$scope.carregandoEtapa = false;
				}).
				error(function (data, status, headers, config){
					$scope.carregandoEtapa = false;
				});
			}

			if($scope.planilha != null) {
				$upload.upload({
					url : 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/enderecos',
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
			}

			$upload.upload({
				url : 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/foto',
				method: "POST",
				headers: {'token': $.cookie('token')},
				file: $scope.fotosAdicionada,
				fileFormDataName: 'myFile'
			}).then(function(response) {
				$scope.carregandoFotos = false;
//				$scope.uploadResult.push(response.data);
			}, null, function(evt) {
//				$scope.progress[index] = parseInt(100.0 * evt.loaded / evt.total);
				$scope.carregandoFotos = false;
			}).xhr(function(xhr){
//				xhr.upload.addEventListener('abort', function(){console.log('aborted complete')}, false);
				$scope.carregandoFotos = false;
			});
        }).
        error(function (data, status, headers, config){
        	$scope.salvando = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};

	$scope.verificaSalvamentoOs = function() {
		if($scope.salvando) {
			if($scope.carregandoFotos || $scope.carregandoEtapa) {
				$timeout($scope.verificaSalvamentoOs, 500);
			} else {
				$scope.salvando = false;
				$scope.retorno = {codigo:0,mensagem:'Ordem de atualizada com sucesso!'};
			}
		}
	};

	$scope.apagarFoto = function(foto) {
		if(window.confirm("Deseja apagar a foto \""+foto.nome+"\"?")) {
			var http = $http({url: 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/foto/'+foto.idFoto, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
	            var chave = 0;
	            angular.forEach($scope.fotos, function(value, key){
	            	if(value.idFoto == foto.idFoto) {
	            		$scope.fotos.splice(key, 1);
	            		chave = key;
	            	}
	            });
	            $scope.foto = $scope.fotos[chave - 1];
	            $scope.proximaFoto();
	            alert("Foto \""+foto.nome+"\" excluída com sucesso!");
	        }).
	        error(function (data, status, headers, config){
	        	alert(data);
			});
		}
	};

	$scope.apagar = function(ordemServico) {
		if(window.confirm("Deseja inativar a OS \""+ordemServico.numero+"\"?")) {
			$scope.carregando = true;
			var http = $http({url: 'services/ordemservico/'+ordemServico.idOrdemServico, method: "DELETE", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
			http.success(function (data, status, headers, config) {
				$scope.carregando = false;
	            $scope.retorno = data;
	            angular.forEach($scope.ordensServico, function(value, key){
	            	if(value.idOrdemServico == ordemServico.idOrdemServico) {
	            		$scope.ordensServico.splice(key, 1);
	            	}
	            });
	            alert("OS \""+ordemServico.numero+"\" excluída com sucesso!");
	        }).
	        error(function (data, status, headers, config){
	        	$scope.carregando = false;
	        	alert(data);
//				$scope.retorno = {codigo:1,mensagem:data};
			});
		}
	};

	$scope.alterar = function(ordemServico) {
		$scope.visualizar = false;
		$scope.carregarOs(ordemServico);
	};

	$scope.visualizarOs = function(ordemServico) {
		$scope.visualizar = true;
		$scope.carregarOs(ordemServico);
	};

	$scope.carregarOs = function(ordemServico) {
		$scope.reset();

		$scope.ordemServico = ordemServico;
		$scope.ordemServico.previsaoEntregaTxt = moment($scope.ordemServico.previsaoEntrega).format('DD/MM/YYYY');
		$scope.retorno = null;

		var httpEtapa = $http({url: 'services/etapa', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpEtapa.success(function (data, status, headers, config) {
			$scope.etapas = data.data;
		});

		var httpFoto = $http({url: 'services/ordemservico/'+ordemServico.idOrdemServico+'/foto', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpFoto.success(function (data, status, headers, config) {
			$scope.fotos = data.data;
		});
		var httpHistorico = $http({url: 'services/ordemservico/'+ordemServico.idOrdemServico+'/historico', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpHistorico.success(function (data, status, headers, config) {
			$scope.historicos = data.data;
		});
		$scope.carregarEndereco();
		if(ordemServico.notaFiscal != null && ordemServico.notaFiscal.idNotaFiscal > 0) {
			var httpDetalhesNota = $http({url: 'services/ordemservico/'+ordemServico.idOrdemServico+'/notafiscal/'+ordemServico.notaFiscal.idNotaFiscal, method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
			httpDetalhesNota.success(function (data, status, headers, config) {
				ordemServico.notaFiscal.detalhesNota = data.data;
				angular.forEach(ordemServico.notaFiscal.detalhesNota, function(value, key) {
					value.dataVencimentoTxt = moment(value.dataVencimento).format('DD/MM/YYYY');
				});
			});
		}
	};

	$scope.carregarEndereco = function() {
		var httpEndereco = $http({url: 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/endereco', method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
		httpEndereco.success(function (data, status, headers, config) {
			$scope.enderecos = data.data;
	      angular.forEach($scope.enderecos, function(endereco, endkey){
	      	angular.forEach(endereco.referenciasEntrega, function(ref, refkey){
	        	if(ref.tipoEntrega != 'PARTICULAR') {
	        		var httpCorreios = $http({url: 'services/ordemservico/endereco/'+ref.codigoReferencia, method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
	        		httpCorreios.success(function (data, status, headers, config) {
	        			ref.eventos = data.data;
	        		});
	      		}
	      	});
	      });
		});
	};

	$scope.selecionarEnderecoEntrega = function(endereco) {
		$scope.enderecoSelecionado = endereco;
	};

	$scope.adicionarReferenciaEntrega = function() {
		var http = $http({
			url: 'services/ordemservico/endereco/'+$scope.enderecoSelecionado.idEndereco+'/referencia',
			method: "POST",
			headers: {'Content-Type': 'application/json', 'token': $.cookie('token')},
			data: $scope.codigoReferencia
		});
		http.success(function (data, status, headers, config) {
			$scope.codigoReferencia = '';
			$scope.carregarEndereco();
		});
	};

	$scope.importar = function() {
		$scope.exibirImportar = true;
		$scope.ordemServico = null;
	};

	$scope.onPlanilhaSelect = function($files) {
		$scope.planilha = $files;
	};

	$scope.cancelar = function() {
		$scope.ordemServico = null;
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

	$scope.importarServicos = function() {
		$scope.envioFinalizado = null;
		$scope.importando = true;
		var envio = {"entidade": "ORDEM_SERVICO"};
		var http = $http({url: 'services/envio', method: "POST", data: envio, headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			$scope.envio = data.data;

			$scope.intervalo = $interval($scope.checkImportar, 1000);

			$upload.upload({
				url : 'services/envio/'+data.data.idEnvio+'/infoos',
				method: "POST",
				headers: {'token': $.cookie('token')},
				file: $scope.planilha,
				fileFormDataName: 'myOs'
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

	$scope.checkImportar = function() {
//		var http = $http({url: 'services/envio/'+$scope.envio.idEnvio+'?'+new Date().getTime(), method: "GET", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}, params: {'nocache': new Date().getTime()}});
//		http.success(function (data, status, headers, config) {
//			$scope.envio = data.data;
//		});
	};

	$scope.manterAprovacao = function(foto) {
		$scope.carregandoAprovacao = true;
		var http = $http({url: 'services/ordemservico/'+$scope.ordemServico.idOrdemServico+'/foto/'+foto.idFoto+'?aprova='+(!foto.aprovada), method: "PUT", headers: {'Content-Type': 'application/json', 'token': $.cookie('token')}});
		http.success(function (data, status, headers, config) {
			if(data.codigo == 0) {
				foto.aprovada = !foto.aprovada;
			} else {
				$scope.retorno = data;
			}
			$scope.carregandoAprovacao = false;
		}).
        error(function (data, status, headers, config){
        	$scope.carregandoAprovacao = false;
			$scope.retorno = {codigo:1,mensagem:data};
		});
	};

	$scope.criarDetalheNota = function(notaFiscal) {
		var detalheNota = {};
		if(notaFiscal.detalhesNota == null) {
			notaFiscal.detalhesNota = new Array();
		}
		notaFiscal.detalhesNota.push(detalheNota);
	};

	$scope.removerDetalheNota = function(detalheNota) {
		var indexOf = $scope.ordemServico.notaFiscal.detalhesNota.indexOf(detalheNota);
		$scope.ordemServico.notaFiscal.detalhesNota.splice(indexOf, 1);
	};

}
