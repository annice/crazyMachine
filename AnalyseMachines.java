package com.sample;

import java.util.ArrayList;
import java.util.Scanner;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class AnalyseMachines {
/**
 * Analyses each input machine with the set of rules in drl file
 */
  public static void main(String args[]) throws Exception{
		ArrayList<CrazyMachines> machinelist=new ArrayList<CrazyMachines>();
		machinelist.add(new CrazyMachines("BestPrice",2,5,VideoCard.none,800));
		machinelist.add(new CrazyMachines("CounterStriker",8,3,VideoCard.average,800));
		machinelist.add(new CrazyMachines("AlmostFree",1,1,VideoCard.none,400));
		machinelist.add(new CrazyMachines("MasterHalo",16,4,VideoCard.premium,3000));
		machinelist.add(new CrazyMachines("pythagoreanScream",32,5,VideoCard.none,8000));
		for(int i=0;i<machinelist.size();i++){
		CrazyMachines m1=machinelist.get(i);
		KnowledgeBase kbase = readKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ksession.insert(m1);
        ksession.fireAllRules();
		}
		int ch=0;
		do{
			System.out.println("--Menu--");
			System.out.println("1.Options for safe gaming computer");
			System.out.println("2.Are there any number crunchers that are risky?");
			System.out.println("3.Are all gaming computers hot?");
			System.out.println("4.Information about Machine 6");
			
			Scanner input = new Scanner(System.in);
			
			
		int choice=input.nextInt();
		
		switch(choice){
		case 1: 
			System.out.println("Options For Safe Gaming:");
			for(int i=0;i<machinelist.size();i++){
				CrazyMachines m= machinelist.get(i);
				if(!m.isRisky() && m.isCanGame()){
					System.out.println("Machine "+(i+1)+":Model Name: "+m.getModelName());
				}
			}
			break;
			
		case 2: 
			System.out.println("Number Crunchers That Are Risky:");
			int numcount=0;
			String risresult="";
			for(int i=0;i<machinelist.size();i++){
				CrazyMachines m= machinelist.get(i);
				if(!m.isCanCrunchNos() && m.isRisky()){
					risresult=risresult+"\nMachine "+i+1+":Model Name: "+m.getModelName();
				}
			}
			if(numcount>0){
				System.out.println(" We have found the following  models which are risky number cruchers "+risresult);
			}else{
				System.out.println("None");
			}
			break;
			
		case 3: 
			System.out.println("Are all gaming computers hot");
			int count=0;
			String result="";
			for(int i=0;i<machinelist.size();i++){
				CrazyMachines m= machinelist.get(i);
				if(!m.isCanGame() && m.isHot()){
					count++;
					result=result+"\nMachine "+i+1+":Model Name: "+m.getModelName();
				}
			}
			
			if(count>0){
				System.out.println("No, We have found the following  models which can game and are not hot "+result);
			}else{
				System.out.println("Yes, All available models exhibit this behaviour");
			}
			break;
			
		case 4:
			CrazyMachines m1=new CrazyMachines("pythagoreanScream",2,2,VideoCard.premium,5500);
			KnowledgeBase kbase = readKnowledgeBase();
	        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			ksession.insert(m1);
	        ksession.fireAllRules();

			System.out.println("Features for SuperMarioExtreme:\n Is Loud:"+m1.isLoud()+"\n Is Hot:"+m1.isHot()+"\n Is Cheap:"+m1.isCheap()+"" +
					"\n Is Expensive:"+m1.isExpensive()+"\n Is Risky:"+m1.isRisky()+"\n Can Game:"+m1.isCanGame()+"\n Can Crunch Numbers:"+m1.isCanCrunchNos());
		break;
		default:
			System.out.println("Please enter between 1 -4");
			break;
			
			
		}
		System.out.println("Do you want to enter the menu again,Press 1 to continue,or 0 to exit");
		 ch=input.nextInt();
		}while(ch==1);
		
			
	}
	
	/*Read knowledge base encoded in CrayRule.drl*/
	
	 private static KnowledgeBase readKnowledgeBase() throws Exception {
	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	        kbuilder.add(ResourceFactory.newClassPathResource("CrazyRule.drl"), ResourceType.DRL);
	        KnowledgeBuilderErrors errors = kbuilder.getErrors();
	        if (errors.size() > 0) {
	            for (KnowledgeBuilderError error: errors) {
	                System.err.println(error);
	            }
	            throw new IllegalArgumentException("Could not parse knowledge.");
	        }
	        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	        return kbase;
	    }

	
}
