package org.jboss.tools.forge.ui.bot.test;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.jboss.tools.forge.ui.bot.test.suite.ForgeConsoleTestBase;
import org.jboss.tools.ui.bot.ext.config.Annotations.Require;
import org.junit.Test;
/**
 * 
 * @author psrna
 *
 */	
@Require(clearWorkspace=true)
public class PreferencesTest extends ForgeConsoleTestBase {
	
	private static String FORGE_RUNTIME_NAME = "1.1.3.Final";
	private static String FORGE_RUNTIME_LOCATION = "/home/psrna/Downloads/forge-distribution-1.1.3.Final";
	
	
	@Test
	public void addAndSelectForgeRuntimeTest(){
		
		setForgeRuntime(FORGE_RUNTIME_NAME, FORGE_RUNTIME_LOCATION);
		assertTrue(isForgeRunning());
		assertTrue(getForgeVersion().equals(FORGE_RUNTIME_NAME));
	}
	
	@Test
	public void editEmbeddedRuntimeTest(){
		
		SWTBotShell preferences = getFRuntimesPrefShell();
		preferences.bot().table().getTableItem("embedded").select();
		
		/* embedded runtime cannot be edited, removed */
		assertFalse(preferences.bot().button("Edit...").isEnabled());
		assertFalse(preferences.bot().button("Remove").isEnabled());
		
	}
	
	@Test
	public void removeSelectedRuntimeTest(){
		
		setForgeRuntime(FORGE_RUNTIME_NAME, FORGE_RUNTIME_LOCATION);
		assertTrue(isForgeRunning());
		assertTrue(getForgeVersion().equals(FORGE_RUNTIME_NAME));
		
		SWTBotShell preferences = getFRuntimesPrefShell();
		/* Remove button is disabled on active/selected runtime */
		assertTrue(preferences.bot().table().getTableItem(FORGE_RUNTIME_NAME).isChecked());
		assertFalse(preferences.bot().button("Remove").isEnabled());
		
		preferences.bot().table().getTableItem("embedded").check(); //check embedded
		
		/* Remove button is enabled on highlighted unactive runtime */  
		preferences.bot().table().getTableItem(FORGE_RUNTIME_NAME).select();  
		assertTrue(preferences.bot().button("Remove").isEnabled());
		preferences.bot().button("Remove").click();
		
		try{
			SWTBotTableItem removed_cell = preferences.bot().table().getTableItem(FORGE_RUNTIME_NAME);
			assertFalse("Runtime not deleted?", removed_cell != null);
		}catch(WidgetNotFoundException e){
			//expected
		}
	}	
}