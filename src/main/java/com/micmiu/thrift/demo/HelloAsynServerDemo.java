package com.micmiu.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TNonblockingServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloAsynServerDemo {

	public static final int SERVER_PORT = 8091;
	
	public void startServer() {
		System.out.println("HelloWorld TNonblockingServer start ...");
		
		TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldImpl>(new HelloWorldImpl());
		try {
			TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
			TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
			tnbArgs.processor(tprocessor);
			tnbArgs.transportFactory(new TFramedTransport.Factory());
			tnbArgs.protocolFactory(new TCompactProtocol.Factory());
			
			TServer server = new TNonblockingServer(tnbArgs);
			server.serve();
			
		} catch (TTransportException e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		HelloAsynServerDemo server = new HelloAsynServerDemo();
		server.startServer();
	}
}
