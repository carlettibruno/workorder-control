/**
 * TrackingBeanServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package jadlogEdiws;

public class TrackingBeanServiceLocator extends org.apache.axis.client.Service implements jadlogEdiws.TrackingBeanService {

    public TrackingBeanServiceLocator() {
    }


    public TrackingBeanServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TrackingBeanServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TrackingBean
    private java.lang.String TrackingBean_address = "http://www.jadlog.com.br:8080/JadlogEdiWs/services/TrackingBean";

    public java.lang.String getTrackingBeanAddress() {
        return TrackingBean_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TrackingBeanWSDDServiceName = "TrackingBean";

    public java.lang.String getTrackingBeanWSDDServiceName() {
        return TrackingBeanWSDDServiceName;
    }

    public void setTrackingBeanWSDDServiceName(java.lang.String name) {
        TrackingBeanWSDDServiceName = name;
    }

    public jadlogEdiws.TrackingBean getTrackingBean() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TrackingBean_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTrackingBean(endpoint);
    }

    public jadlogEdiws.TrackingBean getTrackingBean(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            jadlogEdiws.TrackingBeanSoapBindingStub _stub = new jadlogEdiws.TrackingBeanSoapBindingStub(portAddress, this);
            _stub.setPortName(getTrackingBeanWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTrackingBeanEndpointAddress(java.lang.String address) {
        TrackingBean_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (jadlogEdiws.TrackingBean.class.isAssignableFrom(serviceEndpointInterface)) {
                jadlogEdiws.TrackingBeanSoapBindingStub _stub = new jadlogEdiws.TrackingBeanSoapBindingStub(new java.net.URL(TrackingBean_address), this);
                _stub.setPortName(getTrackingBeanWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TrackingBean".equals(inputPortName)) {
            return getTrackingBean();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://jadlogEdiws", "TrackingBeanService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://jadlogEdiws", "TrackingBean"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TrackingBean".equals(portName)) {
            setTrackingBeanEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
