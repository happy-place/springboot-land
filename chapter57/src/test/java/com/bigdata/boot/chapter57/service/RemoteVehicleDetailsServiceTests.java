package com.bigdata.boot.chapter57.service;

import com.bigdata.boot.chapter57.common.config.ServiceProperties;
import com.bigdata.boot.chapter57.common.exception.VehicleIdentificationNumberNotFoundException;
import com.bigdata.boot.chapter57.domain.VehicleDetails;
import com.bigdata.boot.chapter57.domain.VehicleIdentificationNumber;
import com.bigdata.boot.chapter57.service.impl.RemoteVehicleDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests for {@link RemoteVehicleDetailsService}.
 *
 * @author Phillip Webb
 */
@RunWith(SpringRunner.class)
@RestClientTest({ RemoteVehicleDetailsService.class, ServiceProperties.class })
public class RemoteVehicleDetailsServiceTests {

	private static final String VIN = "00000000000000000";

	@Autowired
	private RemoteVehicleDetailsService service;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void getVehicleDetailsWhenVinIsNullShouldThrowException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.service.getVehicleDetails(null))
				.withMessage("VIN must not be null");
	}

	@Test
	public void getVehicleDetailsWhenResultIsSuccessShouldReturnDetails() {
		this.server.expect(requestTo("/vehicle/" + VIN + "/details"))
				.andRespond(withSuccess(getClassPathResource("vehicledetails.json"),
						MediaType.APPLICATION_JSON));
		VehicleDetails details = this.service
				.getVehicleDetails(new VehicleIdentificationNumber(VIN));
		assertThat(details.getMake()).isEqualTo("Honda");
		assertThat(details.getModel()).isEqualTo("Civic");
	}

	@Test
	public void getVehicleDetailsWhenResultIsNotFoundShouldThrowException() {
		this.server.expect(requestTo("/vehicle/" + VIN + "/details"))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));
		assertThatExceptionOfType(VehicleIdentificationNumberNotFoundException.class)
				.isThrownBy(() -> this.service
						.getVehicleDetails(new VehicleIdentificationNumber(VIN)));
	}

	@Test
	public void getVehicleDetailsWhenResultIServerErrorShouldThrowException() {
		this.server.expect(requestTo("/vehicle/" + VIN + "/details"))
				.andRespond(withServerError());
		assertThatExceptionOfType(HttpServerErrorException.class)
				.isThrownBy(() -> this.service
						.getVehicleDetails(new VehicleIdentificationNumber(VIN)));
	}

	private ClassPathResource getClassPathResource(String path) {
		return new ClassPathResource(path, getClass());
	}

}