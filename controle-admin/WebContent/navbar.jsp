<span ng-controller="SessaoCtrl">
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container"> <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"><span class="icon-bar"></span>
		<span class="icon-bar"></span><span class="icon-bar"></span> </a><a class="brand" href="gerar_os.html">Grafstock Administra&ccedil;&atilde;o</a>
      <div class="nav-collapse">
        <ul class="nav pull-right">
          <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog"></i> {{nomeUsuario}} <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="alterar_senha.jsp"><i class="icon-key"></i> Alterar senha</a></li>
              <li ng-show="permissaoConfiguracao.exibir"><a href="configuracao.jsp"><i class="icon-cogs"></i> Configurações</a></li>
              <li><a href="#" ng-click="sair()"><i class="icon-signout"></i> Sair</a></li>
            </ul>
          </li>
        </ul>
      </div>
      <!--/.nav-collapse --> 
    </div>
    <!-- /container --> 
  </div>
  <!-- /navbar-inner --> 
</div>
<!-- /navbar -->
<div class="subnavbar">
  <div class="subnavbar-inner">
    <div class="container">
      <ul class="mainnav">
      	<li <%= request.getRequestURL().toString().contains("inicio.jsp") ? "class=\"active\"" : "" %>><a href="inicio.jsp"><i class="icon-home"></i><span>Home</span> </a> </li>
        <li ng-show="permissaoGerarOs.exibir" <%= request.getRequestURL().toString().contains("gerar_os.jsp") ? "class=\"active\"" : "" %>><a href="gerar_os.jsp"><i class="icon-print"></i><span>Gerar OS</span> </a> </li>
        <li ng-show="permissaoFuncionario.exibir" <%= request.getRequestURL().toString().contains("funcionarios.jsp") ? "class=\"active\"" : "" %>><a href="funcionarios.jsp"><i class="icon-user"></i><span>Funcion&aacute;rios</span> </a></li>
        <li ng-show="permissaoCliente.exibir" <%= request.getRequestURL().toString().contains("clientes.jsp") ? "class=\"active\"" : "" %>><a href="clientes.jsp"><i class="icon-group"></i><span>Clientes</span> </a> </li>
        <li ng-show="permissaoServicos.exibir" <%= request.getRequestURL().toString().contains("servicos.jsp") ? "class=\"active\"" : "" %>><a href="servicos.jsp"><i class="icon-truck"></i><span>Servi&ccedil;os</span> </a> </li>
		<li ng-show="permissaoEtapas.exibir" <%= request.getRequestURL().toString().contains("etapas.jsp") ? "class=\"active\"" : "" %>><a href="etapas.jsp"><i class="icon-qrcode"></i><span>Etapas</span> </a> </li>
      </ul>
    </div>
    <!-- /container --> 
  </div>
  <!-- /subnavbar-inner --> 
</div>
<!-- /subnavbar -->
</span>