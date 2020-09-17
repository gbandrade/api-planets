package br.com.planets.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Api Planets.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private final Api api = new Api();
	
	public static class Api {
		private String path;
		private Endpoint endpoint;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Endpoint getEndpoint() {
			return endpoint;
		}

		public void setEndpoint(Endpoint endpoint) {
			this.endpoint = endpoint;
		}
	}
	
	public static class Endpoint {
		private String getPlanets;

		public String getGetPlanets() {
			return getPlanets;
		}

		public void setGetPlanets(String getPlanets) {
			this.getPlanets = getPlanets;
		}
	}

	public Api getApi() {
		return api;
	}

}
