package seolnavy.point;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static seolnavy.point.restdocs.RestDocsCommonField.CODE;
import static seolnavy.point.restdocs.RestDocsCommonField.MESSAGE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seolnavy.point.restdocs.ControllerTestSupport;

@DisplayName("공통 응답코드")
public class CommonDocsDocument extends ControllerTestSupport {

	@Test
	@DisplayName("500 에러")
	void internalServerError() throws Exception {
		mockMvc.perform(
						get("/test/docs/internalServerError")
				)
				.andExpect(status().is5xxServerError())
				.andDo(
						restDocs.document(
								responseFields(
										CODE,
										MESSAGE
								)
						)
				);
	}

	@Test
	@DisplayName("400 에러")
	void badReqeust() throws Exception {
		mockMvc.perform(
						get("/test/docs/badRequest")
				)
				.andExpect(status().is4xxClientError())
				.andDo(
						restDocs.document(
								responseFields(
										CODE,
										MESSAGE
								)
						)
				);
	}
}
