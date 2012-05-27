package geogebra.common.kernel.parser.cashandlers;

import geogebra.common.plugin.Operation;
import geogebra.common.util.StringUtil;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Handles function references for Parser
 * @author zbynek
 *
 */
public class ParserFunctions {
private static final List<Map<String,Operation>> stringToOp = new ArrayList<Map<String,Operation>>();

/**
 * Names that cannot be used for elements because of collision with predefined functions
 * these should also be documented here:
 * http://wiki.geogebra.org/en/Manual:Naming_Objects
 */
public static final Set<String> RESERVED_FUNCTION_NAMES = new TreeSet<String>();
/**
 * Same as RESERVED_FUNCTION_NAMES, but in lower case
 */
public static final Set<String> RESERVED_FUNCTION_LOWERCASE = new TreeSet<String>();

static {
	for(int i=0;i<4;i++)
		stringToOp.add(new TreeMap<String,Operation>());
	put(1,"sin", Operation.SIN);
	put(1,"Sin", Operation.SIN);

	put(1,"cos", Operation.COS);
	put(1,"Cos", Operation.COS);
	
	put(1,"tan", Operation.TAN);
	put(1,"Tan", Operation.TAN);
	
	put(1,"csc", Operation.CSC);
	put(1,"Csc", Operation.CSC);
	put(1,"cosec", Operation.CSC);
	put(1,"Cosec", Operation.CSC);
	
	put(1,"sec", Operation.SEC);
	put(1,"Sec", Operation.SEC);
	
	put(1,"cot", Operation.COT);
	put(1,"Cot", Operation.COT);
	
	put(1,"csch", Operation.CSCH);
	put(1,"Csch", Operation.CSCH);
	
	put(1,"sech", Operation.SECH);
	put(1,"Sech", Operation.SECH);
	
	put(1,"coth", Operation.COTH);
	put(1,"Coth", Operation.COTH);
	
	put(1,"acos", Operation.ARCCOS);
	put(1,"arccos", Operation.ARCCOS);
	put(1,"arcos", Operation.ARCCOS);
	put(1,"ArcCos", Operation.ARCCOS);
	
	put(1,"asin", Operation.ARCSIN);
	put(1,"arcsin", Operation.ARCSIN);
	put(1,"ArcSin", Operation.ARCSIN);
	
	put(1,"atan", Operation.ARCTAN);
	put(1,"arctan", Operation.ARCTAN);
	put(1,"ArcTan", Operation.ARCTAN);
	
	put(1,"atan2", Operation.ARCTAN2);
	put(1,"arctan2", Operation.ARCTAN2);
	put(1,"ArcTan2", Operation.ARCTAN2);
	
	put(1,"erf", Operation.ERF);
	put(1,"Erf", Operation.ERF);
	
	put(1,"psi", Operation.PSI);
	
	put(1,"polygamma", Operation.POLYGAMMA);
	
	put(1,"cosh", Operation.COSH);
	put(1,"Cosh", Operation.COSH);
	
	put(1,"sinh", Operation.SINH);
	put(1,"Sinh", Operation.SINH);
	
	put(1,"tanh", Operation.TANH);
	put(1,"Tanh", Operation.TANH);
	
	put(1,"acosh", Operation.ACOSH);
	put(1,"Acosh", Operation.ACOSH);
	
	put(1,"asinh", Operation.ASINH);
	put(1,"Asinh", Operation.ASINH);
	
	put(1,"atanh", Operation.ATANH);
	put(1,"Atanh", Operation.ATANH);
	
	put(1,"exp", Operation.EXP);
	put(1,"Exp", Operation.EXP);
	
	put(1,"log", Operation.LOG);
	put(1,"ln", Operation.LOG);
	put(1,"Ln", Operation.LOG);
	
	put(2,"log", Operation.LOGB);
	put(2,"ln", Operation.LOGB);
	put(2,"Ln", Operation.LOGB);
	
	put(1,"ld", Operation.LOG2);
	put(1,"log2", Operation.LOG2);
	
	put(1,"lg", Operation.LOG10);
	put(1,"log10", Operation.LOG10);
	
	put(2,"beta", Operation.BETA);
	put(2,"Beta", Operation.BETA);
	
	put(3,"beta", Operation.BETA_INCOMPLETE);
	put(3,"Beta", Operation.BETA_INCOMPLETE);
	
	put(3,"betaRegularized", Operation.BETA_INCOMPLETE_REGULARIZED);
	put(3,"ibeta", Operation.BETA_INCOMPLETE_REGULARIZED);
	
	put(1,"gamma", Operation.GAMMA);
	put(1,"igamma", Operation.GAMMA);
	put(1,"Gamma", Operation.GAMMA);
	
	put(2,"gamma", Operation.GAMMA_INCOMPLETE);
	put(2,"igamma", Operation.GAMMA_INCOMPLETE);
	put(2,"Gamma", Operation.GAMMA_INCOMPLETE);
	
	put(1,"gammaRegularized", Operation.GAMMA_INCOMPLETE_REGULARIZED);
	
	put(1,"cosIntegral", Operation.CI);
	put(1,"CosIntegral", Operation.CI);
	
	put(1,"sinIntegral", Operation.SI);
	put(1,"SinIntegral", Operation.SI);
	
	put(1,"expIntegral", Operation.EI);
	put(1,"ExpIntegral", Operation.EI);
	
	put(1,"gGbInTeGrAl", Operation.INTEGRAL);
	put(1,"gGbSuBsTiTuTiOn", Operation.SUBSTITUTION);
	
	put(1,"arbint", Operation.ARBINT);
	
	put(1,"arbconst", Operation.ARBCONST);
	
	put(1,"arbcomplex", Operation.ARBCOMPLEX);
	
	put(1,"sqrt", Operation.SQRT);
	put(1,"Sqrt", Operation.SQRT);
	
	put(1,"cbrt", Operation.CBRT);
	put(1,"Cbrt", Operation.CBRT);
	
	put(1,"abs", Operation.ABS);
	put(1,"Abs", Operation.ABS);
	
	put(1,"sgn", Operation.SGN);
	put(1,"sign", Operation.SGN);
	put(1,"Sign", Operation.SGN);
	
	put(1,"floor", Operation.FLOOR);
	put(1,"Floor", Operation.FLOOR);
	
	put(1,"ceil", Operation.CEIL);
	put(1,"Ceil", Operation.CEIL);
	
	put(1,"conjugate", Operation.CONJUGATE);
	put(1,"Conjugate", Operation.CONJUGATE);
	
	put(1,"arg", Operation.ARG);
	put(1,"Arg", Operation.ARG);
	
}
/**
 * @param s function name
 * @param size number of arguments
 * @return operation
 */
public static Operation get(String s,int size){
	return stringToOp.get(size).get(s);
}
private static void put(int size, String name, Operation op) {
	RESERVED_FUNCTION_NAMES.add(name);
	RESERVED_FUNCTION_LOWERCASE.add(StringUtil.toLowerCase(name));
	stringToOp.get(size).put(name,op);
	
}
}

