package com.micmiu.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServerDemo4 {
	
	public static final int SERVER_PORT = 8090;
	
	public void startServer() {
		System.out.println("HelloWorld THsHaServer start ...");
		TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldImpl>(new HelloWorldImpl());
		try {
			TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
			THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
			thhsArgs.processor(tprocessor);
			thhsArgs.transportFactory(new TFramedTransport.Factory());
			thhsArgs.protocolFactory(new TBinaryProtocol.Factory());
			TServer server = new THsHaServer(thhsArgs);
			server.serve();
		} catch (TTransportException e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		HelloServerDemo4 server = new HelloServerDemo4();
		server.startServer();
	}

}
