package com.kintosoft.svnwebclient.jira.public_interface;

public interface CommitsEventbusService {
	public void startListenting(Object subscriber);

	public void stopListenting(Object subscriber);
}
