package dev.sadik.graphingcalculator;

//this class is used for controlling various elements of the anchorpane

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import java.lang.Double;
import java.lang.foreign.StructLayout;
import java.math.BigDecimal;

public class AppController {
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
                {"e^{x}", "exp("},                   // e^x (Euler’s number power)
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

            // prevents the buttons from stealing focus from textfield
            for (Node node : buttonSet.getChildren()) {
                if (node instanceof Button) {
                    node.setFocusTraversable(false);
                }
            }
            backspace.setFocusTraversable(false);
            clearGraph.setFocusTraversable(false);
            clearText.setFocusTraversable(false);
            generateButton.setFocusTraversable(false);

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

    //gets the equation from the textfield
    private String getEquation() {
        return equationBox.getText();
    }

    //pareses the equation and gets the result of the equation
    private double parseEquation(double currentValue, String equation, String variable) {
        Expression e = new ExpressionBuilder(equation).variables(variable).functions(acot, coth).build().setVariable(variable, currentValue);
        return e.evaluate();
    }

    String findExpression(String variable, String exp1, String exp2) {
        return exp1.equals(variable) ? exp2 : exp1;
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

            String expression;

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
            // failsafe
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
                //have to find break point
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
}