package dev.sadik.GraphX;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import java.util.ArrayList;
import java.util.List;
import static dev.sadik.GraphX.AppController.*;

public class Parser {
    //inverse cot function implementation
    private Function acot = new Function("acot", 1) {
        @Override
        public double apply(double... args) {
            return Math.atan(1/args[0]);
        }
    };

    // inverse hyperbolic coth function generation
    private Function coth = new Function("coth", 1) {
        @Override
        public double apply(double... args) {
            return Math.cosh(args[0]) / Math.sinh(args[0]);
        }
    };

    //pareses the equation and gets the result of the equation
    protected double parseEquation(double currentValue, String equation, String variable) {
        Expression e = new ExpressionBuilder(equation)
                .variables(variable)
                .functions(acot, coth)
                .build()
                .setVariable(variable, currentValue);
        return e.evaluate();
    }

    //parse double variable equation and get result
    protected double parseEquation(double val1, double val2, String equation, String var1, String var2) {
        Expression e = new ExpressionBuilder(equation)
                .variables(var1, var2)
                .functions(acot, coth)
                .build()
                .setVariable(var1, val1)
                .setVariable(var2,val2);

        return e.evaluate();
    }

    // bisection search for single-variable equations
    protected double bisectionSearchSingleVar(double min, double max, String equation, String variable) {
        double f_min = parseEquation(min, equation, variable);
        double f_max = parseEquation(max, equation, variable);

        // Check if interval straddles a root
        if (Math.signum(f_min) == Math.signum(f_max)) {
            if (Math.abs(f_min) < TOLERANCE) return min;
            if (Math.abs(f_max) < TOLERANCE) return max;
            return Double.NaN;
        }

        double a = min;
        double b = max;
        double c = 0;
        double f_c;

        int i = 0;
        while (i < MAX_ITERATIONS) {
            c = (a + b) / 2;
            f_c = parseEquation(c, equation, variable);

            if (Math.abs(f_c) < TOLERANCE || (b - a) / 2 < TOLERANCE) {
                return c;
            }

            if (Math.signum(f_c) == Math.signum(f_min)) {
                a = c;
                f_min = f_c;
            } else {
                b = c;
            }
            i++;
        }
        return c;
    }

    /**
     * Bisection method to find a root (y value) for G(y) = F(x_const, y) = 0
     * within the given y range.
     * @param x_const The fixed x value.
     * @param y_min Lower bound of y range.
     * @param y_max Upper bound of y range.
     * @param equation The implicit equation (RHS of F(x,y)=0).
     * @return The y root, or Double.NaN if no root is found in the range.
     */

    protected double bisectionSearch(double x_const, double y_min, double y_max, String equation) {
        double f_min = parseEquation(x_const, y_min, equation, "x", "y");
        double f_max = parseEquation(x_const, y_max, equation, "x", "y");

        // if the interval doesn't straddle a root, or either end is already close to zero
        if (Math.signum(f_min) == Math.signum(f_max)) {
            if (Math.abs(f_min) < TOLERANCE) return y_min;
            if (Math.abs(f_max) < TOLERANCE) return y_max;
            return Double.NaN;
        }

        double a = y_min;
        double b = y_max;
        double c = 0;
        double f_c;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            c = (a + b) / 2;
            f_c = parseEquation(x_const, c, equation, "x", "y");

            if (Math.abs(f_c) < TOLERANCE || (b - a) / 2 < TOLERANCE) {
                return c; // Root found
            }

            if (Math.signum(f_c) == Math.signum(f_min)) {
                a = c;
                f_min = f_c;
            } else {
                b = c;
            }
        }
        return c; // Best estimate after max iterations
    }

    protected List<Double> findAllRoots(double x_const, double y_min, double y_max,
                                      String equation, double sampleStep) {
        List<Double> roots = new ArrayList<>();

        // sample the range to find all sign changes
        for (double y = y_min; y < y_max; y += sampleStep) {
            try {
                double f1 = parseEquation(x_const, y, equation, "x", "y");
                double f2 = parseEquation(x_const, y + sampleStep, equation, "x", "y");

                if (Double.isNaN(f1) || Double.isInfinite(f1) ||
                        Double.isNaN(f2) || Double.isInfinite(f2)) {
                    continue;
                }

                if (Math.signum(f1) != Math.signum(f2)) {
                    double root = bisectionSearch(x_const, y, y + sampleStep, equation);
                    if (!Double.isNaN(root)) {
                        roots.add(root);
                    }
                } else if (Math.abs(f1) < TOLERANCE) {
                    roots.add(y);
                }
            } catch (NullPointerException |
                     ArithmeticException |
                     IllegalArgumentException e) {
                // Log or skip invalid computation
                System.err.println("Computation skipped at y=" + y + ": " + e.getMessage());
            } catch (Exception e) {
                // Catch any unexpected exceptions to prevent loop termination
                System.err.println("Unexpected error at y=" + y + ": " + e);
            }
        }
        return roots;
    }
}
