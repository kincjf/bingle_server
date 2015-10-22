package com.leanstacks.ws.web.api;

import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.AbstractControllerTest;

/**
 * Unit tests for the ImageStitchController using Mockito mocks and spies.
 * 
 * These tests utilize the Mockito framework objects to simulate interaction
 * with back-end components. The controller methods are invoked directly
 * bypassing the Spring MVC mappings. Back-end components are mocked and
 * injected into the controller. Mockito spies and verifications are performed
 * ensuring controller behaviors.
 * 
 * @author KIMSEONHO
 */
@Transactional
public class ImageStitchControllerMocksTest extends AbstractControllerTest {

	/**
	 * A GreetingController instance with <code>@Mock</code> components injected
	 * into it.
	 */
	@InjectMocks
	private ImageStitchController imageStitchController;

	/**
	 * Setup each test method. Initialize Mockito mock and spy objects. Scan for
	 * Mockito annotations.
	 */
	@Before
	public void setUp() {
		// Initialize Mockito annotated components
		MockitoAnnotations.initMocks(this);
		// Prepare the Spring MVC Mock components for standalone testing
		setUp(imageStitchController);
	}

	@Test
	public void testGetImageUrl() throws Exception {
		// Perform the behavior being tested
		String uri = "/upload";
		// String uploadFilePath = File.separator + "resource" + File.separator
		// + "2015092120583295.zip";
		String uploadFilePath = "src/test/resource/2015092120583295.zip";

		// ClassLoader classLoader = getClass().getClassLoader();

		// logger.info("> testGetImageUrl",
		// classLoader.getResource(uploadFilePath).getFile());

		FileInputStream fis = new FileInputStream(uploadFilePath);
		MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.fileUpload(uri).file(multipartFile)
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		logger.info("> testGetImageUrl", content);
		
		// Perform standard JUnit assertions on the response
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}
}
