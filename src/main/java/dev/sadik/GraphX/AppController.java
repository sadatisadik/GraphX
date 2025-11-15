package dev.sadik.GraphX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
import java.util.Objects;

interface functionConstant{
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
}

public class AppController extends GraphController implements functionConstant {
    // Tolerance for the root finding method
    protected static final double TOLERANCE = 1e-4;
    // Max iterations for bisection search
    protected static final int MAX_ITERATIONS = 50;
    //Range and required steps for graph
    protected static final double range = 10;
    protected static final double STEP_SIZE = 0.01;
    //rows and cols for calculator buttons
    //array to store inserted equations
    private final ArrayList<String> equations = new ArrayList<>();

    //Injecting the elements to be controlled from FXML to AppController class
    @FXML LineChart <Double,Double> equationGraph;
    @FXML TextField equationBox;
    @FXML Button generateButton;
    @FXML Button clearText;
    @FXML Button clearGraph;
    @FXML GridPane buttonSet;
    @FXML Button backspace;
    @FXML Button information;
    @FXML Button showTable;

    public void initialize() {
        int index = 0;
        final int rows = 10;
        final int cols = 4;
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
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dev/sadik/GraphX/information.png")));
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
        //gets the equation from the text field
        String equation = equationBox.getText();
        plotLine(equation);
        if(equations.contains(equation)) return;
        equations.add(equation);
    }

    //initiates the clear button action
    public void clearText(ActionEvent event) {
        equationBox.clear();
        equationBox.requestFocus();
    }

    //initiates the clear graph button action
    public void clearGraph(ActionEvent event) {
        equationGraph.getData().clear();
        equations.clear();
    }

    //initiates the showTable button action
    public void showTable(ActionEvent event) {
        if (equations.isEmpty()) return; //if there is no equation used yet simply skip

        //creating an empty space to include table
        AnchorPane canvas = new AnchorPane();
        canvas.setPrefSize(450, 500);
        Accordion ac = new Accordion();

        TitledPane[] tps = new TitledPane[equations.size()];
        for(int i = 0; i < equations.size(); i++){
            try{
                final String currentEquation = equations.get(i);
                final String expressionToParse;
                final String variableToUse;

                if (currentEquation.contains("=")) {
                    String leftHandSide = currentEquation.substring(0, currentEquation.indexOf("=")).trim().toLowerCase();
                    String rightHandSide = currentEquation.substring(currentEquation.indexOf("=") + 1).trim().toLowerCase();

                    if (leftHandSide.equals("x") || rightHandSide.equals("x")) {
                        variableToUse = "y";
                        expressionToParse = findExpression("x", leftHandSide, rightHandSide);
                    } else { // Default to y=f(x)
                        variableToUse = "x";
                        expressionToParse = findExpression("y", leftHandSide, rightHandSide);
                    }
                } else {
                    // Handle cases with no "=" (e.g., user just types "sin(x)")
                    expressionToParse = currentEquation;
                    variableToUse = detectVariable(currentEquation);
                }
                //populate the Panes and set text
                tps[i] = new TitledPane();
                String textToSet = "#" + (i+1) + ": " + currentEquation;
                tps[i].setText(textToSet);
                tps[i].setPrefSize(450, 290);
                tps[i].setStyle("-fx-font-weight: bold");

                //initialize the table
                TableView<DataModel> table = new TableView<>();

                //now the columns
                TableColumn<DataModel, Double> x = new TableColumn<>("x");
                x.setCellValueFactory(new PropertyValueFactory<DataModel, Double>("x"));
                TableColumn<DataModel, Double> y = new TableColumn<>("y");
                y.setCellValueFactory(new PropertyValueFactory<DataModel, Double>("y"));

                //populate the table columns with appropriate variable
                if(variableToUse.contains("x")) {
                    table.getColumns().add(x);
                    table.getColumns().add(y);

                } else if(variableToUse.contains("y")) {
                    table.getColumns().add(y);
                    table.getColumns().add(x);
                }

                for(int j = 1; j<=10; j++) {
                    table.getItems().add(new DataModel(j, parseEquation(j, expressionToParse, variableToUse)));
                }

                //making the table justified
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                //adding the table to the accordion
                tps[i].setContent(table);
            }catch(Exception e) { // Catch an exception just in case
                System.err.println("Error generating UI for Table: " + e.getMessage());
            }
        }

        //adding tps into accordion
        ac.getPanes().addAll(tps);

        //setting the first titled pane to be expanded
        ac.setExpandedPane(tps[0]);

        //adding accordion to the canvas
        canvas.getChildren().add(ac);

        //creating the scene for table
        Scene tableScene = new Scene(canvas);
        Stage tableStage = new Stage();
        tableStage.setResizable(false);
        tableStage.setAlwaysOnTop(true);

        //set the scene and show it on screen
        tableStage.setScene(tableScene);
        if(tableStage.isShowing()) {
            tableStage.close();
        }
        tableStage.show();
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

    private void plotImplicitFunction(String implicitEquation) {
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

    //show info button controlling method
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
