package seolnavy.point.interfaces.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static seolnavy.point.restdocs.RestDocsCommonField.CODE;
import static seolnavy.point.restdocs.RestDocsCommonField.MESSAGE;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import seolnavy.point.application.PointTestSupport;
import seolnavy.point.common.response.CommonResponse;
import seolnavy.point.common.response.ResponseCode;
import seolnavy.point.domain.user.UserService;
import seolnavy.point.interfaces.controller.dto.CancelDeductPointDto;
import seolnavy.point.interfaces.controller.dto.DeductPointDto;
import seolnavy.point.interfaces.controller.dto.EarnPointDto.Request;
import seolnavy.point.restdocs.ControllerTestSupport;

@DisplayName("포인트 Command Controller")
class PointCommandControllerTest extends ControllerTestSupport {

	@Autowired private UserService userService;
	@Autowired private PointTestSupport pointTestSupport;

	@Test
	@DisplayName("회원별 포인트 적립")
	void earnPoint() throws Exception {
		// given
		final var userNo = 1L;
		final var requestDto = Request.of(UUID.randomUUID().toString(), 1000L);

		// when
		final ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/v1/points/{userNo}/earn", userNo)
						.content(objectToJsonString(requestDto))
						.contentType(MediaType.APPLICATION_JSON));

		// then
		perform
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(parameterWithName("userNo").description("회원번호")),
								requestFields(
										fieldWithPath("uuid").description("적립 UUID(중복방지 식별자)"),
										fieldWithPath("point").description("적립 포인트")
								),
								responseFields(
										CODE,
										MESSAGE
								)
						)
				);

		final var response = objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), CommonResponse.class);
		assertThat(response.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
		assertThat(response.getMessage()).isNotBlank();
	}

	@Test
	@DisplayName("회원별 포인트 사용")
	void deductPoint() throws Exception {
		// given
		final var userNo = 1L;
		pointTestSupport.earnPoint(userNo, 1000L);
		final var requestDto = DeductPointDto.Request.of(UUID.randomUUID().toString(), 1000L);
		// when
		final ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/v1/points/{userNo}/deduct", userNo)
						.content(objectToJsonString(requestDto))
						.contentType(MediaType.APPLICATION_JSON));

		// then
		perform
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(parameterWithName("userNo").description("회원번호")),
								requestFields(
										fieldWithPath("uuid").description("사용 UUID(중복방지 식별자)"),
										fieldWithPath("point").description("사용 포인트")
								),
								responseFields(
										CODE,
										MESSAGE,
										fieldWithPath("data.deductPointNo").description("포인트 차감번호")
								)
						)
				);

		final var response = objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), CommonResponse.class);
		assertThat(response.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
		assertThat(response.getMessage()).isNotBlank();

		final var dto = objectMapper.convertValue(response.getData(), DeductPointDto.Response.class);
		assertThat(dto.getDeductPointNo()).as("차감 포인트 번호").isNotNull();
	}

	@Test
	@DisplayName("회원별 포인트 사용 취소")
	void cancelDeductPoint() throws Exception {
		// given
		final var userNo = 1L;
		pointTestSupport.earnPoint(userNo, 1000L);
		final var deductPointNo = pointTestSupport.deductPoint(userNo, 1000L).getDeductPointNo();
		final var requestDto = CancelDeductPointDto.Request.of(deductPointNo);

		// when
		final ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/v1/points/{userNo}/cancel-deduct", userNo)
						.content(objectToJsonString(requestDto))
						.contentType(MediaType.APPLICATION_JSON));

		// then
		perform
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(parameterWithName("userNo").description("회원번호")),
								requestFields(
										fieldWithPath("deductPointNo").description("포인트 차감번호")
								),
								responseFields(
										CODE,
										MESSAGE
								)
						)
				);

		final var response = objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), CommonResponse.class);
		assertThat(response.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
		assertThat(response.getMessage()).isNotBlank();
	}

}