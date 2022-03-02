package LoopingCheckTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import LoopingCheck.LoopingChecks;

import java.text.DecimalFormat;
import java.util.Arrays;

public class LoopingCheckTest {
	private int[] expectedTokenTypeList = {
			TokenTypes.LITERAL_DO, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE
	};
	
	private int[] notExpectedTokenTypeList = { 
			TokenTypes.COMMA, TokenTypes.DIV 
			};
	
	// Used to test results for required tokens
	private int[] expectedRequiredToken = new int[0];
	
	DetailAST mockAST;
	DetailAstImpl mockASTImpl;
	LoopingChecks checks;
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	@BeforeEach
	public void setup() {
		checks = new LoopingChecks();
		mockAST = Mockito.mock(DetailAST.class);
		mockASTImpl = new DetailAstImpl();
	}
	
	@DisplayName("BB: Tests that the tree begins with 0 looping statement count")
	@Test
	public void testBeginTree() {
		checks.beginTree(mockAST);
		
		assertEquals(0, checks.getLoopingStatementCount());
		assertNotEquals(-1, checks.getLoopingStatementCount());
		assertNotEquals(1, checks.getLoopingStatementCount());
	}
	
	@DisplayName("BB: testGetAcceptableTokens()")
	@Test
	public void testGetAcceptableTokens() {
		int[] actualTokenList = checks.getAcceptableTokens();
		Arrays.equals(expectedTokenTypeList, actualTokenList);
		Arrays.mismatch(notExpectedTokenTypeList, actualTokenList);
	}
	
	@DisplayName("BB: testGetDefaultTokens()")
	@Test
	public void testGetDefaultTokens() {
		int[] actualTokenList = checks.getDefaultTokens();
		Arrays.equals(expectedTokenTypeList, actualTokenList);
		Arrays.mismatch(notExpectedTokenTypeList, actualTokenList);
	}
	
	@DisplayName("BB: testGetRequiredTokens()")
	@Test
	public void testGetRequiredTokens() {
		int[] actualTokenList = checks.getRequiredTokens();
		Arrays.equals(expectedRequiredToken, actualTokenList);
		Arrays.mismatch(notExpectedTokenTypeList, actualTokenList);
	}
	
	@DisplayName("BB: Tests looping counter for 2 loops with asserting 1 (F), 2 (T), & 3 (T)")
	@Test
	public void testAddLoopingStatementCount() {
		assertEquals(0, checks.getLoopingStatementCount());
		
		checks.addLoopingStatementCount();
		checks.addLoopingStatementCount();
		
		assertEquals(2, checks.getLoopingStatementCount());
		assertNotEquals(1, checks.getLoopingStatementCount());
		assertNotEquals(3, checks.getLoopingStatementCount());
	}
	
	@DisplayName("BB: testVisitToken()")
	@Test
	public void testVisitToken() {
		checks = Mockito.mock(LoopingChecks.class);
		checks.visitToken(mockAST);
		Mockito.verify(checks).visitToken(mockAST);
	}
}