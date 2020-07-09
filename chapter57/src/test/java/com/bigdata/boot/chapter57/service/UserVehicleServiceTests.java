package com.bigdata.boot.chapter57.service;

import com.bigdata.boot.chapter57.common.exception.UserNameNotFoundException;
import com.bigdata.boot.chapter57.dao.UserRepository;
import com.bigdata.boot.chapter57.domain.User;
import com.bigdata.boot.chapter57.domain.VehicleDetails;
import com.bigdata.boot.chapter57.domain.VehicleIdentificationNumber;
import com.bigdata.boot.chapter57.service.impl.UserVehicleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link UserVehicleService}.
 *
 * @author Phillip Webb
 */
public class UserVehicleServiceTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"00000000000000000");

	@Mock
	private VehicleDetailsService vehicleDetailsService;

	@Mock
	private UserRepository userRepository;

    @MockBean
	private UserVehicleService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.service = new UserVehicleServiceImpl(userRepository,vehicleDetailsService);
	}

	@Test
	public void getVehicleDetailsWhenUsernameIsNullShouldThrowException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.service.getVehicleDetails(null))
				.withMessage("Username must not be null");
	}

	@Test
	public void getVehicleDetailsWhenUsernameNotFoundShouldThrowException() {
		given(this.userRepository.findByUsername(anyString())).willReturn(null);
		assertThatExceptionOfType(UserNameNotFoundException.class)
				.isThrownBy(() -> this.service.getVehicleDetails("sboot"));
	}

	@Test
	public void getVehicleDetailsShouldReturnMakeAndModel() {
		given(this.userRepository.findByUsername(anyString()))
				.willReturn(new User("sboot", VIN));
		VehicleDetails details = new VehicleDetails("Honda", "Civic");
		given(this.vehicleDetailsService.getVehicleDetails(VIN)).willReturn(details);
		VehicleDetails actual = this.service.getVehicleDetails("sboot");
		assertThat(actual).isEqualTo(details);
	}

}