package seolnavy.point.interfaces.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static seolnavy.point.restdocs.RestDocsCommonField.CODE;
import static seolnavy.point.restdocs.RestDocsCommonField.MESSAGE;
import static seolnavy.point.restdocs.RestDocsConfiguration.field;

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
import seolnavy.point.interfaces.controller.dto.GetUserPointHistoryDto;
import seolnavy.point.interfaces.controller.dto.GetUserRemainPointDto;
import seolnavy.point.restdocs.ControllerTestSupport;

@DisplayName("포인트 Query Controller")
class PointQueryControllerTest extends ControllerTestSupport {

	@Autowired private UserService userService;
	@Autowired private PointTestSupport pointTestSupport;

	@Test
	@DisplayName("회원별 포인트 조회")
	void getUserRemainPoint() throws Exception {
		// given
		final var userNo = 1L;
		final var remainPoint = userService.getRemainPoint(userNo);

		// when
		final ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/v1/points/{userNo}", userNo)
						.contentType(MediaType.APPLICATION_JSON));

		// then
		perform
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(parameterWithName("userNo").description("회원번호")),
								responseFields(
										CODE,
										MESSAGE,
										fieldWithPath("data.remainPoint").description("잔여 포인트")
								)
						)
				);

		final var response = objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), CommonResponse.class);
		assertThat(response.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
		assertThat(response.getMessage()).isNotBlank();

		final var dto = objectMapper.convertValue(response.getData(), GetUserRemainPointDto.Response.class);
		assertThat(dto.getRemainPoint()).as("잔여 포인트").isEqualTo(remainPoint);
	}

	@Test
	@DisplayName("회원별 포인트 적립/사용내역 조회")
	void getUserPointHistory() throws Exception {
		// given
		final var userNo = 1L;
		pointTestSupport.earnPoint(userNo, 1000L);

		// when
		final ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/v1/points/{userNo}/history", userNo)
						.contentType(MediaType.APPLICATION_JSON));

		// then
		perform
				.andExpect(status().isOk())
				.andDo(
						restDocs.document(
								pathParameters(parameterWithName("userNo").description("회원번호")),
								responseFields(
										CODE,
										MESSAGE,
										fieldWithPath("data.history[0].pointHistoryNo").description("포인트내역번호"),
										fieldWithPath("data.history[0].historyType").description("내역구분"),
										fieldWithPath("data.history[0].point").description("포인트"),
										fieldWithPath("data.history[0].createdDate").description("등록일자").attributes(field("format", "2022-10-03T22:05:01.106881"))
								)
						)
				);

		final var response = objectMapper.readValue(perform.andReturn().getResponse().getContentAsString(), CommonResponse.class);
		assertThat(response.getCode()).isEqualTo(ResponseCode.SUCCESS.getCode());
		assertThat(response.getMessage()).isNotBlank();

		final var dto = objectMapper.convertValue(response.getData(), GetUserPointHistoryDto.Response.class);
		assertThat(dto.getHistory()).isNotNull();

		final var pointHistoryDetail = dto.getHistory().get(0);
		assertThat(pointHistoryDetail.getPointHistoryNo()).isNotNull();
		assertThat(pointHistoryDetail.getHistoryType()).isNotNull();
		assertThat(pointHistoryDetail.getPoint()).isNotNull();
		assertThat(pointHistoryDetail.getCreatedDate()).isNotNull();
	}

}