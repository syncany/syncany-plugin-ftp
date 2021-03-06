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
package org.syncany.plugins.ftp;

import org.simpleframework.xml.Element;
import org.syncany.plugins.transfer.Encrypted;
import org.syncany.plugins.transfer.Setup;
import org.syncany.plugins.transfer.TransferSettings;

/**
 * The FTP connection represents the settings required to connect to an
 * FTP-based storage backend. It can be used to initialize/create an 
 * {@link FtpTransferManager} and is part of the {@link FtpTransferPlugin}.  
 *
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class FtpTransferSettings extends TransferSettings {
	@Element(name = "hostname", required = true)
	@Setup(order = 1, description = "Hostname")
	private String hostname;
	
	@Element(name = "username", required = true)
	@Setup(order = 2, description = "Username")
	private String username;
	
	@Element(name = "password", required = true)
	@Setup(order = 3, sensitive = true, description = "Password")
	@Encrypted
	private String password;
	
	@Element(name = "path", required = true)
	@Setup(order = 4, description = "Relative path")
	private String path;
	
	@Element(name = "port", required = false)
	@Setup(order = 5, description = "Port")
	private int port = 21;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return FtpTransferSettings.class.getSimpleName() + "[hostname=" + hostname + ":" + port + ", username=" + username + ", path=" + path + "]";
	}
}
