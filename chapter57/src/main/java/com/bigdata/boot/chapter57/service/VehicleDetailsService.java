package com.bigdata.boot.chapter57.service;

import com.bigdata.boot.chapter57.common.exception.VehicleIdentificationNumberNotFoundException;
import com.bigdata.boot.chapter57.domain.VehicleDetails;
import com.bigdata.boot.chapter57.domain.VehicleIdentificationNumber;

/**
 * A service to obtain {@link VehicleDetails} given a {@link VehicleIdentificationNumber}.
 *
 * @author Phillip Webb
 */
public interface VehicleDetailsService {

	/**
	 * Get vehicle details for a given {@link VehicleIdentificationNumber}.
	 * @param vin the vehicle identification number
	 * @return vehicle details
	 * @throws VehicleIdentificationNumberNotFoundException if the VIN is not known
	 */
	VehicleDetails getVehicleDetails(VehicleIdentificationNumber vin)
			throws VehicleIdentificationNumberNotFoundException;

}