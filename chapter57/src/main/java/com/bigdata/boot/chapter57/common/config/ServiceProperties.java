package com.bigdata.boot.chapter57.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties for the service.
 *
 * @author Phillip Webb
 */
@Component
@ConfigurationProperties
public class ServiceProperties {

	private String vehicleServiceRootUrl = "http://localhost:8080/vs";

	public String getVehicleServiceRootUrl() {
		return this.vehicleServiceRootUrl;
	}

	public void setVehicleServiceRootUrl(String vehicleServiceRootUrl) {
		this.vehicleServiceRootUrl = vehicleServiceRootUrl;
	}

}