package com.micmiu.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServerDemo2 {

	public static final int SERVER_PORT = 8090;
	
	public void startServer() {
		System.out.println("HelloWorld TThreadPoolServer start ...");
		TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldImpl>(new HelloWorldImpl());
		try {
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
			ttpsArgs.processor(tProcessor);
			ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
			
			TServer server = new TThreadPoolServer(ttpsArgs);
			server.serve();
		} catch (TTransportException e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HelloServerDemo2 server = new HelloServerDemo2();
		server.startServer();
	}
}
