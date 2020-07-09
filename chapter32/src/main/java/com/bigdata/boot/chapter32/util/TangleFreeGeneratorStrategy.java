package com.bigdata.boot.chapter32.util;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

/**
 * Custom {@link DefaultGeneratorStrategy} that doesn't produce tangled packages. Too
 * simple for general use but solves false flags for our build.
 */
public class TangleFreeGeneratorStrategy extends DefaultGeneratorStrategy {

	@Override
	public String getJavaPackageName(Definition definition, Mode mode) {
		return getTargetPackage();
	}

}