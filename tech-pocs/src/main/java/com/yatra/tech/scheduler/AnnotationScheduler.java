package com.yatra.tech.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AnnotationScheduler {

	@Scheduled(fixedDelay = 1 * 1000)
	public void annotationScheduler() {
		System.out.println("Rahul Dev Vardhan : " + System.currentTimeMillis());
	}
}
