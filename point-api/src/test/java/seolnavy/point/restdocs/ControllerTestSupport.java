package seolnavy.point.restdocs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Import(RestDocsConfiguration.class)
public class ControllerTestSupport {

	@Autowired protected MockMvc mockMvc;
	@Autowired protected RestDocumentationResultHandler restDocs;
	@Autowired protected ObjectMapper objectMapper;
	@Autowired protected EntityManager entityManager;


	@BeforeEach
	void setUp(
			final WebApplicationContext context,
			final RestDocumentationContextProvider provider
	) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(provider).uris().withPort(8500))
				.alwaysDo(MockMvcResultHandlers.print())
				.alwaysDo(restDocs)
				.build();
	}

	protected String objectToJsonString(final Object value) throws IOException {
		return objectMapper.writeValueAsString(value);
	}
}
