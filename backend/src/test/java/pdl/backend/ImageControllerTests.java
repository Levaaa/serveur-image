package pdl.backend;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.http.MediaType;
import java.nio.file.Files;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;



@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ImageControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void reset() {
	   // reset Image class static counter
	   ReflectionTestUtils.setField(Image.class, "count", Long.valueOf(0));
	}

	@Test
	@Order(1)
	public void getImageListShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/images")).andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void getImageShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(get("/images/50000")).andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	public void getImageShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/images/0")).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void deleteImageShouldReturnBadRequest() throws Exception {
		this.mockMvc.perform(delete("/images/bad_args")).andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void deleteImageShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(delete("/fail")).andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	public void createImageShouldReturnSuccess() throws Exception {
		final ClassPathResource imgFile = new ClassPathResource("test.jpg");
    	byte[] fileContent = Files.readAllBytes(imgFile.getFile().toPath());
		MockMultipartFile file = new MockMultipartFile("file","test.jpg", MediaType.IMAGE_JPEG_VALUE, fileContent);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/images").file(file);
		this.mockMvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	@Order(7)
	public void createImageShouldReturnUnsupportedMediaType() throws Exception {
		final ClassPathResource imgFile = new ClassPathResource("test.png");
    	byte[] fileContent = Files.readAllBytes(imgFile.getFile().toPath());
		MockMultipartFile file = new MockMultipartFile("file","test.png", MediaType.IMAGE_PNG_VALUE, fileContent);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/images").file(file);
		this.mockMvc.perform(builder).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	@Order(8)
	public void getImageWithAlgorithmWithoutArgShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/images/0?algorithm=contrast")).andExpect(status().isOk());
	}

	@Test
	@Order(9)
	public void getImageWithWrongAlgorithmShouldReturnBedRequest() throws Exception {
		this.mockMvc.perform(get("/images/0?algorithm=salut")).andExpect(status().isBadRequest());
	}

	@Test
	@Order(10)
	public void getImageWithAlgorithmWithWrongParamShouldReturnBedRequest() throws Exception {
		this.mockMvc.perform(get("/images/0?algorithm=contrast&p1=50")).andExpect(status().isBadRequest());
	}

	@Test
	@Order(11)
	public void getImageWithAlgorithmWithUnvalidParamShouldReturnBedRequest() throws Exception {
		this.mockMvc.perform(get("/images/0?algorithm=brightness&p1=500")).andExpect(status().isBadRequest());
	}

	@Test
	@Order(12)
	public void getImageWithAlgorithmWithWrongIdShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(get("/images/50000?algorithm=contrast")).andExpect(status().isNotFound());
	}

	@Test
	@Order(13)
	public void getImageWithAlgorithmWithArgShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/images/0?algorithm=brightness&p1=50")).andExpect(status().isOk());
	}

	@Test
	@Order(14)
	public void deleteImageShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(delete("/images/0")).andExpect(status().isOk());
	}


}
