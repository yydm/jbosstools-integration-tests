package org.jboss.ide.eclipse.as.ui.bot.test.as3;

import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqType;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.JBossServer;
import org.jboss.ide.eclipse.as.ui.bot.test.template.DeployJSPProjectTemplate;
import org.jboss.reddeer.requirements.server.ServerReqState;

/**
 * @see DeployJSPProjectTemplate
 * @author Lucia Jelinkova
 *
 */

@JBossServer(state=ServerReqState.RUNNING, type=ServerReqType.AS3_2,cleanup=false)
public class DeployJSPProjectAS3Server extends DeployJSPProjectTemplate {

	@Override
	protected String getConsoleMessage() {
		return "deploy, ctxPath=/" + PROJECT_NAME;
	}
}
