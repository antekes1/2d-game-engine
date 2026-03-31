# 2D Game Engine

Game engine written in Java.

## Features
- 2D Rendering
- Physics Engine
- Asset Management
- Scripting Support

## Getting Started

### Requirements
- Java 11 or later
- Maven 3.6 or later

### Build and Run
1. Clone the repository:
   ```bash
   git clone https://github.com/antekes1/2d-game-engine.git
   cd 2d-game-engine
   ```
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the game engine:
   ```bash
   mvn exec:java
   ```

## Project Structure
- `src/main/java`: Contains the main source code.
- `src/main/resources`: Contains configuration and resources.
- `src/test/java`: Contains unit tests.

## Usage Examples
```java
// Example code to initialize the engine
GameEngine engine = new GameEngine();
engine.start();
```

## Roadmap
- [ ] Implement 3D Rendering
- [ ] Add Multiplayer Support
- [ ] Create Level Editor

## Contributing
1. Fork the project.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For any inquiries, please reach out to [Your Name](mailto:your.email@example.com).