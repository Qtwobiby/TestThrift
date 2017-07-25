package com.micmiu.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServerDemo {

	public static final int SERVER_PORT = 8090;
	
	public void startServer() {
		System.out.println("HelloWorld TSimpleServer start ...");
		TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldImpl>(new HelloWorldImpl());
		try {
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TServer.Args tArgs = new TServer.Args(serverTransport);
			tArgs.processor(tProcessor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			TServer server = new TSimpleServer(tArgs);
			server.serve();
		} catch (TTransportException e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		HelloServerDemo server = new HelloServerDemo();
		server.startServer();
	}
}
