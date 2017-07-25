package com.micmiu.thrift.demo;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloClientDemo3 {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8090;
	public static final int TIMEOUT = 30000;
	
	public void startClient(String userName) {
		TTransport transport = null;
		
		transport = new TFramedTransport(new TSocket(SERVER_IP,  SERVER_PORT, TIMEOUT));
		//协议要和服务端一致
		TProtocol protocol = new TCompactProtocol(transport);
		
		HelloWorldService.Client client = new HelloWorldService.Client(protocol);
		try {
			transport.open();
			String result = client.sayHello(userName);
			System.out.println("Thrift client result =: " + result);
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
		
	}
	
	public static void main(String[] args) {
		HelloClientDemo3 client = new HelloClientDemo3();
		client.startClient("sunshine");
	}
}
