package Deliverable3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import DEV3.Dev3BlackBoxTester;
import LoopingCheck.LoopingChecks;

public class BBLoopingTests {
	private Dev3BlackBoxTester tester;
	private LoopingChecks checker = new LoopingChecks();
	String pathName1 = "src/main/java/DEV3TestCases/SingleIterationSample.java";
	String pathName2 = "src/main/java/DEV3TestCases/MultipleIterationSample.java";

 	
	private Dev3BlackBoxTester testerPrep(String pathName) throws CheckstyleException {
		tester = new Dev3BlackBoxTester(pathName, checker);
		tester.configureCheck();
		tester.walk();
		return tester;
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void LoopTest(int iter) throws CheckstyleException {
		int numberLoops = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberLoops = 1;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberLoops = 3;
		}
		
		System.out.println("Loop Test" + iter + ": # of loops: " + checker.getLoopingStatementCount());
		assertTrue(checker.getLoopingStatementCount() == numberLoops);
	}
}