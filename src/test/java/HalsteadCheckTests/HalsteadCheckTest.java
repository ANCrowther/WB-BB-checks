package HalsteadCheckTests;

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
import java.util.Arrays;

public class HalsteadCheckTest {
	// Used to test results for acceptable & default tokens
	private int[] expectedTokenTypeList = {
			TokenTypes.ASSIGN, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT,
			TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BSR, TokenTypes.BSR_ASSIGN,
			TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.COLON, TokenTypes.COMMA,
			TokenTypes.DEC, TokenTypes.DIV, TokenTypes.DIV_ASSIGN, TokenTypes.DOT,
			TokenTypes.EQUAL, TokenTypes.EXPR, TokenTypes.GE, TokenTypes.GT,
			TokenTypes.INC, TokenTypes.INDEX_OP, TokenTypes.LAND, TokenTypes.LE,
			TokenTypes.LITERAL_INSTANCEOF, TokenTypes.LNOT, TokenTypes.LOR, TokenTypes.LT,
			TokenTypes.MINUS, TokenTypes.MINUS_ASSIGN, TokenTypes.MOD, TokenTypes.MOD_ASSIGN,
			TokenTypes.NOT_EQUAL, TokenTypes.PLUS, TokenTypes.PLUS_ASSIGN, TokenTypes.POST_DEC,
			TokenTypes.POST_INC, TokenTypes.QUESTION, TokenTypes.SL, TokenTypes.SL_ASSIGN,
			TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.STAR, TokenTypes.STAR_ASSIGN,
			TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS, TokenTypes.IDENT, TokenTypes.LITERAL_CHAR,
			TokenTypes.LITERAL_SHORT, TokenTypes.LITERAL_INT, TokenTypes.LITERAL_FLOAT, TokenTypes.LITERAL_LONG,
			TokenTypes.LITERAL_DOUBLE, TokenTypes.NUM_DOUBLE,  TokenTypes.NUM_FLOAT,  TokenTypes.NUM_INT, 
			TokenTypes.NUM_LONG, TokenTypes.STRING_LITERAL
	};
	
	// Used to test results for required tokens
	private int[] expectedRequiredToken = new int[0];
	
	private int[] notExpectedTokenTypeList = { 
			TokenTypes.COMMA, TokenTypes.DIV 
			};
	
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
	
	@DisplayName("BB: testBeginTree()")
	@Test
	public void testBeginTree() {
		checks.beginTree(mockAST);
		
		assertEquals(0, checks.getOperatorsCount());
		assertEquals(0, checks.getOperandsCount());
		assertEquals(0, checks.getUniqueOperatorsCount());
		assertEquals(0, checks.getUniqueOperandsCount());
		assertEquals(0, checks.getHalsteadVoculary());
		assertEquals(0, checks.getHalsteadVolume());
		assertEquals(0, checks.getHalsteadDifficulty());
		assertEquals(0, checks.getHalsteadEffort());
		assertEquals(0, checks.getExpressionCount());
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
	
	// Used a roundabout way to test the private boolean isOperator()
	@DisplayName("BB: testIsOperator()")
	@Test
	public void testIsOperator() {
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.DIV);
		assertEquals(0, checks.getOperatorsCount()); // checks has operator count = 0 before adding division token
		checks.visitToken(mockAST);
		assertEquals(1, checks.getOperatorsCount());
	}
	
	// Used a roundabout way to test the private boolean isOperand()
	@DisplayName("BB: testIsOperand()")
	@Test
	public void testIsOperand() {
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.NUM_FLOAT);
		assertEquals(0, checks.getOperandsCount()); // checks has operand count = 0 before adding division token
		checks.visitToken(mockAST);
		assertEquals(1, checks.getOperandsCount());
	}
	
	@DisplayName("BB: testUniqueOperatorsCount()")
	@Test
	public void testUniqueOperatorsCount() {
		checks.setUniqueOperatorsCount(5);
		
		assertEquals(5, checks.getUniqueOperatorsCount());
		assertNotEquals(4, checks.getUniqueOperatorsCount());
		assertNotEquals(6, checks.getUniqueOperatorsCount());
	}
	
	@DisplayName("BB: testUniqueOperandsCount()")
	@Test
	public void testUniqueOperandsCount() {
		checks.setUniqueOperandsCount(5);
		
		assertEquals(5, checks.getUniqueOperandsCount());
		assertNotEquals(4, checks.getUniqueOperandsCount());
		assertNotEquals(6, checks.getUniqueOperandsCount());
	}
	
	@DisplayName("BB: testSetHalsteadLength()")
	@Test
	public void testSetHalsteadLength() {
		for (int index = 0; index < 3; index++) {
			checks.addOperandsCount();
		}
		for (int index = 0; index < 6; index++) {
			checks.addOperatorsCount();
		}

		assertEquals(3, checks.getOperandsCount());
		assertEquals(6, checks.getOperatorsCount());
		
		checks.setHalsteadLength();
		
		assertEquals(9, checks.getHalsteadLength());
		assertNotEquals(8, checks.getHalsteadLength());
		assertNotEquals(10, checks.getHalsteadLength());
	}
	
	@DisplayName("BB: testSetHalsteadVocabulary()")
	@Test
	public void testSetHalsteadVocabulary() {
		checks.setUniqueOperatorsCount(5);
		checks.setUniqueOperandsCount(2);
		checks.setHalsteadVocabulary();
		
		assertEquals(7, checks.getHalsteadVoculary());
	}
	
	// Results are rounded '0.00' as program only prints this format.
	// Volume = 9*log(5)/log(2)=20.89735...
	@DisplayName("BB: testSetHalsteadVolume()")
	@Test
	public void testSetHalsteadVolume() {
		for (int index = 0; index < 3; index++) {
			checks.addOperandsCount();
		}
		for (int index = 0; index < 6; index++) {
			checks.addOperatorsCount();
		}
		checks.setUniqueOperatorsCount(3);
		checks.setUniqueOperandsCount(2);
		checks.setHalsteadLength();
		checks.setHalsteadVocabulary();
		checks.setHalsteadVolume();
		String testAnswer = df.format(checks.getHalsteadVolume());
		assertEquals(20.90, Double.parseDouble(testAnswer));
	}
	
	// Results are rounded '0.00' as program only prints this format.
	// difficulty = (3.0/2)*(3/2)=2.25
	@DisplayName("BB: testHalsteadDifficulty()")
	@Test
	public void testHalsteadDifficulty() {
		for (int index = 0; index < 3; index++) {
			checks.addOperandsCount();
		}
		for (int index = 0; index < 6; index++) {
			checks.addOperatorsCount();
		}
		checks.setUniqueOperatorsCount(3);
		checks.setUniqueOperandsCount(2);
		checks.setHalsteadDifficulty();
		
		String testAnswer = df.format(checks.getHalsteadDifficulty());
		assertEquals(2.25, Double.parseDouble(testAnswer));
	}
	
	// Results are rounded '0.00' as program only prints this format.
	// difficulty = 20.897535*2.25=47.01945...
	@DisplayName("BB: testSetHalsteadEffort()")
	@Test
	public void testSetHalsteadEffort() {
		for (int index = 0; index < 3; index++) {
			checks.addOperandsCount();
		}
		for (int index = 0; index < 6; index++) {
			checks.addOperatorsCount();
		}
		checks.setUniqueOperatorsCount(3);
		checks.setUniqueOperandsCount(2);
		checks.setHalsteadLength();
		checks.setHalsteadVocabulary();
		checks.setHalsteadVolume();
		checks.setHalsteadDifficulty();
		checks.setHalsteadEffort();
		
		String testAnswer = df.format(checks.getHalsteadEffort());
		assertEquals(47.02, Double.parseDouble(testAnswer));
	}
	
	@DisplayName("BB: testExpressionCount()")
	@Test
	public void testExpressionCount() {
		assertEquals(0, checks.getExpressionCount());
		for (int index = 0; index < 3; index++) {
			checks.addExpressionCount();
		}
		assertEquals(3, checks.getExpressionCount());
		assertNotEquals(0, checks.getExpressionCount());
		assertNotEquals(2, checks.getExpressionCount());
		assertNotEquals(4, checks.getExpressionCount());
	}
	
	@DisplayName("BB: testVisitToken()")
	@Test
	public void testVisitToken() {
		checks = Mockito.mock(HalsteadChecks.class);
		checks.visitToken(mockAST);
		Mockito.verify(checks).visitToken(mockAST);
	}
}