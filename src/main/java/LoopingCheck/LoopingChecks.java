package LoopingCheck;

import java.util.Hashtable;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LoopingChecks extends AbstractCheck {
	
	private int loopingStatementCount;
	public int getLoopingStatementCount() { return loopingStatementCount; }
	public void addLoopingStatementCount() { this.loopingStatementCount += 1; }
	
	@Override
	public int[] getDefaultTokens() { return tokenList(); }
	
	@Override
	public int[] getRequiredTokens() { return new int[0]; }

	@Override
	public int[] getAcceptableTokens() { return tokenList(); }
	
	@Override
	public void beginTree(DetailAST ast) { loopingStatementCount = 0; }
	
	@Override
	public void visitToken(DetailAST ast) {
		if (isLoopingStatement(ast.getType())) {
			addLoopingStatementCount();
		}
	}
	
	public boolean isLoopingStatement(int astType) {
		for (int token : tokenList()) {
			if (astType == token) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void finishTree(DetailAST ast) {
		log(ast, "Number of looping statements: " + getLoopingStatementCount());
	}
	
	private int[] tokenList() {
		return new int[] {
				TokenTypes.LITERAL_DO,
				TokenTypes.LITERAL_FOR,
				TokenTypes.LITERAL_WHILE
		};
	}
}