package MissingCtorCheckTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.exceptions.base.MockitoException;

import com.google.common.base.VerifyException;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import CtorCheck.MissingCtorCheck;
import DEV3.Dev3BlackBoxTester;

import static org.mockito.Mockito.*;

import java.util.Arrays;

public class MissingCtorCheckTests {
	DetailAST mockAST;
	DetailAstImpl mockASTImpl;
	MissingCtorCheck checks;
	String temp;
	
	private int[] expectedTokenList = {
			TokenTypes.ABSTRACT
	};
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	  
	@Test
	public void testGetDefaultToken() {
		checks = new MissingCtorCheck();
		Arrays.equals(expectedTokenList, checks.getDefaultTokens());
	}
	
	@Test
	public void testGetRequiredToken() {
		checks = new MissingCtorCheck();
		Arrays.equals(expectedTokenList, checks.getRequiredTokens());
	}
	
	@Test
	public void testGetAcceptableTokensToken() {
		checks = new MissingCtorCheck();
		Arrays.equals(expectedTokenList, checks.getAcceptableTokens());
	}

	@Test
	public void testVisitToken() {
		checks = new MissingCtorCheck();
		mockAST = Mockito.mock(DetailAST.class);
		mockASTImpl = new DetailAstImpl();
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.METHOD_DEF);
		verify(checks).visitToken(mockAST);
	}
}