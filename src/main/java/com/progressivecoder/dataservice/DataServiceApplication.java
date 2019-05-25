package com.progressivecoder.dataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
public class DataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataServiceApplication.class, args);
	}

}

@RestController
class ServiceDiscoveryController{

	@Autowired
	private DiscoveryClient discoveryClient;


	@GetMapping("/services")
	public Optional<URI> serviceURL(){
		return discoveryClient.getInstances("data-service")
				.stream()
				.map(instance -> instance.getUri())
				.findFirst();
	}

}

@RestController
class ExternalConfigurationController{

	@Value("${test.msg}")
	String value;

	@GetMapping("/getConfigValue")
	public String getConfigValue(){
		return value;
	}

}

