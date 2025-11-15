package dev.sadik.GraphX;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;
import static dev.sadik.GraphX.AppController.*;
import dev.sadik.GraphX.GraphingCalculator;

public class GraphController extends Parser {
    private AppController controller;

    public GraphController() {
    }

    public GraphController(AppController controller) {
        this.controller = controller;
    }

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

    //adds data in the series and then in graph
    protected void plotLine(String equation) {
        XYChart.Series<Double,Double> series = new XYChart.Series<Double, Double>();
        String variable;
        XYChart.Data<Double, Double> data;
        boolean isFunctionOfY = false;
        // only applicable for explicit functions
        // equations written in f(x) form
        if(!equation.contains("=")) {
            variable = detectVariable(equation);
            for(double x = -range, y; x <= range; x += STEP_SIZE) {
                y = parseEquation(x, equation, variable);
                data = new XYChart.Data<Double, Double>(x,y);
                series.getData().add(data);
            }
            controller.setEquationGraph(series);
        }
        //equation written in y=f(x), f(x) = y form or similarly using other variables
        else {
            String leftHandSide = equation.substring(0, equation.indexOf("=")).trim().toLowerCase();
            String rightHandSide = equation.substring(equation.indexOf("=")+1).trim().toLowerCase();

            String expression, implicitEquation, fullEquation;

            implicitEquation = "(" + leftHandSide + ") - (" + rightHandSide + ")";
            fullEquation = leftHandSide + rightHandSide;
            boolean hasX = fullEquation.contains("x");
            boolean hasY = fullEquation.contains("y");

            boolean leftHasSingleVar = leftHandSide.equals("x") || leftHandSide.equals("y");
            boolean rightHasSingleVar = rightHandSide.equals("x") || rightHandSide.equals("y");
            boolean isExplicit = leftHasSingleVar || rightHasSingleVar;
            boolean hasBothVariable = hasX && hasY;

            // check if same variable appears on both sides
            boolean sameVarBothSides = hasSameVariableBothSides(leftHandSide, rightHandSide);

            boolean isImplicit = !isExplicit && hasBothVariable && !sameVarBothSides;

            // handle equations with same variable on both sides like x^2 = x^3, sin(x) = cos(x) etc.
            if (sameVarBothSides) {
                variable = detectPrimaryVariable(leftHandSide, rightHandSide);
                plotSameVariableEquation(implicitEquation, variable);
            }
            // for F(x,y) = 0 type function
            else if(isImplicit) {
                plotImplicitFunction(implicitEquation);
            }
            // y=f(x), x=f(y), f(x), z=f(t).....t=f(z) etc.
            else {
                //x is dependent and y independent
                if(leftHandSide.equals("x") || rightHandSide.equals("x")) {
                    variable = "y";
                    expression = findExpression("x", leftHandSide, rightHandSide);
                    isFunctionOfY = true;
                }
                // y is dependent and x is independent
                else if(leftHandSide.equals("y") || rightHandSide.equals("y")) {
                    variable = "x";
                    expression = findExpression("y", leftHandSide, rightHandSide);
                }
                // failsafe when no other case matches
                else {
                    //assumes the variable is in right hand side
                    if(leftHandSide.length() < rightHandSide.length()) {
                        variable = detectVariable(rightHandSide);
                        expression = rightHandSide;
                    }
                    //variable not in right hand side
                    else {
                        variable = detectVariable(leftHandSide);
                        expression = leftHandSide;
                    }
                }

                if(isFunctionOfY) {
                    XYChart.Series<Double, Double> currentSeries = new XYChart.Series<>();
                    double prevX = Double.NaN;
                    double lastX = Double.NaN;
                    for(double y= -range, x; y<=range; y+= STEP_SIZE) {
                        // get the current x
                        x = parseEquation(y, expression, variable);
                        // skip non-real numbers
                        if(Double.isInfinite(x) || Double.isNaN(x)) continue;
                        // if break point is found
                        if(!Double.isNaN(prevX) && Math.signum(x - lastX) != Math.signum(lastX - prevX) && Math.signum(lastX - prevX) != 0) {
                            controller.setEquationGraph(currentSeries);
                            currentSeries = new XYChart.Series<>();
                            // add the last point to the series
                            currentSeries.getData().add(new XYChart.Data<>(lastX, y - STEP_SIZE));
                        }
                        currentSeries.getData().add(new XYChart.Data<>(x, y));
                        prevX = lastX;
                        lastX = x;
                    }
                    controller.setEquationGraph(currentSeries); // add the final points
                }
                else {
                    for(double x = -range, y; x<=range; x+=STEP_SIZE) {
                        y = parseEquation(x, expression, variable);
                        series.getData().add(new XYChart.Data<>(x, y));
                    }
                    controller.setEquationGraph(series);
                }
            }
        }
    }

    //plots equations where the same variable appears on both sides
    protected void plotSameVariableEquation(String implicitEquation, String variable) {
        final double SEARCH_STEP = 0.1;
        List<Double> roots = new ArrayList<>();

        // Find all roots where the equation equals zero
        for (double v = -range; v < range; v += SEARCH_STEP) {
            double f1 = parseEquation(v, implicitEquation, variable);
            double f2 = parseEquation(v + SEARCH_STEP, implicitEquation, variable);

            // Skip invalid values
            if (Double.isNaN(f1) || Double.isInfinite(f1) ||
                    Double.isNaN(f2) || Double.isInfinite(f2)) {
                continue;
            }

            // Check for sign change (root exists)
            if (Math.signum(f1) != Math.signum(f2)) {
                double root = bisectionSearchSingleVar(v, v + SEARCH_STEP, implicitEquation, variable);
                if (!Double.isNaN(root)) {
                    roots.add(root);
                }
            }
            // Check if already very close to zero
            else if (Math.abs(f1) < TOLERANCE) {
                roots.add(v);
            }
        }

        // Plot the roots as points with vertical lines
        if (variable.equals("x")) {
            // For x variable, plot vertical lines at each root
            for (Double root : roots) {
                XYChart.Series<Double, Double> lineSeries = new XYChart.Series<>();
                lineSeries.getData().add(new XYChart.Data<>(root, -range));
                lineSeries.getData().add(new XYChart.Data<>(root, range));
                controller.setEquationGraph(lineSeries);
            }
        } else {
            // For y variable, plot horizontal lines at each root
            for (Double root : roots) {
                XYChart.Series<Double, Double> lineSeries = new XYChart.Series<>();
                lineSeries.getData().add(new XYChart.Data<>(-range, root));
                lineSeries.getData().add(new XYChart.Data<>(range, root));
                controller.setEquationGraph(lineSeries);
            }
        }
    }

    protected void plotImplicitFunction(String implicitEquation) {
        final double SEARCH_RANGE_MIN = -range;
        final double SEARCH_RANGE_MAX = range;
        final double Y_SAMPLE_STEP = 0.2; // Sample the y-range to find sign changes

        for(double x = SEARCH_RANGE_MIN; x <= SEARCH_RANGE_MAX; x += STEP_SIZE) {
            // Find ALL roots for this x value by sampling the entire y-range
            List<Double> roots = findAllRoots(x, SEARCH_RANGE_MIN, SEARCH_RANGE_MAX,
                    implicitEquation, Y_SAMPLE_STEP);

            // add all found roots as individual points
            for(Double y_root : roots) {
                // check if this point should be connected to previous series
                boolean connected = false;

                for(XYChart.Series<Double, Double> existingSeries : controller.getEquationGraphData()) {
                    if(existingSeries.getData().isEmpty()) continue;

                    XYChart.Data<Double, Double> lastPoint =
                            existingSeries.getData().get(existingSeries.getData().size() - 1);

                    // if the last point is very close in x and y, add to that series
                    if(Math.abs(lastPoint.getXValue() - x) < STEP_SIZE * 2 &&
                            Math.abs(lastPoint.getYValue() - y_root) < 0.5) {
                        existingSeries.getData().add(new XYChart.Data<>(x, y_root));
                        connected = true;
                        break;
                    }
                }

                // if not connected to existing series, start a new one
                if(!connected) {
                    XYChart.Series<Double, Double> newSeries = new XYChart.Series<>();
                    newSeries.getData().add(new XYChart.Data<>(x, y_root));
                    controller.setEquationGraph(newSeries);
                }
            }
        }
    }
}
