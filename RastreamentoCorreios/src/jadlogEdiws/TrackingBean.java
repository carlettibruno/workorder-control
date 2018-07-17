/**
 * TrackingBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package jadlogEdiws;

public interface TrackingBean extends java.rmi.Remote {
    public java.lang.String consultar(java.lang.String codCliente, java.lang.String password, java.lang.String NDs) throws java.rmi.RemoteException;
    public java.lang.String consultarPedido(java.lang.String codCliente, java.lang.String password, java.lang.String NDs) throws java.rmi.RemoteException;
}
