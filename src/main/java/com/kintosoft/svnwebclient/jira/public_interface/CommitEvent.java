package com.kintosoft.svnwebclient.jira.public_interface;

public class CommitEvent {
	private long repoId;
	private long revision;
	private long timestamp;

	public long getRepoId() {
		return repoId;
	}

	public long getRevision() {
		return revision;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public CommitEvent(long repoId, long revision, long timestamp) {
		super();
		this.repoId = repoId;
		this.revision = revision;
		this.timestamp = timestamp;
	}
}
