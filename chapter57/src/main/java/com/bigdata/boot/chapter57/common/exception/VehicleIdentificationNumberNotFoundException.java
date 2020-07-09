package com.bigdata.boot.chapter57.common.exception;

import com.bigdata.boot.chapter57.domain.VehicleIdentificationNumber;

/**
 * Exception thrown when a {@link VehicleIdentificationNumber} is not found.
 *
 * @author Phillip Webb
 */
public class VehicleIdentificationNumberNotFoundException extends RuntimeException {

	private final VehicleIdentificationNumber vehicleIdentificationNumber;

	public VehicleIdentificationNumberNotFoundException(VehicleIdentificationNumber vin) {
		this(vin, null);
	}

	public VehicleIdentificationNumberNotFoundException(VehicleIdentificationNumber vin,
			Throwable cause) {
		super("Unable to find VehicleIdentificationNumber " + vin, cause);
		this.vehicleIdentificationNumber = vin;
	}

	public VehicleIdentificationNumber getVehicleIdentificationNumber() {
		return this.vehicleIdentificationNumber;
	}

}