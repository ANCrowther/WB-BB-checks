package Deliverable3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import DEV3.Dev3BlackBoxTester;
import CommentsCheck.CommentsChecks;

public class BBCommentTests {
	private Dev3BlackBoxTester tester;
	private CommentsChecks checker = new CommentsChecks();
	final String pathName1 = "src/main/java/DEV3TestCases/SingleIterationSample.java";
	final String pathName2 = "src/main/java/DEV3TestCases/MultipleIterationSample.java";
 	
	private Dev3BlackBoxTester testerPrep(String pathName) throws CheckstyleException {
		tester = new Dev3BlackBoxTester(pathName, checker);
		tester.configureCheck();
		tester.walk();
		return tester;
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void CommentTest(int iter) throws CheckstyleException {
		int numberComments = 0;
		int numberLines = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberComments = 1;
			numberLines = 1;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberComments = 5;
			numberLines = 10;
		}
		System.out.println("Comment Test" + iter + ": " + checker.getResults());
		assertTrue(checker.getNumberOfComments() == numberComments);
		assertTrue(checker.getNumberOfCommentLinesCount() == numberLines);
		
	}
	
}