package org.owasp.goatdroid.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lessons")
public class LessonController {

	@RequestMapping(value = "/")
	public String index() {
		return "/lessons/index";
	}
}
