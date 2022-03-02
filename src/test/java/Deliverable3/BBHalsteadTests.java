package Deliverable3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import DEV3.Dev3BlackBoxTester;
import HalsteadCheck.HalsteadChecks;
import java.text.DecimalFormat;

public class BBHalsteadTests {
	private Dev3BlackBoxTester tester;
	private HalsteadChecks checker = new HalsteadChecks();
	private static DecimalFormat df = new DecimalFormat("0.00");
	private String pathName1 = "src/main/java/DEV3TestCases/SingleIterationSample.java";
	private String pathName2 = "src/main/java/DEV3TestCases/MultipleIterationSample.java";
	private String pathName3 = "src/main/java/DEV3TestCases/HalsteadSample.java";
	
	private Dev3BlackBoxTester testerPrep(String pathName) throws CheckstyleException {
		tester = new Dev3BlackBoxTester(pathName, checker);
		tester.configureCheck();
		tester.walk();
		return tester;
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void OperatorTest(int iter) throws CheckstyleException {
		int numberOperators = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberOperators = 3;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberOperators = 15;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		System.out.println("Halstead Operator Test" + iter + ":       " + checker.getOperatorsCount());
		assertTrue(checker.getOperatorsCount() == numberOperators);
	}
	
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void OperandTest(int iter) throws CheckstyleException {
		int numberOperands = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberOperands = 9;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberOperands = 33;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		System.out.println("Halstead Operand Test" + iter + ":        " + checker.getOperandsCount());
		assertTrue(checker.getOperandsCount() == numberOperands);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void UniqueOperandTest(int iter) throws CheckstyleException {
		int numberUniqueOperands = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberUniqueOperands = 7;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberUniqueOperands = 14;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		System.out.println("Halstead UniqueOperand Test" + iter + ":  " + checker.getUniqueOperandsCount());
		assertTrue(checker.getUniqueOperandsCount() == numberUniqueOperands);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void UniqueOperatorsTest(int iter) throws CheckstyleException {
		int numberUniqueOperators = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberUniqueOperators = 3;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberUniqueOperators = 5;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		System.out.println("Halstead UniqueOperator Test" + iter + ": " + checker.getUniqueOperatorsCount());
		assertTrue(checker.getUniqueOperatorsCount() == numberUniqueOperators);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void LengthTest(int iter) throws CheckstyleException {
		int numberLength = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberLength = 12;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberLength = 48;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		System.out.println("Halstead Length Test" + iter + ":         " + checker.getHalsteadLength());
		assertTrue(checker.getHalsteadLength() == numberLength);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void VocabularyTest(int iter) throws CheckstyleException {
		int numberLength = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberLength = 12;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberLength = 48;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		System.out.println("Halstead Vocabulary Test" + iter + ":     " + checker.getHalsteadLength());
		assertTrue(checker.getHalsteadLength() == numberLength);
	}

	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void VolumeTest(int iter) throws CheckstyleException {
		double numberVolume = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberVolume = 39.86;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberVolume = 203.90;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		String testAnswer = df.format(checker.getHalsteadVolume());
		System.out.println("Halstead Volume test" + iter + ":         " + testAnswer);
		assertTrue(Double.parseDouble(testAnswer) == numberVolume);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void DifficultyTest(int iter) throws CheckstyleException {
		double numberVolume = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberVolume = 1.93;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberVolume = 5.89;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		String testAnswer = df.format(checker.getHalsteadDifficulty());
		System.out.println("Halstead Difficulty test" + iter + ":     " + testAnswer);
		assertTrue(Double.parseDouble(testAnswer) == numberVolume);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void EffortTest(int iter) throws CheckstyleException {
		double numberVolume = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberVolume = 76.88;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberVolume = 1201.56;
		}
		else {
			tester = testerPrep(pathName3);
		}
		
		String testAnswer = df.format(checker.getHalsteadEffort());
		System.out.println("Halstead Effort test" + iter + ":         " + testAnswer);
		assertTrue(Double.parseDouble(testAnswer) == numberVolume);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2})
	public void ExpressionTest(int iter) throws CheckstyleException {
		int numberExpressions = 0;
		
		if (iter == 1) {
			tester = testerPrep(pathName1);
			numberExpressions = 3;
		}
		else if (iter == 2) {
			tester = testerPrep(pathName2);
			numberExpressions = 13;
		}
		else {
			tester = testerPrep(pathName3);
		}

		System.out.println("Expression Test" + iter + ":              " + checker.getExpressionCount());
		assertTrue(checker.getExpressionCount() == numberExpressions);
	}
}