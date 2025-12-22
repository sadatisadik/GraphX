# GraphX - Advanced Graphing Calculator

![GraphX Logo](src/main/resources/dev/sadik/GraphX/icon.png)

A modern, feature-rich graphing calculator built with JavaFX that enables users to visualize mathematical functions and solve complex equations with an intuitive interface.

## Features

✨ **Interactive Graph Visualization**
- Real-time rendering of mathematical functions
- Support for single and multi-variable equations
- Smooth zooming and panning capabilities

📐 **Advanced Mathematical Operations**
- Comprehensive trigonometric functions (sin, cos, tan, cot, and inverse variants)
- Hyperbolic functions (sinh, cosh, coth)
- Support for logarithmic and exponential functions
- Custom variable support (x, y, and more)

🔍 **Equation Solving**
- Bisection method for finding roots
- Support for implicit equations F(x,y)=0
- Multi-root detection and analysis

🎨 **User-Friendly Interface**
- Clean, modern GUI design
- Real-time error detection and reporting
- Mathematical notation rendering using JLaTeXMath
- Responsive and accessible controls

## System Requirements

### GraphX v2.0
- **OS**: Windows 10 or newer (64-bit)
- **Disk Space**: ~800MB (includes bundled runtime/JDK)
- **Memory**: Minimum 512MB RAM (1GB recommended)
- **Internet**: Not required after download

The distributed `GraphX.zip` includes the required runtime — no separate JDK installation is necessary for the Windows distribution.

## Installation

### GraphX v2.0 (Recommended)

1. Download the `GraphX.zip` file.
2. Extract the zip to a folder of your choice.
3. Run `GraphX.exe` to launch the calculator.

The runtime (JDK) is included in the zip file — no additional JDK installation is required.

## Usage

### Basic Graphing
1. Enter your mathematical expression in the input field
2. Specify the variable and range (e.g., x from -10 to 10)
3. Click "Graph" to visualize the function
4. Use mouse controls to zoom and pan

### Supported Functions
- **Trigonometric**: sin, cos, tan, cot, asin, acos, atan, acot
- **Hyperbolic**: sinh, cosh, tanh, coth
- **Logarithmic**: log, ln
- **Other**: sqrt, abs, pow, floor, ceil

### Example Expressions
- Simple: `sin(x)`, `x^2 - 4`
- Complex: `sin(x) * cos(y)`, `sqrt(x^2 + y^2)`
- Multi-variable: `x*y + 2*x - 3*y`

## Project Structure

```
GraphX/
├── src/
│   └── main/
│       ├── java/
│       │   ├── dev/sadik/GraphX/
│       │   │   ├── App.java                  # Main application entry point
│       │   │   ├── GraphingCalculator.java   # JavaFX Application class
│       │   │   ├── GraphController.java      # Graph rendering logic
│       │   │   ├── Parser.java               # Expression parsing and solving
│       │   │   ├── DataModel.java            # Data management
│       │   │   ├── AppController.java        # Main UI controller
│       │   │   ├── ButtonController.java     # Button event handlers
│       │   │   ├── ErrorController.java      # Error handling
│       │   │   ├── InformationController.java # Information display
│       │   │   └── module-info.java          # Java module configuration
│       │   └── resources/
│       │       └── dev/sadik/GraphX/
│       │           ├── App.fxml              # Main UI layout
│       │           ├── ErrorScreen.fxml      # Error dialog layout
│       │           └── Information.fxml      # Information panel layout
├── lib/
│   └── javafx-25.pom                        # JavaFX dependency
├── pom.xml                                   # Maven configuration
├── mvnw, mvnw.cmd                            # Maven wrapper
└── README.md                                 # This file
```

## Technologies Used

- **JavaFX 25**: Modern GUI framework for Java
- **exp4j 0.4.8**: Expression evaluation library
- **JLaTeXMath 1.0.7**: Mathematical notation rendering
- **Maven 3.x**: Build automation
- **JUnit 5**: Unit testing framework
- **Java 24+**: Latest Java features and modules

## Building Executable

### Creating Windows Executable
The project uses Launch4j to create a standalone `.exe` file:

```bash
mvn clean package
```

This generates `target/GraphX.exe` ready for distribution.

### Creating Installer
Use Inno Setup with the provided configuration to bundle the executable and dependencies into an installer package.

## Architecture

### Graph Rendering
- Utilizes JavaFX Canvas for efficient real-time rendering
- Adaptive resolution based on graph range and zoom level
- Hardware-accelerated graphics support

### Expression Parsing
- Built on exp4j library for safe expression evaluation
- Custom function implementations (acot, coth)
- Error handling with meaningful user feedback

### Root Finding
- Implements bisection method algorithm
- Handles both single and multi-variable equations
- Robust handling of edge cases (infinity, NaN)

## Future Enhancements

- [ ] 3D graphing support
- [ ] Parametric equations
- [ ] Derivative and integral visualization
- [ ] Export graphs as images (PNG, SVG)
- [ ] Graph annotation tools
- [ ] History and saved expressions
- [ ] Keyboard shortcuts reference

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- **Icon Design**: [Anggara - Flaticon](https://www.flaticon.com/authors/anggara) for the information icon
- **exp4j**: For reliable mathematical expression evaluation
- **JLaTeXMath**: For beautiful mathematical rendering
- **JavaFX**: For the modern UI framework

## Support & Contact

For issues, feature requests, or questions:
- Open an issue on GitHub
- Contact: [Your Email Here]
- Website: [Your Website Here]

## Changelog

### v1.0 (Current)
- Initial release
- Basic graphing functionality
- Single and multi-variable equation support
- Root finding capabilities
- Modern JavaFX UI

---

**Made with ❤️ by Sadati Sadik Pranto**

*Last Updated: November 2025*
