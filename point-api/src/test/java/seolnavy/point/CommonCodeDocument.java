package seolnavy.point;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import seolnavy.point.restdocs.ControllerTestSupport;

@DisplayName("공통코드")
public class CommonCodeDocument extends ControllerTestSupport {

	@Test
	@DisplayName("상태코드")
	void rowStsCd() throws Exception {
		mockMvc.perform(
						get("/test/common/pointHistoryResponseType")
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk());
	}

}
