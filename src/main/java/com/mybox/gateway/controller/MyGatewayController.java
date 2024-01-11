package com.mybox.gateway.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/my_gateway")
public class MyGatewayController {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Environment env;
	
	@RequestMapping(method = RequestMethod.GET, value = "/data")
	public String getData() {
		System.out.println("Returning data from MY GATEWAY Service getData method");
		return "Hello from MY GATEWAY data method";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/gw_post_msg")
	public String postGwMsg(@RequestBody String msg) {
		System.out.println("Returning POST data from MY GATEWAY Service - postGwMsg method");
		return( msg );
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ms1data")
	public String getMs1Data() {
		System.out.println("Got data through GATEWAY - ms1data method");
		try {
			String msEndpoint = env.getProperty("endpoint.ms1-service");
			System.out.println("MS 1 Endpoint name : [" + msEndpoint + "]");
			
			return restTemplate.getForObject(new URI(msEndpoint), String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Exception occurred.. so, returning default data";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ms2data")
	public String getMs2Data() {
		System.out.println("Got data through GATEWAY - ms2data method");
		try {
			String msEndpoint = env.getProperty("endpoint.ms2-service");
			System.out.println("MS 2 Endpoint name : [" + msEndpoint + "]");
			
			return restTemplate.getForObject(new URI(msEndpoint), String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Exception occurred.. so, returning default data";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/greeting")
	public String getMs2Greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		System.out.println("Got MS2 Greeting through GATEWAY - greeting method");
		try {
			String msEndpoint = env.getProperty("endpoint.ms2-greeting") + "?name=" + name;
			System.out.println("MS 2 Endpoint name : [" + msEndpoint + "]");
			
			return restTemplate.getForObject(new URI(msEndpoint), String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Exception occurred.. so, returning default data";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ms2sendmsg")
	public String postMs2Msg(@RequestBody String msg) {
		System.out.println("Send Message to MS 2 through GATEWAY - postMs2Msg method");
		try {
			String msEndpoint = env.getProperty("endpoint.ms2-sendmsg");
			System.out.println("MS 2 Endpoint name : [" + msEndpoint + "]");
			
			return restTemplate.postForObject(new URI(msEndpoint), msg, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Exception occurred.. so, returning default data";
	}

}
