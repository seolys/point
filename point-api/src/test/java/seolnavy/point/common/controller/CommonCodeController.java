package seolnavy.point.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.EnumSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seolnavy.point.interfaces.controller.dto.GetUserPointHistoryDto.PointHistoryResponseType;

@RestController
@RequestMapping("/test/common")
public class CommonCodeController {

	@Autowired private ObjectMapper objectMapper;

	@GetMapping("/pointHistoryResponseType")
	public ArrayNode pointHistoryResponseType() {
		final ArrayNode arrayNode = objectMapper.createArrayNode();
		for (final var type : EnumSet.allOf(PointHistoryResponseType.class)) {
			final ObjectNode objectNode = objectMapper.createObjectNode();
			objectNode.put("rowStsCd", type.name());
			objectNode.put("description", type.getDescription());
			arrayNode.add(objectNode);
		}
		return arrayNode;
	}
	
}
