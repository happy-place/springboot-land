package com.bigdata.boot.chapter57.service;

import com.bigdata.boot.chapter57.common.exception.UserNameNotFoundException;
import com.bigdata.boot.chapter57.common.exception.VehicleIdentificationNumberNotFoundException;
import com.bigdata.boot.chapter57.domain.VehicleDetails;

public interface UserVehicleService {

    VehicleDetails getVehicleDetails(String username) throws UserNameNotFoundException, VehicleIdentificationNumberNotFoundException;

}
