package jadlogEdiws;

public class TrackingBeanProxy implements jadlogEdiws.TrackingBean {
  private String _endpoint = null;
  private jadlogEdiws.TrackingBean trackingBean = null;
  
  public TrackingBeanProxy() {
    _initTrackingBeanProxy();
  }
  
  public TrackingBeanProxy(String endpoint) {
    _endpoint = endpoint;
    _initTrackingBeanProxy();
  }
  
  private void _initTrackingBeanProxy() {
    try {
      trackingBean = (new jadlogEdiws.TrackingBeanServiceLocator()).getTrackingBean();
      if (trackingBean != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)trackingBean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)trackingBean)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (trackingBean != null)
      ((javax.xml.rpc.Stub)trackingBean)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public jadlogEdiws.TrackingBean getTrackingBean() {
    if (trackingBean == null)
      _initTrackingBeanProxy();
    return trackingBean;
  }
  
  public java.lang.String consultar(java.lang.String codCliente, java.lang.String password, java.lang.String NDs) throws java.rmi.RemoteException{
    if (trackingBean == null)
      _initTrackingBeanProxy();
    return trackingBean.consultar(codCliente, password, NDs);
  }
  
  public java.lang.String consultarPedido(java.lang.String codCliente, java.lang.String password, java.lang.String NDs) throws java.rmi.RemoteException{
    if (trackingBean == null)
      _initTrackingBeanProxy();
    return trackingBean.consultarPedido(codCliente, password, NDs);
  }
  
  
}