package com.company.challenge.helper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UUIDUnitTest {

	@Test
	public void UUIDFormatIsValid() {
		String uuid = UUIDGen.getUUID();
		assertTrue(uuid.matches("([a-f]|\\d){32}"));
	}

	@Test
	public void UUIDIsRandom() {
		String uuid1 = UUIDGen.getUUID();
		String uuid2 = UUIDGen.getUUID();
		assertNotNull(uuid1);
		assertTrue(uuid1.matches("([a-f]|\\d){32}"));
		assertNotNull(uuid2);
		assertTrue(uuid2.matches("([a-f]|\\d){32}"));
		assertThat(uuid1,not(equalTo(uuid2)));
	}
	
}
