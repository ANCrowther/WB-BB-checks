package HalsteadWBChecckTests;

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

import HalsteadCheck.HalsteadChecks;

import java.text.DecimalFormat;

public class HalsteadWBCheckTest {
	DetailAST mockAST;
	DetailAstImpl mockASTImpl;
	HalsteadChecks checks;
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	@BeforeEach
	public void setup() {
		checks = new HalsteadChecks();
		mockAST = Mockito.mock(DetailAST.class);
		mockASTImpl = new DetailAstImpl();
	}
	
	@DisplayName("WB:  Testing isOperator for statement for true")
	@ParameterizedTest
	@ValueSource(ints = { TokenTypes.MINUS, TokenTypes.MINUS_ASSIGN, TokenTypes.MOD})
	public void testIsOperatorWhitebox(int token) {
		Mockito.when(mockAST.getType()).thenReturn(token);
		checks.visitToken(mockAST);
		
		assertTrue(checks.isOperator(mockAST.getType()));
	}
	
	@DisplayName("WB: Testing isOperator for statement for false")
	@ParameterizedTest
	@ValueSource(ints = { TokenTypes.IDENT, TokenTypes.LITERAL_CHAR})
	public void testIsOperatorFalseWhitebox(int token) {
		Mockito.when(mockAST.getType()).thenReturn(token);
		checks.visitToken(mockAST);
		
		assertFalse(checks.isOperator(mockAST.getType()));
	}
	
	@DisplayName("WB:  Testing isOperand for statement for true")
	@ParameterizedTest
	@ValueSource(ints = { TokenTypes.NUM_FLOAT, TokenTypes.IDENT, TokenTypes.LITERAL_CHAR })
	public void testIsOperandWhitebox(int token) {
		Mockito.when(mockAST.getType()).thenReturn(token);
		checks.visitToken(mockAST);
		
		assertTrue(checks.isOperand(mockAST.getType()));
	}
	
	@DisplayName("WB: Testing isOperand for statement for false")
	@ParameterizedTest
	@ValueSource(ints = { TokenTypes.MINUS, TokenTypes.MOD})
	public void testIsOperandFalseWhitebox(int token) {
		Mockito.when(mockAST.getType()).thenReturn(token);
		checks.visitToken(mockAST);
		
		assertFalse(checks.isOperand(mockAST.getType()));
	}
	
	@DisplayName("WB: Testing the if and else-if statements using a bad token and good tokens.")
	@Test
	public void testVisitTokenWhiteBox() {
		//testing for unlisted token
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_DO);
		checks.visitToken(mockAST);
		assertEquals(0, checks.getOperatorsCount());
		assertEquals(0, checks.getOperandsCount());
		assertEquals(0, checks.getUniqueOperatorsCount());
		assertEquals(0, checks.getUniqueOperandsCount());
		assertEquals(0, checks.getExpressionCount());
		assertNotEquals(-1, checks.getOperatorsCount());
		assertNotEquals(-1, checks.getOperandsCount());
		assertNotEquals(-1, checks.getUniqueOperatorsCount());
		assertNotEquals(-1, checks.getUniqueOperandsCount());
		assertNotEquals(-1, checks.getExpressionCount());
		assertNotEquals(1, checks.getOperatorsCount());
		assertNotEquals(1, checks.getOperandsCount());
		assertNotEquals(1, checks.getUniqueOperatorsCount());
		assertNotEquals(1, checks.getUniqueOperandsCount());
		assertNotEquals(1, checks.getExpressionCount());
		
		// Testing operator if loop
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.DIV);
		checks.visitToken(mockAST);
		assertEquals(1, checks.getOperatorsCount());
		assertEquals(1, checks.getUniqueOperatorsCount());
		assertNotEquals(0, checks.getOperatorsCount());
		assertNotEquals(0, checks.getUniqueOperatorsCount());
		assertNotEquals(2, checks.getOperatorsCount());
		assertNotEquals(2, checks.getUniqueOperatorsCount());
		
		checks.visitToken(mockAST);
		assertEquals(2, checks.getOperatorsCount());
		assertEquals(1, checks.getUniqueOperatorsCount());
		assertNotEquals(1, checks.getOperatorsCount());
		assertNotEquals(0, checks.getUniqueOperatorsCount());
		assertNotEquals(3, checks.getOperatorsCount());
		assertNotEquals(2, checks.getUniqueOperatorsCount());
		
		// Testing operand if loop
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
		checks.visitToken(mockAST);
		assertEquals(1, checks.getOperandsCount());
		assertEquals(1, checks.getUniqueOperandsCount());
		assertNotEquals(0, checks.getOperandsCount());
		assertNotEquals(0, checks.getUniqueOperandsCount());
		assertNotEquals(2, checks.getOperandsCount());
		assertNotEquals(2, checks.getUniqueOperandsCount());
		
		checks.visitToken(mockAST);
		checks.visitToken(mockAST);
		assertEquals(3, checks.getOperandsCount());
		assertEquals(1, checks.getUniqueOperandsCount());
		assertNotEquals(2, checks.getOperandsCount());
		assertNotEquals(0, checks.getUniqueOperandsCount());
		assertNotEquals(4, checks.getOperandsCount());
		assertNotEquals(2, checks.getUniqueOperandsCount());
		
		// Testing expression if loop
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.EXPR);
		checks.visitToken(mockAST);
		assertEquals(1, checks.getExpressionCount());
		assertNotEquals(0, checks.getExpressionCount());
		assertNotEquals(2, checks.getExpressionCount());
		for (int index = 0; index < 5; index++) {
			checks.visitToken(mockAST);
		}
		assertEquals(6, checks.getExpressionCount());
		assertNotEquals(5, checks.getExpressionCount());
		assertNotEquals(7, checks.getExpressionCount());
	}
}