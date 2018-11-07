/*******************************************************************************
 * Copyright (c) 2010-2018 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.cdi.bot.test.condition;

import java.util.List;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.eclipse.ui.problems.Problem;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView.ProblemType;

public class BeanXMLValidationProblemIsEmpty extends AbstractWaitCondition{
	
	private String projectName;
	
	public BeanXMLValidationProblemIsEmpty(String projectName){
		this.projectName = projectName;
	}

	public boolean test() {
		ProblemsView pv = new ProblemsView();
		List<Problem> problems = pv.getProblems(ProblemType.ALL);
		boolean toReturn = true;
		for(Problem problem: problems){
			try{
				if(problem.getPath().contains(projectName) &&
						problem.getResource().contains("beans.xml") &&
						problem.getType().contains("CDI Problem")) {
					toReturn = false;
				}
			} catch (RedDeerException ex){
				//do nothing. Problem is not there anymore
			}
 		}
		return toReturn;
	}

	public String description() {
		ProblemsView pv = new ProblemsView();
		pv.open();
		List<Problem> problems = pv.getProblems(ProblemType.ALL);
		StringBuilder b = new StringBuilder();
		for(Problem p: problems){
			try{
				b.append("\n "+p.getDescription());
			} catch (RedDeerException e){
				//do nothing. Problem is not there anymore
			}
		}
		return  "Problems View doesnt contain bean xml validation error. Current Errors: "+b.toString();
	}

}
