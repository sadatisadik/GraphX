# GraphX - Advanced Graphing Calculator

![GraphX Logo](src/main/resources/dev/sadik/GraphX/icon.png)

A modern, feature-rich graphing calculator built with JavaFX that enables users to visualize mathematical functions and solve complex equations with an intuitive interface.

## Features

✨ **Interactive Graph Visualization**
- Real-time rendering of mathematical functions
- Support for single and multi-variable equations

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

### For Installer Version (`GraphX-installer.exe`)
- **OS**: Windows 10 or newer (64-bit)
- **Disk Space**: ~800MB (includes JDK 25)
- **Memory**: Minimum 512MB RAM (1GB recommended)
- **Internet**: Optional (not required after download)

### For Portable Non-Installer Version (`GraphX-Non-Installer.exe`)
- **Java**: JDK 24 or higher (must be pre-installed)
- **OS**: Windows, macOS, or Linux
- **Memory**: Minimum 256MB RAM (512MB+ recommended)
- **Disk Space**: ~50MB (portable executable only)
- **Notes**: Java must be properly installed and in system PATH

## Installation

### Option 1: Installer with Bundled JDK (Recommended for Most Users)

**File**: `GraphX-installer.exe`

✅ **Advantages**:
- Includes Java Development Kit (JDK 25) automatically
- No prerequisite installation required
- One-click installation process
- System-wide integration with Start Menu shortcuts
- Automatic dependency management

**Steps**:
1. Download `GraphX-installer.exe`
2. Double-click to run the installer
3. Follow the on-screen instructions
4. Application launches automatically upon completion

### Option 2: Portable Non-Installer Executable (Advanced Users)

**File**: `GraphX-Non-Installer.exe`

⚠️ **Requirements**:
- Java Development Kit (JDK) 24 or newer must be pre-installed
- No installation required; run directly from any location
- Portable - can be stored on USB drives

**Steps**:
1. Download and install [Java JDK 25](https://www.oracle.com/java/technologies/javase/jdk25-archive-downloads.html) or [Adoptium](https://adoptium.net/)
2. Download `GraphX-Non-Installer.exe`
3. Run the executable directly - no installation wizard
4. Application launches immediately

**Ideal for**:
- Users with Java already installed
- Portable installation on USB/external drives
- Advanced developers and power users
- Environments where software installation is restricted

### From Source (Developers)

1. **Clone the Repository**
   ```bash
   git clone https://github.com/sadikpranto/GraphX.git
   cd GraphX
   ```

2. **Build the Project** (requires JDK 24+ and Maven)
   ```bash
   mvn clean package
   ```

3. **Run the Application**
   ```bash
   mvn javafx:run
   ```

### Manual JAR Execution (Advanced)
   ```bash
   java -jar target/GraphX-1.0.jar
   ```
   Requires Java 24+ installed on your system.

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
- Contact: [Sadati Sadik](mailto:sadatisadik.pranto@gmail.com)
- LinkedIn: [Sadati Sadik](https://www.linkedin.com/in/sadatisadik/)

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
