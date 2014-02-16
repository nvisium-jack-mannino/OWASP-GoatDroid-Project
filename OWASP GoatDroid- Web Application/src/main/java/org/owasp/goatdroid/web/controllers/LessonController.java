package org.owasp.goatdroid.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lessons")
public class LessonController {

	@RequestMapping(value = "")
	public String index() {
		return "/lessons/home";
	}

	@RequestMapping(value = "/developer-track")
	public String developerTrack() {
		return "/lessons/developer-track";
	}

	@RequestMapping(value = "/pentester-track")
	public String pentesterTrack() {
		return "/lessons/pentester-track";
	}
}
