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

package com.expedia.tesla.serialization.binary;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.junit.Before;

import com.expedia.tesla.SchemaVersion;
import com.expedia.tesla.serialization.BinaryReader;
import com.expedia.tesla.serialization.BinaryWriter;
import com.expedia.tesla.serialization.TeslaConstants;
import com.expedia.tesla.serialization.TeslaReader;
import com.expedia.tesla.serialization.TeslaWriter;

/**
 * Fixture base for binary serialization/deserialization tests.
 * 
 * @author dheld
 */
public class BaseFixture {

	public static final SchemaVersion SCHEMA_VERSION = new SchemaVersion((short) 13);
	public static final byte[] EMPTY_STREAM = {};
	public static final byte[] BINARY_FALSE = { TeslaConstants.BOOLEAN_FALSE };
	public static final byte[] BINARY_TRUE = { TeslaConstants.BOOLEAN_TRUE };
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	private final BinaryWriter writer = new BinaryWriter(output, SCHEMA_VERSION);
	private final OutputStream throwStream = mock(OutputStream.class);
	private final BinaryWriter throwWriter = new BinaryWriter(throwStream, SCHEMA_VERSION);

	public static byte[] concat(byte value, byte[]... tail) {
		final byte[] head = { value };
		return concat(head, tail);
	}

	public static byte[] concat(byte[] head, byte tail) {
		return concat(head, concat(tail, new byte[0]));
	}

	public static byte[] concat(byte[] head, byte[]... tail) {
		if (head == null) {
			return null;
		}

		int length = head.length;
		for (final byte[] array : tail) {
			if (array == null) {
				return null;
			}
			length += array.length;
		}

		final byte[] result = Arrays.copyOf(head, length);
		int offset = head.length;
		for (final byte[] array : tail) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		return result;
	}

	public BaseFixture() {
		try {
			doThrow(new IOException("Test")).when(throwStream).write(
			any(byte[].class));
			doThrow(new IOException("Test")).when(throwStream).write(
			any(byte[].class), anyInt(), anyInt());
			doThrow(new IOException("Test")).when(throwStream).write(anyByte());
			doThrow(new IOException("Test")).when(throwStream).write(anyInt());
			doThrow(new IOException("Test")).when(throwStream).flush();
		} catch (IOException e) {
			throw new RuntimeException("Error creating mock", e);
		}
	}

	@Before
	public void setUp() {
		this.output.reset();
	}

	protected TeslaWriter getWriter() {
		return this.writer;
	}

	protected TeslaWriter getThrowWriter() {
		return this.throwWriter;
	}

	protected TeslaReader getReader(byte[] buffer) {
		return new BinaryReader(buffer, 0, buffer.length, SCHEMA_VERSION);
	}

	protected OutputStream getOutputStream() {
		return this.output;
	}

	protected byte[] getOutput() {
		return this.output.toByteArray();
	}

}
