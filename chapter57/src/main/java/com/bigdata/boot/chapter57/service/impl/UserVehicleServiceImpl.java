package com.bigdata.boot.chapter57.service.impl;

import com.bigdata.boot.chapter57.common.exception.UserNameNotFoundException;
import com.bigdata.boot.chapter57.common.exception.VehicleIdentificationNumberNotFoundException;
import com.bigdata.boot.chapter57.dao.UserRepository;
import com.bigdata.boot.chapter57.domain.User;
import com.bigdata.boot.chapter57.domain.VehicleDetails;

import com.bigdata.boot.chapter57.service.UserVehicleService;
import com.bigdata.boot.chapter57.service.VehicleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Controller service used to provide vehicle information for a given user.
 *
 * @author Phillip Webb
 */
@Service
public class UserVehicleServiceImpl implements UserVehicleService {

    @Autowired
	private UserRepository userRepository;

    @Autowired
	private VehicleDetailsService vehicleDetailsService;

    public UserVehicleServiceImpl(UserRepository userRepository,
                                  VehicleDetailsService vehicleDetailsService){
        this.userRepository=userRepository;
        this.vehicleDetailsService=vehicleDetailsService;
    }

	@Override
    public VehicleDetails getVehicleDetails(String username)
			throws UserNameNotFoundException,
            VehicleIdentificationNumberNotFoundException {
		Assert.notNull(username, "Username must not be null");
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new UserNameNotFoundException(username);
		}
		return this.vehicleDetailsService.getVehicleDetails(user.getVin());
	}

}