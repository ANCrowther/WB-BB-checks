package CommentsCheckTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import CommentsCheck.CommentsChecks;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class CommentsCheckTest {
	// Used to test results for acceptable & default tokens
	private int[] expectedTokenList = {
			TokenTypes.SINGLE_LINE_COMMENT,
			TokenTypes.BLOCK_COMMENT_BEGIN,
			TokenTypes.BLOCK_COMMENT_END
	};
	
	// Used to test results for required tokens
	private int[] expectedRequiredToken = new int[0];
	
	DetailAST mockAST;
	DetailAstImpl mockASTImpl;
	CommentsChecks checks;
	
	@BeforeEach
	public void setup() {
		checks = new CommentsChecks();
		mockAST = Mockito.mock(DetailAST.class);
		mockASTImpl = new DetailAstImpl();
	}
	
	@DisplayName("BB: testZeroNumberOfCommentsAndLines()")
	@Test
	public void testZeroNumberOfCommentsAndLines() {
		CommentsChecks checks = mock(CommentsChecks.class);
		
		assertEquals(0, checks.getNumberOfComments());
		assertEquals(0, checks.getNumberOfCommentLinesCount());
	}
	
	@DisplayName("BB: testBeginTree()")
	@Test
	public void testBeginTree() {
		checks.beginTree(mockAST);
		
		assertEquals(0, checks.getNumberOfComments());
		assertEquals(0, checks.getNumberOfCommentLinesCount());
	}
	
	@DisplayName("BB: testGetAcceptableTokens()")
	@Test
	public void testGetAcceptableTokens() {
		int[] actualTokenList = checks.getAcceptableTokens();
		Arrays.equals(expectedTokenList, actualTokenList);
	}
	
	@DisplayName("BB: testGetDefaultTokens()")
	@Test
	public void testGetDefaultTokens() {
		int[] actualTokenList = checks.getDefaultTokens();
		Arrays.equals(expectedTokenList, actualTokenList);
	}
	
	@DisplayName("BB: testGetRequiredTokens()")
	@Test
	public void testGetRequiredTokens() {
		int[] actualTokenList = checks.getRequiredTokens();
		Arrays.equals(expectedRequiredToken, actualTokenList);
	}
	
	@DisplayName("BB: testSetCommentsAndLineCounts()")
	@Test
	public void testSetCommentsAndLineCounts() {
		checks.addNumberOfComments();
		checks.addNumberOfCommentLinesCount();
		checks.addNumberOfCommentLinesCount();
		
		assertEquals(1, checks.getNumberOfComments());
		assertEquals(2, checks.getNumberOfCommentLinesCount());
	}
	
	@DisplayName("BB: testVisitTokenOneComment()")
	@Test
	public void testVisitTokenOneComment() {
		mockASTImpl.initialize(TokenTypes.SINGLE_LINE_COMMENT, "//Hi");
		mockAST = mockASTImpl;
		checks.visitToken(mockAST);
		
		assertEquals(1, checks.getNumberOfComments());
		assertEquals(1, checks.getNumberOfCommentLinesCount());
		assertNotEquals(0, checks.getNumberOfComments());
		assertNotEquals(2, checks.getNumberOfComments());
		assertNotEquals(0, checks.getNumberOfCommentLinesCount());
		assertNotEquals(2, checks.getNumberOfCommentLinesCount());
	}
	
	@DisplayName("BB: testVisitTokenMultipleComments()")
	@Test
	public void testVisitTokenMultipleComments() {
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
		checks.visitToken(mockAST);
		Mockito.when(mockAST.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
		DetailAST mockASTChild = Mockito.mock(DetailAST.class);
		
		// Mocking a 4 line block comment.
		Mockito.when(mockAST.getLineNo()).thenReturn(1);
		Mockito.when(mockASTChild.getLineNo()).thenReturn(4);
		Mockito.when(mockAST.findFirstToken(TokenTypes.BLOCK_COMMENT_END)).thenReturn(mockASTChild);
		checks.visitToken(mockAST);
		
		// Testing for 1 single line comment & 1 block comment with 4 lines.
		assertEquals (2, checks.getNumberOfComments());
		assertEquals (5, checks.getNumberOfCommentLinesCount());
		assertNotEquals(1, checks.getNumberOfComments());
		assertNotEquals(3, checks.getNumberOfComments());
		assertNotEquals(4, checks.getNumberOfCommentLinesCount());
		assertNotEquals(6, checks.getNumberOfCommentLinesCount());
	}
}