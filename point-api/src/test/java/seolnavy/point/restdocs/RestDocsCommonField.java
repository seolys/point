package seolnavy.point.restdocs;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static seolnavy.point.restdocs.RestDocsConfiguration.field;

import org.springframework.restdocs.payload.FieldDescriptor;

public class RestDocsCommonField {

	public static final FieldDescriptor CODE = fieldWithPath("code").description("결과코드");
	public static final FieldDescriptor MESSAGE = fieldWithPath("message").description("결과메시지").attributes(field("type", "String")).optional();

}
