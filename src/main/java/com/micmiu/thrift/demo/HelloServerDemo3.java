package com.micmiu.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServerDemo3 {

	public static final int SERVER_PORT = 8090;
	
	public void startServer() {
		System.out.println("HelloWorld TNonblockingServer start ...");
		TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldImpl>(new HelloWorldImpl());
		
		try {
			TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
			TNonblockingServer.Args tnArgs = new TNonblockingServer.Args(tnbSocketTransport);
			
			tnArgs.processor(tProcessor);
			tnArgs.transportFactory(new TFramedTransport.Factory());
			tnArgs.protocolFactory(new TCompactProtocol.Factory());
			
			TServer server = new TNonblockingServer(tnArgs);
			server.serve();
		} catch (TTransportException e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		HelloServerDemo3 server = new HelloServerDemo3();
		server.startServer();
	}
}
