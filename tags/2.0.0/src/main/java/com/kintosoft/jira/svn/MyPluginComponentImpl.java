package com.kintosoft.jira.svn;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.sal.api.ApplicationProperties;
import com.google.common.eventbus.Subscribe;
import com.kintosoft.svnwebclient.jira.public_interface.CommitEvent;
import com.kintosoft.svnwebclient.jira.public_interface.CommitsEventbusService;
import com.kintosoft.svnwebclient.jira.public_interface.SWCPublicInterface;

public class MyPluginComponentImpl implements MyPluginComponent {

	private static Logger log = LoggerFactory
			.getLogger(MyPluginComponent.class);

	private final ApplicationProperties applicationProperties;

	private CommitsEventbusService eventBus;

	private SWCPublicInterface swc;

	private CommitEventListener commitListener;

	public static class CommitEventListener {

		@Subscribe
		public void handler(CommitEvent event) {
			log.info(event.getRevision() + " revision commited in repository "
					+ event.getRepoId());
		}

	}

	public MyPluginComponentImpl(ApplicationProperties applicationProperties,
			SWCPublicInterface swc, CommitsEventbusService eventbus) {
		this.applicationProperties = applicationProperties;
		this.swc = swc;
		this.eventBus = eventbus;
		this.commitListener = new CommitEventListener();

		log.info("MyPluginComponentImpl initialized");

		register();
	}

	public String getName() {
		if (null != applicationProperties) {
			return "myComponent:" + applicationProperties.getDisplayName();
		}

		return "myComponent";
	}

	public void register() {
		eventBus.startListenting(commitListener);
		log.info("MyPluginComponentImpl commit listener registered");
	}

	@PreDestroy
	public void unregister() {
		log.info("MyPluginComponentImpl destroying");
		eventBus.stopListenting(commitListener);
		log.info("MyPluginComponentImpl commit listener unregistered");
	}
}