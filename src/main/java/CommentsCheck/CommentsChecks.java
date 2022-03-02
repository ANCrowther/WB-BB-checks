package CommentsCheck;

import java.util.Hashtable;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentsChecks extends AbstractCheck {
	
	private int numberOfComments;
	public int getNumberOfComments() { return numberOfComments; }
	public void addNumberOfComments() { this.numberOfComments += 1; }
	
	private int numberOfCommentLinesCount;
	public int getNumberOfCommentLinesCount() { return numberOfCommentLinesCount; }
	public void addNumberOfCommentLinesCount() { this.numberOfCommentLinesCount += 1; }
	public void addNumberOfCommentLinesCount(DetailAST ast) {
		this.numberOfCommentLinesCount += ast.findFirstToken(TokenTypes.BLOCK_COMMENT_END).getLineNo() - ast.getLineNo() + 1;
	}
	
	@Override
	public int[] getDefaultTokens() { 
		return operatorTokenTypeList(); 
	}
	
	@Override
	public int[] getRequiredTokens() { return new int[0]; }

	@Override
	public int[] getAcceptableTokens() { return operatorTokenTypeList(); }
	
	private int[] operatorTokenTypeList() {
		return new int[] {
				TokenTypes.SINGLE_LINE_COMMENT,
				TokenTypes.BLOCK_COMMENT_BEGIN,
				TokenTypes.BLOCK_COMMENT_END
		};
	}
	
	@Override
	public void visitToken(DetailAST ast) {
		switch(ast.getType()) {
		case TokenTypes.SINGLE_LINE_COMMENT:
			addNumberOfComments();
			addNumberOfCommentLinesCount();
			break;
		case TokenTypes.BLOCK_COMMENT_BEGIN:
			addNumberOfComments();
			addNumberOfCommentLinesCount(ast);
			break;
		}
	}
	
	@Override
	public void finishTree(DetailAST ast) {
		log(ast.getLineNo(), "Total number of comments: " + getNumberOfComments());
		log(ast.getLineNo(), "Total lines of comments:  " + getNumberOfCommentLinesCount());
	}
	
	Hashtable<String,Integer> results = new Hashtable<String,Integer>();
	
	public Hashtable<String,Integer> getResults()
	{
		try {
			results.put("Total number of comments: ", getNumberOfComments());
			results.put("Total lines of comments:  ", getNumberOfCommentLinesCount());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }
}