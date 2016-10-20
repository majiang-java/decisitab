/**
 * 
 * @author majiang
 *
 */
package com.ifre.ruleengin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

public class RuleFactory {
	
	Logger logger = Logger.getLogger(RuleFactory.class);
	private DecisionTableConfiguration dtableconfiguration;
	private KnowledgeBase kbase;
	private StatelessKieSession statelessKieSession;
	
	

	public StatelessKieSession createKsession(String path) throws IOException {
		if(dtableconfiguration == null){
			dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
			dtableconfiguration.setInputType(DecisionTableInputType.XLS);
		}
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		File pathf= new File(path);
		FileFilter filefilter = new FileFilter() {
		        public boolean accept(File file) {
		            if (file.getName().endsWith(".xls")) {
		                return true;
		            }
		            return false;
		        }
		 };

		File[] files = pathf.listFiles(filefilter);
		for (File file : files) {
			Resource xlsRes = ResourceFactory.newFileResource(file);
			kbuilder.add(xlsRes, ResourceType.DTABLE, dtableconfiguration);
			xlsRes.getInputStream().close();
		}
	   if ( kbuilder.hasErrors() ) {
		   logger.info( kbuilder.getErrors().toString() );
	            throw new RuntimeException( "Unable to compile \"."+kbuilder.getErrors().toString() ); 
        }
	   
	   KnowledgeBase kbase = LocalKnowledgeBaseFactory.getKnowledgeBaseInstance();
       kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
       return kbase.newStatelessKieSession();
       
	}
	
	public StatelessKieSession createKsession(List<File> files) {
		if(dtableconfiguration == null){
			dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
			dtableconfiguration.setInputType(DecisionTableInputType.XLS);
		}
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		for (File file : files) {
			Resource xlsRes = ResourceFactory.newFileResource(file);
			kbuilder.add(xlsRes, ResourceType.DTABLE, dtableconfiguration);
		}
	
	   if ( kbuilder.hasErrors() ) {
		   logger.info( kbuilder.getErrors().toString() );
	            throw new RuntimeException( "Unable to compile \"."+kbuilder.getErrors().toString() ); 
        }
	
	   KnowledgeBase kbase = LocalKnowledgeBaseFactory.getKnowledgeBaseInstance();
       kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
       return kbase.newStatelessKieSession();
       
	}
	
	public void checkKsession(File f) {
		if(dtableconfiguration == null){
			dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
			dtableconfiguration.setInputType(DecisionTableInputType.XLS);
		}
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		Resource xlsRes = ResourceFactory.newFileResource(f);
		kbuilder.add(xlsRes, ResourceType.DTABLE, dtableconfiguration);
	   if ( kbuilder.hasErrors() ) {
		   logger.info( kbuilder.getErrors().toString() );
	            throw new RuntimeException( "Unable to compile \"."+kbuilder.getErrors().toString() ); 
        }
	   
	   KnowledgeBase kbase = LocalKnowledgeBaseFactory.getKnowledgeBaseInstance();
       kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
       KieSession ksession =  kbase.newKieSession();
       
       ksession.destroy();
       
	}
	
	
}
