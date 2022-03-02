package CommentsWBCheckTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import CommentsCheck.CommentsChecks;

public class CommentsWBCheckTest {
	DetailAST mockAST;
	DetailAstImpl mockASTImpl;
	CommentsChecks checks;
	
	@BeforeEach
	public void setup() {
		checks = new CommentsChecks();
		mockAST = Mockito.mock(DetailAST.class);
		mockASTImpl = new DetailAstImpl();
	}
	
	@Test
	public void testVisitTokenWhiteBox() {
		// SKhould fall out of the switch statement.
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.DIV);
		checks.visitToken(mockAST);
		assertEquals(0, checks.getNumberOfComments());
		
		// Should take the 1st switch case.
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
		checks.visitToken(mockAST);
		assertEquals(1, checks.getNumberOfComments());
		
		// Should take the 2nd switch case.
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
		DetailAST mockASTChild = Mockito.mock(DetailAST.class);
		Mockito.when(mockAST.getLineNo()).thenReturn(1);
		Mockito.when(mockASTChild.getLineNo()).thenReturn(4);
		Mockito.when(mockAST.findFirstToken(TokenTypes.BLOCK_COMMENT_END)).thenReturn(mockASTChild);
		checks.visitToken(mockAST);
		assertEquals (2, checks.getNumberOfComments());
	}
}