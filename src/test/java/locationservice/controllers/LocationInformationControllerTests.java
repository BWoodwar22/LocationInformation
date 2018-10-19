package locationservice.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import locationservice.LocationInformationManager;
import locationservice.controllers.LocationInformationController;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationInformationController.class)
public class LocationInformationControllerTests {
    
	@Autowired
    private MockMvc mvc;
    
    @MockBean
    private LocationInformationManager manager;
 
    @Before
    public void setup() {
    }

    @Test
    public void testGetLocationDataFake() throws Exception {
    	String locationString = "Location 11111";
        given(manager.getMessage("11111")).willReturn(locationString);

        MockHttpServletResponse response = mvc.perform(
        		get("/zip-code/11111")
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn().getResponse();
 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(locationString);
    }
    
    @Test
    public void testGetLocationDataBadZipCode() throws Exception {

        given(manager.getMessage("1111")).willReturn(null);

        MockHttpServletResponse response = mvc.perform(
        		get("/zip-code/1111")
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn().getResponse();
 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }
}
