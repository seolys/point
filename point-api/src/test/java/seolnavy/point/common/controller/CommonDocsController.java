package seolnavy.point.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/docs")
public class CommonDocsController {
	
	@GetMapping("/internalServerError")
	public void internalServerError() {
		throw new RuntimeException("INTERNAL_SERVER_ERROR 관련 메시지");
	}

	@GetMapping("/badRequest")
	public void badRequest() {
		throw new IllegalArgumentException("BAD_REQUEST 관련 메시지");
	}

}
