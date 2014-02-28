package com.kintosoft.jira.svn.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.plugin.projectpanel.impl.GenericProjectTabPanel;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.browse.BrowseContext;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.kintosoft.svnwebclient.jira.public_interface.SWCPublicInterface;

public class SubversionALMDemoProjectTabPanel extends GenericProjectTabPanel {

	private final SWCPublicInterface swc;

	public SubversionALMDemoProjectTabPanel(
			JiraAuthenticationContext jiraAuthenticationContext,
			SWCPublicInterface swc) {
		super(jiraAuthenticationContext);

		this.swc = swc;

	}

	@Override
	public String getHtml(BrowseContext browseContext) {
		Map<String, Object> startingParams = new HashMap<String, Object>();
		Project project = browseContext.getProject();
		String key = project.getKey();
		User user = browseContext.getUser();

		startingParams.put("user", user);
		startingParams.put("project", project);
		startingParams.put("projectKey", key);

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			try {
				int commits = 0;
				conn = swc.getConnection();
				String sql = "select count(*) as count from KEYS where project=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, key);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					commits = rs.getInt("count");
				}
				rs.close();
				startingParams.put("commits", commits);
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.rollback(); // rollback or commit accordingly
					conn.close(); // return the connection to the pool
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return descriptor.getHtml("view", startingParams);
	}

}
