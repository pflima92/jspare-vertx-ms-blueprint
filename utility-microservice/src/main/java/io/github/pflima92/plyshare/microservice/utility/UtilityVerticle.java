package io.github.pflima92.plyshare.microservice.utility;

import io.github.pflima92.plyshare.common.MicroserviceVerticle;
import io.github.pflima92.plyshare.microservice.utility.api.UtilityAPIVerticle;

public class UtilityVerticle extends MicroserviceVerticle {
	
	@Override
	protected void initialize() {
		
		publishEventBusServiceEndpoint(NotificationService.NAME, NotificationService.ADDRESS, NotificationService.class);
		deployVerticle(UtilityAPIVerticle.class);
	}
}