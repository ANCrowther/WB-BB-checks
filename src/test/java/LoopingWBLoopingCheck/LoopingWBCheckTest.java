package LoopingWBLoopingCheck;

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

public class LoopingWBCheckTest {
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
	
	@DisplayName("WB: Tests looping statement for loop for true return")
	@ParameterizedTest
	@ValueSource(ints = { TokenTypes.LITERAL_DO, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE})
	public void testLoopingStatementWhitebox(int token) {
		Mockito.when(mockAST.getType()).thenReturn(token);
		checks.visitToken(mockAST);
		
		assertTrue(checks.isLoopingStatement(mockAST.getType()));
	}
	
	@DisplayName("WB: Tests looping statement for loop for false return")
	@ParameterizedTest
	@ValueSource(ints = { TokenTypes.DIV, TokenTypes.STRING_LITERAL})
	public void testLoopingStatementFalseWhitebox(int token) {
		Mockito.when(mockAST.getType()).thenReturn(token);
		checks.visitToken(mockAST);
		
		assertFalse(checks.isLoopingStatement(mockAST.getType()));
	}
}