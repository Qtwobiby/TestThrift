package com.micmiu.thrift.demo;

import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

	public String sayHello(String username) throws TException {
		return "Hi, " + username + " welcome to my blog";
	}

}
