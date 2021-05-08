package org.abs_models.frontend.mtvl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import org.abs_models.frontend.FrontendTest;
import org.abs_models.frontend.ast.Model;

public class FMAnalysisTest extends FrontendTest {
	
	  static private String helloFM =
		        " module Helloworld;" +
		        " product P1 (English);" +
		        " product P2 (French);" +
		        " product P3 (French, Repeat{times=10});" +
		        " product P4 (English, Repeat{times=6});" +
		        " root MultiLingualHelloWorld {" +
		        "   group allof {" +
		        "      Language {" +
		        "        group oneof { English, Dutch, French, German }" +
		        "      }," +
		        "      opt Repeat {" +
		        "        Int times in [0 .. 10];" +
		        "        ifin: times > 0; " +
		        "      } " +
		        "    } " +
		        " }" +
		        " extension English {" +
		        "    group oneof { UK, US }" +
		        "    ifin: Repeat ->" +
		        "          (Repeat.times >= 2 && Repeat.times <= 5);" +
		        " }";
	  
	  static private String emptyFM = " root FM";

	  private ChocoSolver resetSolver(Model m) {
		  ChocoSolver solver = new ChocoSolver();
		  solver = m.instantiateCSModel();
		  return solver;
	  }
	  
	  @Test
	  public void nsol() {
		  //Test Case 1 empty FM 
		  Model model1 = assertParse(emptyFM);
		  //Test Case 2 Complete FM
		  Model model2 = assertParse(helloFM);
		  
	      ChocoSolver solverEmptyFM = model1.instantiateCSModel();
	      ChocoSolver solver1 = model2.instantiateCSModel(); 
	      model2.dropAttributes();
	      ChocoSolver solver2 = model2.instantiateCSModel(); //model with no attribute

	      //Boundary Test(Lower Bound) - empty FM
	      assertEquals(1, solverEmptyFM.countSolutions());
	      
	      //Boundary Test(Mid Range) - with and without attributes
	      assertEquals(93, solver1.countSolutions());
	      assertEquals(10, solver2.countSolutions());
	  }
	  
	  @Test
	  public void isvoid() {
		  //Test Case 1 empty FM 
		  Model model1 = assertParse(emptyFM);
		  //Test Case 2 Complete FM
		  Model model2 = assertParse(helloFM);
		  
		  ChocoSolver solverEmptyFM = model1.instantiateCSModel();
	      ChocoSolver solver = model2.instantiateCSModel();  
	      
	      //Boundary Test(Lower Bound) - empty FM
	      assertEquals("Feature Model is void.", solverEmptyFM.isVoid());
	      
	      //Boundary Test(Mid Range)
	      assertEquals("Feature Model is not void.", solver.isVoid());
	  }
	  
	  @Test
	  public void core() {
		  String result1 = "FM\n";
		  String result2 = 
				  "MultiLingualHelloWorld\n"+
		          "Language\n";
		  
		  //Test Case 1 empty FM
		  Model model1 = assertParse(emptyFM);
		  //Test Case 2 Complete FM
		  Model model2 = assertParse(helloFM);
		  
		  ChocoSolver solverEmptyFM = model1.instantiateCSModel();
	      ChocoSolver solver = model2.instantiateCSModel(); 
		  
	      //Boundary Test(Lower Bound) - empty FM
	      assertEquals(result1, solverEmptyFM.coreToStrings());
	      
	      //Boundary Test(Mid Range)
	      assertEquals(result2, solver.coreToStrings());
	  }
	  
	  @Test
	  public void variant() {
		  String result1 = "";
		  String result2 = 
				  "English\n" +
				  "Dutch\n" +
				  "French\n" +
				  "German\n" +
				  "Repeat\n" +
				  "UK\n" +
				  "US\n";
		  
		  //Test Case 1 empty FM
		  Model model1 = assertParse(emptyFM);
		  //Test Case 2 Complete FM
		  Model model2 = assertParse(helloFM);
		  
		  ChocoSolver solverEmptyFM = model1.instantiateCSModel();
	      ChocoSolver solver = model2.instantiateCSModel(); 
		  
	      //Boundary Test(Lower Bound) - empty FM
	      assertEquals(result1, solverEmptyFM.variantToStrings());
	      
	      //Boundary Test(Mid Range)
	      assertEquals(result2, solver.variantToStrings());
	  }
	  
	  @Test
	  public void validpartialconfig() {
		  Model model = assertParse(helloFM);
		  Model modelEmpty = assertParse(emptyFM);
		  ChocoSolver solver = new ChocoSolver();
	  
		  //Test Case 1a input "English,UK"
		  String[] validSelectedFeat = new String[] {"English","UK"};
		  ArrayList<String[]> listValidSelectedFeat = new ArrayList<>();
		  listValidSelectedFeat.add(validSelectedFeat);
		  
		  //Test Case 1b input "English,French"
		  String[] invalidSelectedFeat = new String[] {"English","French"};
		  ArrayList<String[]> listInvalidSelectedFeat = new ArrayList<>();
		  listInvalidSelectedFeat.add(invalidSelectedFeat);

		  //Test Case 2a input "<English,US>,<UK,Dutch>"
		  String[] validSFeature = new String[] {"English","US"};
		  String[] validRFeature = new String[] {"UK","Dutch"};
		  ArrayList<String[]> listValidSRFeature = new ArrayList<>();
		  listValidSRFeature.add(0,validSFeature);
		  listValidSRFeature.add(1,validRFeature);
		  
		  //Test Case 2b input "<English,Dutch>,<French>"
		  String[] invalidSFeature = new String[] {"English","Dutch"};
		  String[] invalidRFeature = new String[] {"French"};
		  ArrayList<String[]> listInvalidSRFeature = new ArrayList<>();
		  listInvalidSRFeature.add(0,invalidSFeature);
		  listInvalidSRFeature.add(1,invalidRFeature);
		  
		  String resultValid = "Config is valid";
		  String resultInvalid = "Config is not valid";	  
		  
		  solver = this.resetSolver(model);
		  assertEquals(resultValid, solver.validPartialConfig(listValidSelectedFeat));	  
		  
		  solver = this.resetSolver(model);
		  assertEquals(resultInvalid, solver.validPartialConfig(listInvalidSelectedFeat));
		  
		  solver = this.resetSolver(model);
		  assertEquals(resultValid, solver.validPartialConfig(listValidSRFeature));	
		  
		  solver = this.resetSolver(model);
		  assertEquals(resultInvalid, solver.validPartialConfig(listInvalidSRFeature));	  	  
		  
		  //Test with Empty model
		  //Test Case 1 input "English,French"
		  solver = this.resetSolver(modelEmpty);
		  assertEquals(resultInvalid, solver.validPartialConfig(listInvalidSelectedFeat));
		  
		  //Test Case 2 input "<English,Dutch>,<French>"
		  solver = this.resetSolver(modelEmpty);
		  assertEquals(resultInvalid, solver.validPartialConfig(listInvalidSRFeature));
		  
	  }
}
