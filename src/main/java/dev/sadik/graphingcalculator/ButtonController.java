package dev.sadik.graphingcalculator;

import  javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;

public class ButtonController {
    public static Button createLatexButton(String latex, double size) {
        TeXFormula formula = new TeXFormula(latex);
        TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, (float) size);

        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, image.createGraphics(), 0, 0);

        ImageView view = new ImageView(SwingFXUtils.toFXImage(image, null));
        view.setPreserveRatio(true);
        view.setSmooth(true);

        Button button = new Button();
        button.setGraphic(view);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }
}
