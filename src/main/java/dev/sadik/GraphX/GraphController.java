package dev.sadik.GraphX;

public class GraphController extends Parser {
    //finds the dependent equation based on independent variable
    protected String findExpression(String variable, String exp1, String exp2) {
        return exp1.equals(variable) ? exp2 : exp1;
    }

    //checks if both sides of an equation contain the same variable(s)
    protected boolean hasSameVariableBothSides(String leftSide, String rightSide) {
        boolean leftHasX = leftSide.contains("x");
        boolean rightHasX = rightSide.contains("x");
        boolean leftHasY = leftSide.contains("y");
        boolean rightHasY = rightSide.contains("y");

        // Check if x appears on both sides
        boolean xOnBothSides = leftHasX && rightHasX && !leftHasY && !rightHasY;

        // Check if y appears on both sides
        boolean yOnBothSides = leftHasY && rightHasY && !leftHasX && !rightHasX;

        return xOnBothSides || yOnBothSides;
    }

    //detects the primary variable in an equation containing same variable on both sides
    protected String detectPrimaryVariable(String leftSide, String rightSide) {
        boolean leftHasX = leftSide.contains("x");
        boolean rightHasX = rightSide.contains("x");

        if (leftHasX || rightHasX) {
            return "x";
        }
        return "y";
    }

    //detects the primary variable in an equation containing differing variable on both sides
    protected String detectVariable(String equation) {
        String[] functions = {
                "sin", "cos", "tan", "cot", "asin", "acos", "atan",
                "acot", "sinh", "cosh", "tanh", "coth",
                "log", "log2", "log10", "sqrt", "cbrt", "exp", "abs", "signum"
        };

        for(String fn: functions) {
            equation = equation.replaceAll("\\b" + fn + "\\b", "");
        }

        equation = equation.replaceAll("[^a-zA-z]", "");

        if(equation.isEmpty()) return "x";
        else return String.valueOf(equation.charAt(0));
    }

}
