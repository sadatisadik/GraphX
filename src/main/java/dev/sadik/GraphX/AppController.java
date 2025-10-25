package dev.sadik.GraphX;

//this class is used for controlling various elements of the anchor-pane

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.io.IOException;
import java.lang.Double;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    // Tolerance for the root finding
    private static final double TOLERANCE = 1e-4;
    // Max iterations for bisection
    private static final int MAX_ITERATIONS = 50;
    final double range = 10;
    final double STEP_SIZE = 0.01;
    final int rows = 10;
    final int cols = 4;

    @FXML
    LineChart <Double,Double> equationGraph;

    @FXML
    TextField equationBox;

    @FXML
    Button generateButton;

    @FXML
    Button clearText;

    @FXML
    Button clearGraph;

    @FXML
    GridPane buttonSet;

    @FXML
    Button backspace;

    @FXML Button information;

    Function acot = new Function("acot", 1) {
        @Override
        public double apply(double... args) {
            return Math.atan(1/args[0]);
        }
    };

    Function coth = new Function("coth", 1) {
        @Override
        public double apply(double... args) {
            return Math.cosh(args[0]) / Math.sinh(args[0]);
        }
    };

    public void initialize() {
        String[][] functions = {
                {"1","1"}, {"2", "2"}, {"+", "+"}, {"-", "-"},
                {"3", "3"}, {"4", "4"}, {"\\times", "*"}, {"\\div", "/"},
                {"5","5"}, {"6", "6"}, {"(", "("}, {")", ")"},
                {"7", "7"}, {"8", "8"},  {"=", "="}, {"x", "x"},
                {"9", "9"}, {"0", "0"},{"|x|", "abs("}, {"\\Box^{a}","^"},
                {"y", "y"},                          // y
                {"\\sqrt{x}", "sqrt("},              // square root
                {"\\sqrt[3]{x}", "cbrt("},           // cubic root
                {"e^{x}", "exp("},                   // e^x (Euler's number power)
                {"\\ln(x)", "log("},                 // natural logarithm (base e)
                {"\\log_{10}(x)", "log10("},         // logarithm base 10
                {"\\log_{2}(x)", "log2("},           // logarithm base 2
                {"\\sin(x)", "sin("},                // sine
                {"\\cos(x)", "cos("},                // cosine
                {"\\tan(x)", "tan("},                // tangent
                {"\\cot(x)", "cot("},                // cotangent
                {"\\sin^{-1}(x)", "asin("},          // inverse sine
                {"\\cos^{-1}(x)", "acos("},          // inverse cosine
                {"\\tan^{-1}(x)", "atan("},          // inverse tangent
                {"\\cot^{-1}(x)", "acot("},          // inverse cotangent
                {"\\sinh(x)", "sinh("},              // hyperbolic sine
                {"\\cosh(x)", "cosh("},              // hyperbolic cosine
                {"\\tanh(x)", "tanh("},              // hyperbolic tangent
                {"\\coth(x)", "coth("},              // hyperbolic cotangent
                {"\\mathrm{sign}(x)", "signum("}     // signum function
        };

        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (index >= functions.length) break;
                String[] fn = functions[index++];
                Button b = ButtonController.createLatexButton(fn[0], 18);
                b.setOnAction(event -> insertTextAtCursor(fn[1]));
                if(fn[0].equals("=")) {
                    String baseStyle = "-fx-background-color: linear-gradient(to bottom, #79bbff 5%, #378de5 100%);" +
                            "-fx-background-radius: 6;" +
                            "-fx-border-radius: 6;" +
                            "-fx-border-color: #84bbf3;" +
                            "-fx-border-width: 1;" +
                            "-fx-font-family: Arial;" +
                            "-fx-font-size: 15px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 6 24 6 24;" +
                            "-fx-text-fill: white;";

                    b.setStyle(baseStyle);

                    // Inner shadow effect (similar to inset box-shadow)
                    InnerShadow shadow = new InnerShadow();
                    shadow.setOffsetY(1.0);
                    shadow.setColor(Color.web("#bbdaf7"));
                    b.setEffect(shadow);

                    // Hover effect
                    b.setOnMouseEntered(e -> b.setStyle(
                            "-fx-background-color: linear-gradient(to bottom, #378de5 5%, #79bbff 100%);" +
                                    "-fx-background-radius: 6;" +
                                    "-fx-border-radius: 6;" +
                                    "-fx-border-color: #84bbf3;" +
                                    "-fx-border-width: 1;" +
                                    "-fx-font-family: Arial;" +
                                    "-fx-font-size: 15px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-padding: 6 24 6 24;" +
                                    "-fx-text-fill: white;"
                    ));

                    b.setOnMouseExited(e -> b.setStyle(baseStyle));

                    // Pressed effect
                    b.setOnMousePressed(e -> b.setTranslateY(1));
                    b.setOnMouseReleased(e -> b.setTranslateY(0));
                }

                else if(fn[0].charAt(0) >= '0' && fn[0].charAt(0) <= '9') {
                    // Set text color and shadow
                    b.setTextFill(Color.web("#505739"));

                    // Base style
                    String baseStyle = "-fx-background-color: linear-gradient(to bottom, #eae0c2 5%, #ccc2a6 100%);";

                    b.setStyle(baseStyle);

                    // Hover effect
                    b.setOnMouseEntered(e -> b.setStyle(
                            "-fx-background-color: linear-gradient(to bottom, #ccc2a6 5%, #eae0c2 100%);"
                    ));
                    b.setOnMouseExited(e -> b.setStyle(baseStyle));

                    // Pressed effect (move down 1px)
                    b.setOnMousePressed(e -> b.setTranslateY(1));
                    b.setOnMouseReleased(e -> b.setTranslateY(0));
                }

                else if(fn[0].equals("+") || fn[0].equals("-") || fn[1].equals("*") || fn[1].equals("/")) {
                    String baseStyle = "-fx-background-color: FA6161;" +
                            "-fx-background-radius: 4;" +    // small rounding
                            "-fx-border-radius: 4;" +
                            "-fx-text-fill: #000000;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 16px;" +
                            "-fx-padding: 10 15;" +
                            "-fx-cursor: hand;" +
                            "-fx-border-width: 0;";

                    b.setStyle(baseStyle);

                    // Hover effect (yellow glow)
                    b.setOnMouseEntered(e -> {
                        b.setStyle("-fx-background-color: #D14B4B;" +
                                "-fx-background-radius: 4;" +
                                "-fx-border-radius: 4;" +
                                "-fx-text-fill: #000000;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 16px;" +
                                "-fx-padding: 10 15;" +
                                "-fx-cursor: hand;" +
                                "-fx-border-width: 0;");
                        DropShadow shadow = new DropShadow();
                        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
                        shadow.setRadius(8);
                        shadow.setOffsetY(3);
                        b.setEffect(shadow);
                    });

                    // Exit hover — revert to base
                    b.setOnMouseExited(e -> {
                        b.setStyle(baseStyle);
                        b.setEffect(null);
                    });

                    // Disabled state
                    b.disabledProperty().addListener((obs, wasDisabled, isNowDisabled) -> {
                        if (isNowDisabled) {
                            b.setStyle("-fx-background-color: #fff000;" +
                                    "-fx-opacity: 0.5;" +
                                    "-fx-background-radius: 4;" +
                                    "-fx-border-radius: 4;" +
                                    "-fx-text-fill: #000000;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-font-size: 16px;" +
                                    "-fx-padding: 10 15;" +
                                    "-fx-border-width: 0;" +
                                    "-fx-cursor: not-allowed;");
                            b.setEffect(null);
                        } else {
                            b.setStyle(baseStyle);
                        }
                    });
                }

                buttonSet.add(b, c, r);
            }

            // prevents the buttons from stealing focus from text field
            for (Node node : buttonSet.getChildren()) {
                if (node instanceof Button) {
                    node.setFocusTraversable(false);
                }
            }
            backspace.setFocusTraversable(false);
            clearGraph.setFocusTraversable(false);
            clearText.setFocusTraversable(false);
            generateButton.setFocusTraversable(false);
            information.setFocusTraversable(false);

            //sets the informating image inside the button
            information.setPrefSize(25,25);
            Image image = new Image(getClass().getResourceAsStream("/dev/sadik/GraphX/information.png"));
            ImageView img = new ImageView(image);
            img.setFitHeight(25);
            img.setPreserveRatio(true);
            information.setGraphic(img);

            backspace.setOnAction(event -> {
                String currentText = equationBox.getText();
                if(currentText != null && !currentText.isEmpty()) {
                    int caretPosition = equationBox.getCaretPosition();
                    equationBox.deleteText(caretPosition-1, caretPosition);
                    equationBox.positionCaret(caretPosition-1);
                }
                equationBox.requestFocus();
            });
        }
    }

    private void insertTextAtCursor(String text) {
        // get the current location of cursor
        int caretPosition = equationBox.getCaretPosition();

        // set the text at the current cursor location
        equationBox.insertText(caretPosition, text);


        // update the location of cursor
        equationBox.positionCaret(caretPosition+text.length());
    }

    //initiates the generate button action
    public void generate(ActionEvent event) {
        String equation = getEquation();
        plotLine(equation);
    }

    //initiates the clear button action
    public void clearText(ActionEvent event) {
        equationBox.clear();
        equationBox.requestFocus();
    }

    public void clearGraph(ActionEvent event) {
        equationGraph.getData().clear();
    }

    //gets the equation from the text field
    private String getEquation() {
        return equationBox.getText();
    }

    //pareses the equation and gets the result of the equation
    private double parseEquation(double currentValue, String equation, String variable) {
        Expression e = new ExpressionBuilder(equation)
                .variables(variable)
                .functions(acot, coth)
                .build()
                .setVariable(variable, currentValue);
        return e.evaluate();
    }

    private double parseEquation(double val1, double val2, String equation, String var1, String var2) {
        Expression e = new ExpressionBuilder(equation)
                .variables(var1, var2)
                .functions(acot, coth)
                .build()
                .setVariable(var1, val1)
                .setVariable(var2,val2);

        return e.evaluate();
    }

    String findExpression(String variable, String exp1, String exp2) {
        return exp1.equals(variable) ? exp2 : exp1;
    }


     //checks if both sides of an equation contain the same variable(s)
    private boolean hasSameVariableBothSides(String leftSide, String rightSide) {
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
    private String detectPrimaryVariable(String leftSide, String rightSide) {
        boolean leftHasX = leftSide.contains("x");
        boolean rightHasX = rightSide.contains("x");

        if (leftHasX || rightHasX) {
            return "x";
        }
        return "y";
    }

    //adds data in the series and then in graph
    private void plotLine(String equation) {
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
            equationGraph.getData().add(series);
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
                            equationGraph.getData().add(currentSeries);
                            currentSeries = new XYChart.Series<>();
                            // add the last point to the series
                            currentSeries.getData().add(new XYChart.Data<>(lastX, y - STEP_SIZE));
                        }
                        currentSeries.getData().add(new XYChart.Data<>(x, y));
                        prevX = lastX;
                        lastX = x;
                    }
                    equationGraph.getData().add(currentSeries); // add the final points
                }
                else {
                    for(double x = -range, y; x<=range; x+=STEP_SIZE) {
                        y = parseEquation(x, expression, variable);
                        series.getData().add(new XYChart.Data<>(x, y));
                    }
                    equationGraph.getData().add(series);
                }
            }
        }
    }

    //plots equations where the same variable appears on both sides
    private void plotSameVariableEquation(String implicitEquation, String variable) {
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
                equationGraph.getData().add(lineSeries);
            }
        } else {
            // For y variable, plot horizontal lines at each root
            for (Double root : roots) {
                XYChart.Series<Double, Double> lineSeries = new XYChart.Series<>();
                lineSeries.getData().add(new XYChart.Data<>(-range, root));
                lineSeries.getData().add(new XYChart.Data<>(range, root));
                equationGraph.getData().add(lineSeries);
            }
        }
    }

   // bisection search for single-variable equations
    private double bisectionSearchSingleVar(double min, double max, String equation, String variable) {
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

        for (int i = 0; i < MAX_ITERATIONS; i++) {
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
        }
        return c;
    }

    private void plotImplicitFunction(String implicitEquation) {
        final double SEARCH_RANGE_MIN = -range;
        final double SEARCH_RANGE_MAX = range;
        final double Y_SAMPLE_STEP = 0.2; // Sample the y-range to find sign changes

        for(double x = -range; x <= range; x += STEP_SIZE) {
            // Find ALL roots for this x value by sampling the entire y-range
            List<Double> roots = findAllRoots(x, SEARCH_RANGE_MIN, SEARCH_RANGE_MAX,
                    implicitEquation, Y_SAMPLE_STEP);

            // add all found roots as individual points
            for(Double y_root : roots) {
                // check if this point should be connected to previous series
                boolean connected = false;

                for(XYChart.Series<Double, Double> existingSeries : equationGraph.getData()) {
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
                    equationGraph.getData().add(newSeries);
                }
            }
        }
    }

    private List<Double> findAllRoots(double x_const, double y_min, double y_max,
                                      String equation, double sampleStep) {
        List<Double> roots = new ArrayList<>();

        // sample the range to find all sign changes
        for(double y = y_min; y < y_max; y += sampleStep) {
            double f1 = parseEquation(x_const, y, equation, "x", "y");
            double f2 = parseEquation(x_const, y + sampleStep, equation, "x", "y");

            // skip if either value is invalid
            if(Double.isNaN(f1) || Double.isInfinite(f1) ||
                    Double.isNaN(f2) || Double.isInfinite(f2)) {
                continue;
            }

            // in case of a sign change, use bisection to find the exact root
            if(Math.signum(f1) != Math.signum(f2)) {
                double root = bisectionSearch(x_const, y, y + sampleStep, equation);
                if(!Double.isNaN(root)) {
                    roots.add(root);
                }
            }
            // check if value is very close to zero
            else if(Math.abs(f1) < TOLERANCE) {
                roots.add(y);
            }
        }

        return roots;
    }

    private String detectVariable(String equation) {
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

    /**
     * Bisection method to find a root (y value) for G(y) = F(x_const, y) = 0
     * within the given y range.
     * @param x_const The fixed x value.
     * @param y_min Lower bound of y range.
     * @param y_max Upper bound of y range.
     * @param equation The implicit equation (RHS of F(x,y)=0).
     * @return The y root, or Double.NaN if no root is found in the range.
     */

    private double bisectionSearch(double x_const, double y_min, double y_max, String equation) {
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

    //function for author information
    public void showInformation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dev/sadik/GraphX/Information.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage infoScreen = new Stage();
        infoScreen.setTitle("About Developer");
        Image icon = new Image(getClass().getResourceAsStream("/dev/sadik/GraphX/information.png"));
        infoScreen.getIcons().add(icon);
        infoScreen.setScene(scene);
        infoScreen.show();
    }
}
