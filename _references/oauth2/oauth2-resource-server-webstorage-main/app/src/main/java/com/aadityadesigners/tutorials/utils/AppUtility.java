package com.aadityadesigners.tutorials.utils;

import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import java.net.InetAddress;

public class AppUtility {

	private static final Logger LOGGER = LogManager.getLogger(AppUtility.class);

	public static String getHost() throws Exception {
		String host;
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			LOGGER.error("Error fetching hostname.");
			throw e;
		}

		return host;
	}

	public static int getServerPortFromCloud() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonContent = restTemplate.getForObject(
				"https://raw.githubusercontent.com/aakashkumar1980/apps-configs/main/SERVER-PORTS.json", 
				String.class
			);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(jsonContent);
			return rootNode.get(0).get("OAUTH2_APPLICATION").get("RESOURCE_SERVER_PORT").asInt();
		} catch (Exception e) {
			// Handle exception
			e.printStackTrace();
			return -1;
		}
	}

}