package DEV3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.JavaParser.Options;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

import CommentsCheck.CommentsChecks;
import HalsteadCheck.HalsteadChecks;
import CtorCheck.MissingCtorCheck;

public class Dev3BlackBoxTester {
	private DetailAST rootAST;
	private AbstractCheck checker;
	
	public Dev3BlackBoxTester(String filePath, AbstractCheck check) throws CheckstyleException {
		this.checker = check;
		rootAST = createDetailAST(filePath);
	}
	
	public DetailAST createDetailAST(String filePath) throws CheckstyleException {
		File file = new File(filePath);
		DetailAST tempAST = null;
		tempAST = buildFile(file, tempAST);
		return tempAST;
	}

	public DetailAST buildFile(File file, DetailAST a) throws CheckstyleException {
		try {
			FileText text = new FileText(file, "UTF-8");
			FileContents contents = new FileContents(text);
			checker.setFileContents(contents);
			
			if (checker.isCommentNodesRequired()) {
				a = JavaParser.parseFile(file, Options.WITH_COMMENTS);
			} else {
				a = JavaParser.parse(contents);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public void configureCheck() throws CheckstyleException {
		checker.configure(new DefaultConfiguration("Local"));
		checker.contextualize(new DefaultContext());
	}
	
	public void walk() throws CheckstyleException {
		checker.beginTree(rootAST);
		helper(rootAST);
		if (checker.getClass() == HalsteadChecks.class) {
			checker.finishTree(rootAST);
		}
	}
	
	public void helper(DetailAST a) {
		int[] tokenList = checker.getAcceptableTokens();
		DetailAST currentNode = a;
		
		while(currentNode != null) {
			for(int index = 0; index < tokenList.length; index++) {
				if (tokenList[index] == currentNode.getType()) {
					checker.visitToken(currentNode);
				}
			}
			DetailAST nextNode = currentNode.getFirstChild();
			while(currentNode != null && nextNode == null) {
				checker.leaveToken(currentNode);
				nextNode = currentNode.getNextSibling();
				currentNode = currentNode.getParent();
			}
			currentNode = nextNode;
		}
	}
}

