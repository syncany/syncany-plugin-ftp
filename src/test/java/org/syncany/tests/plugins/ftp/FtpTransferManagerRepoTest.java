/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011-2014 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.syncany.tests.plugins.ftp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syncany.plugins.Plugins;
import org.syncany.plugins.ftp.FtpTransferPlugin;
import org.syncany.plugins.ftp.FtpTransferSettings;
import org.syncany.plugins.transfer.StorageException;
import org.syncany.plugins.transfer.StorageTestResult;

/**
 * @author Vincent Wiencek <vwiencek@gmail.com>
 * @author Phililpp Heckel <philipp.heckel@gmail.com>
 */
public class FtpTransferManagerRepoTest {
	@BeforeClass
	public static void beforeTestSetup() throws Exception {
		EmbeddedTestFtpServer.startServer();

		EmbeddedTestFtpServer.mkdir("emptyFolder", EmbeddedTestFtpServer.USER1);

		EmbeddedTestFtpServer.mkdir("validRepo", EmbeddedTestFtpServer.USER1);
		EmbeddedTestFtpServer.createTestFile("validRepo/syncany", EmbeddedTestFtpServer.USER1);

		EmbeddedTestFtpServer.mkdir("canNotCreate", EmbeddedTestFtpServer.USER2);
	}

	@AfterClass
	public static void stop() {
		EmbeddedTestFtpServer.stopServer();
	}

	@Test
	public void testFtpTransferManagerEmptyFolderTestCreateTarget() throws StorageException {
		StorageTestResult testResult = test("/emptyFolder", true);

		assertTrue(testResult.isTargetCanConnect());
		assertTrue(testResult.isTargetExists());
		assertTrue(testResult.isTargetCanCreate());
		assertTrue(testResult.isTargetCanWrite());
		assertFalse(testResult.isRepoFileExists());
	}

	@Test
	public void testFtpTransferManagerEmptyFolderNoTestCreateTarget() throws StorageException {
		StorageTestResult testResult = test("/emptyFolder", false);

		assertTrue(testResult.isTargetCanConnect());
		assertTrue(testResult.isTargetExists());
		assertTrue(testResult.isTargetCanCreate());
		assertTrue(testResult.isTargetCanWrite());
		assertFalse(testResult.isRepoFileExists());
	}

	@Test
	public void testFtpTransferManagerValidRepoTestCreateTarget() throws StorageException {
		StorageTestResult testResult = test("/validRepo", true);

		assertTrue(testResult.isTargetCanConnect());
		assertTrue(testResult.isTargetExists());
		assertTrue(testResult.isTargetCanCreate());
		assertTrue(testResult.isTargetCanWrite());
		assertTrue(testResult.isRepoFileExists());
	}

	@Test
	public void testFtpTransferManagerValidRepoNoTestCreateTarget() throws StorageException {
		StorageTestResult testResult = test("/validRepo", false);

		assertTrue(testResult.isTargetCanConnect());
		assertTrue(testResult.isTargetExists());
		assertTrue(testResult.isTargetCanCreate());
		assertTrue(testResult.isTargetCanWrite());
		assertTrue(testResult.isRepoFileExists());
	}

	@Test
	public void testFtpTransferManagerNonExistingFolderTestCreateTarget() throws StorageException {
		StorageTestResult testResult = test("/nonExistingFolder", true);

		assertTrue(testResult.isTargetCanConnect());
		assertFalse(testResult.isTargetExists());
		assertTrue(testResult.isTargetCanCreate());
		assertFalse(testResult.isTargetCanWrite());
		assertFalse(testResult.isRepoFileExists());
	}

	@Test
	public void testFtpTransferManagerNonExistingFolderNoTestCreateTarget() throws StorageException {
		StorageTestResult testResult = test("/nonExistingFolder", false);

		assertTrue(testResult.isTargetCanConnect());
		assertFalse(testResult.isTargetExists());
		assertFalse(testResult.isTargetCanCreate());
		assertFalse(testResult.isTargetCanWrite());
		assertFalse(testResult.isRepoFileExists());
	}

	public StorageTestResult test(String path, boolean testCreateTarget) throws StorageException {
		FtpTransferSettings connection = workingConnection();
		connection.setPath(path);

		return getPlugin().createTransferManager(connection, null).test(testCreateTarget);
	}

	public FtpTransferPlugin getPlugin() {
		return (FtpTransferPlugin) Plugins.get("ftp");
	}

	public FtpTransferSettings workingConnection() throws StorageException {
		FtpTransferSettings connection = (FtpTransferSettings) getPlugin().createEmptySettings();

		connection.setHostname(EmbeddedTestFtpServer.HOST);
		connection.setPort(EmbeddedTestFtpServer.PORT);
		connection.setUsername(EmbeddedTestFtpServer.USER1);
		connection.setPassword(EmbeddedTestFtpServer.PASSWORD1);
		return connection;
	}

	public FtpTransferSettings invalidConnection() {
		FtpTransferSettings connection = new FtpTransferSettings();
		connection.setHostname(EmbeddedTestFtpServer.HOST_WRONG);
		connection.setPort(EmbeddedTestFtpServer.PORT);
		connection.setUsername(EmbeddedTestFtpServer.USER1);
		connection.setPassword(EmbeddedTestFtpServer.PASSWORD1);
		return connection;
	}
}
