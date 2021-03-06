/*******************************************************************************
 * Copyright (c) 2014 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.expedia.tesla.serialization;

import static com.expedia.tesla.serialization.binary.BaseFixture.concat;

import java.util.Arrays;
import java.util.List;

import com.expedia.tesla.utils.Unsigned;

/**
 * Test fixture values for UInt32 tests.
 * 
 * @author dheld
 */
public interface UInt32Fixture {
	static final long MIN_VALUE = 0;
	static final byte[] MIN_BINARY = { 0 };
	static final String MIN_JSON = "0";
	static final long MAX_VALUE = Unsigned.MAX_UINT32;
	static final byte[] MAX_BINARY = { -1, -1, -1, -1, 15 };
	static final String MAX_JSON = "4294967295";
	static final long SMALL_VALUE = 3636;
	static final byte[] SMALL_BINARY = { -76, 28 };
	static final String SMALL_JSON = "3636";
	static final long LARGE_VALUE = 3214342321L;
	static final byte[] LARGE_BINARY = { -79, -15, -37, -4, 11 };
	static final String LARGE_JSON = "3214342321";
	static final byte[] OVERSIZE_BINARY = { -110, -40, -80, -120, 55 };
	static final List<Integer> ARRAY_VALUES = Arrays.asList((int) MIN_VALUE,
			(int) MAX_VALUE, (int) SMALL_VALUE, (int) LARGE_VALUE);
	static final byte[] ARRAY_BINARY = concat((byte) ARRAY_VALUES.size(),
			MIN_BINARY, MAX_BINARY, SMALL_BINARY, LARGE_BINARY);
	static final String ARRAY_JSON = '[' + MIN_JSON + ',' + MAX_JSON + ','
			+ SMALL_JSON + ',' + LARGE_JSON + ']';
}
