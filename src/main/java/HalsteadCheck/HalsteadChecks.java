package HalsteadCheck;

import java.util.*;
import com.puppycrawl.tools.checkstyle.api.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class HalsteadChecks extends AbstractCheck {
	
	private int operatorsCount;
	public int getOperatorsCount() { return operatorsCount; }
	public void addOperatorsCount() { this.operatorsCount += 1; }
	
	private int operandsCount;
	public int getOperandsCount() { return operandsCount; } 
	public void addOperandsCount() { this.operandsCount += 1; }
	
	private int halsteadLength;
	public int getHalsteadLength() { return halsteadLength; } 
	public void setHalsteadLength() {  
		halsteadLength = getOperatorsCount() + getOperandsCount(); 
	}

	private int uniqueOperatorsCount;
	public int getUniqueOperatorsCount() { return uniqueOperatorsCount; }
	public void setUniqueOperatorsCount(int size) { this.uniqueOperatorsCount = size; }
	
	private int uniqueOperandsCount;
	public int getUniqueOperandsCount() { return uniqueOperandsCount; }
	public void setUniqueOperandsCount(int size) {
		this.uniqueOperandsCount = size;
	}
	
	private int halsteadVocabulary;
	public int getHalsteadVoculary() { return halsteadVocabulary; }
	public void setHalsteadVocabulary() { 
		halsteadVocabulary = getUniqueOperatorsCount() + getUniqueOperandsCount(); 
	}
	
	private double halsteadVolume;
	public double getHalsteadVolume() { return halsteadVolume; }
	public void setHalsteadVolume() {
		halsteadVolume = getHalsteadLength() * Math.log(getHalsteadVoculary()) / Math.log(2);
	}
	
	private double halsteadDifficulty;
	public double getHalsteadDifficulty() { return halsteadDifficulty; }
	public void setHalsteadDifficulty() {
		halsteadDifficulty = ((double) getUniqueOperatorsCount() / 2) * 
				((double) getOperandsCount() / (double) getUniqueOperandsCount());
	}
	
	private double halsteadEffort;
	public double getHalsteadEffort() { return halsteadEffort; }
	public void setHalsteadEffort() {
		halsteadEffort = getHalsteadDifficulty() * getHalsteadVolume();
	}
	
	private int expressionCount;
	public int getExpressionCount() { return expressionCount; }
	public void addExpressionCount() { this.expressionCount += 1; }
	
	@Override
	public int[] getDefaultTokens() { return TokenTypeList(); }
	
	@Override
	public int[] getRequiredTokens() { return new int[0]; }

	@Override
	public int[] getAcceptableTokens() { return TokenTypeList(); }
	
	@Override
	public void beginTree(DetailAST ast) { initializeVariables(); }
	
	private void initializeVariables() {
		operatorsCount = 0;
		operandsCount = 0;
		halsteadLength = 0;
		uniqueOperatorsCount = 0;
		uniqueOperandsCount = 0;
		halsteadVocabulary = 0;
		halsteadVolume = 0.0;
		halsteadDifficulty = 0.0;
		halsteadEffort = 0.0;
		expressionCount = 0;
		uniqueOperandList.clear();
		uniqueOperatorList.clear();
	}
	
	private List<Integer> uniqueOperatorList = new ArrayList<Integer>();
	private List<String> uniqueOperandList = new ArrayList<String>();
	
	@Override
	public void visitToken(DetailAST ast) {
		if (isOperator(ast.getType())) {
			addOperatorsCount();
			if (!uniqueOperatorList.contains(ast.getType())) {
				uniqueOperatorList.add(ast.getType());
				setUniqueOperatorsCount(uniqueOperatorList.size());
			}
		}
		else if (isOperand(ast.getType())) {
			addOperandsCount();
			if (!uniqueOperandList.contains(ast.getText())) {
				uniqueOperandList.add(ast.getText());
				setUniqueOperandsCount(uniqueOperandList.size());
			}
		}
		else if (ast.getType() == TokenTypes.EXPR) {
			addExpressionCount();
		}
	}
	
	public boolean isOperator(int astType) {
		for (int token : operatorTokenTypeList()) {
			if (astType == token) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isOperand(int astType) {
		for (int token : operandTokenTypeList()) {
			if (astType == token) {
				return true;
			}
		}
		return false;
	}
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	@Override
	public void finishTree(DetailAST ast) {
		setHalsteadLength();
		setHalsteadVocabulary();
		setHalsteadVolume();
		setHalsteadDifficulty();
		setHalsteadEffort();
		
		log(ast.getLineNo(), "The Halstead length is: " + getHalsteadLength());
		log(ast.getLineNo(), "The operator count is: " + getOperatorsCount());
		log(ast.getLineNo(), "The operand count is: " + getOperandsCount());
		log(ast.getLineNo(), "The Halstead vocabulary is: " + getHalsteadVoculary());
		log(ast.getLineNo(), "The unique operator count is: " + getUniqueOperatorsCount());
		log(ast.getLineNo(), "The unique operand count is: " + getUniqueOperandsCount());
		log(ast.getLineNo(), "The Halstead volume is: " + df.format(getHalsteadVolume()));
		log(ast.getLineNo(), "The Halstead difficulty is: " + df.format(getHalsteadDifficulty()));
		log(ast.getLineNo(), "The Halstead effort is: " + df.format(getHalsteadEffort()));
		log(ast.getLineNo(), "The expressions count is: " + getExpressionCount());

	}

	public int[] operatorTokenTypeList() {
		return new int[] {
				TokenTypes.ASSIGN,
				TokenTypes.BAND,
				TokenTypes.BAND_ASSIGN,
				TokenTypes.BNOT,
				TokenTypes.BOR,
				TokenTypes.BOR_ASSIGN,
				TokenTypes.BSR,
				TokenTypes.BSR_ASSIGN,
				TokenTypes.BXOR,
				TokenTypes.BXOR_ASSIGN,
				TokenTypes.COLON,
				TokenTypes.COMMA,
				TokenTypes.DEC,
				TokenTypes.DIV,
				TokenTypes.DIV_ASSIGN,
				TokenTypes.DOT,
				TokenTypes.EQUAL,
				TokenTypes.GE,
				TokenTypes.GT,
				TokenTypes.INC,
				TokenTypes.INDEX_OP,
				TokenTypes.LAND,
				TokenTypes.LE,
				TokenTypes.LITERAL_INSTANCEOF,
				TokenTypes.LNOT,
				TokenTypes.LOR,
				TokenTypes.LT,
				TokenTypes.MINUS,
				TokenTypes.MINUS_ASSIGN,
				TokenTypes.MOD,
				TokenTypes.MOD_ASSIGN,
				TokenTypes.NOT_EQUAL,
				TokenTypes.PLUS,
				TokenTypes.PLUS_ASSIGN,
				TokenTypes.POST_DEC,
				TokenTypes.POST_INC,
				TokenTypes.QUESTION,
				TokenTypes.SL,
				TokenTypes.SL_ASSIGN,
				TokenTypes.SR,
				TokenTypes.SR_ASSIGN,
				TokenTypes.STAR,
				TokenTypes.STAR_ASSIGN,
				TokenTypes.UNARY_MINUS,
				TokenTypes.UNARY_PLUS
				};
	}
	
	public int[] operandTokenTypeList() {
		return new int[] {
				TokenTypes.IDENT,
				TokenTypes.LITERAL_CHAR,
				TokenTypes.LITERAL_SHORT,
				TokenTypes.LITERAL_INT,
				TokenTypes.LITERAL_FLOAT,
				TokenTypes.LITERAL_LONG,
				TokenTypes.LITERAL_DOUBLE,
				TokenTypes.NUM_DOUBLE, 
				TokenTypes.NUM_FLOAT, 
				TokenTypes.NUM_INT, 
				TokenTypes.NUM_LONG,
				TokenTypes.STRING_LITERAL
		};
	}
	
	public int[] TokenTypeList() {
		return new int[] {
				TokenTypes.ASSIGN,
				TokenTypes.BAND,
				TokenTypes.BAND_ASSIGN,
				TokenTypes.BNOT,
				TokenTypes.BOR,
				TokenTypes.BOR_ASSIGN,
				TokenTypes.BSR,
				TokenTypes.BSR_ASSIGN,
				TokenTypes.BXOR,
				TokenTypes.BXOR_ASSIGN,
				TokenTypes.COLON,
				TokenTypes.COMMA,
				TokenTypes.DEC,
				TokenTypes.DIV,
				TokenTypes.DIV_ASSIGN,
				TokenTypes.DOT,
				TokenTypes.EQUAL,
				TokenTypes.EXPR,
				TokenTypes.GE,
				TokenTypes.GT,
				TokenTypes.INC,
				TokenTypes.INDEX_OP,
				TokenTypes.LAND,
				TokenTypes.LE,
				TokenTypes.LITERAL_INSTANCEOF,
				TokenTypes.LNOT,
				TokenTypes.LOR,
				TokenTypes.LT,
				TokenTypes.MINUS,
				TokenTypes.MINUS_ASSIGN,
				TokenTypes.MOD,
				TokenTypes.MOD_ASSIGN,
				TokenTypes.NOT_EQUAL,
				TokenTypes.PLUS,
				TokenTypes.PLUS_ASSIGN,
				TokenTypes.POST_DEC,
				TokenTypes.POST_INC,
				TokenTypes.QUESTION,
				TokenTypes.SL,
				TokenTypes.SL_ASSIGN,
				TokenTypes.SR,
				TokenTypes.SR_ASSIGN,
				TokenTypes.STAR,
				TokenTypes.STAR_ASSIGN,
				TokenTypes.UNARY_MINUS,
				TokenTypes.UNARY_PLUS,
				TokenTypes.IDENT,
				TokenTypes.LITERAL_CHAR,
				TokenTypes.LITERAL_SHORT,
				TokenTypes.LITERAL_INT,
				TokenTypes.LITERAL_FLOAT,
				TokenTypes.LITERAL_LONG,
				TokenTypes.LITERAL_DOUBLE,
				TokenTypes.NUM_DOUBLE, 
				TokenTypes.NUM_FLOAT, 
				TokenTypes.NUM_INT, 
				TokenTypes.NUM_LONG,
				TokenTypes.STRING_LITERAL
		};
	}
}
