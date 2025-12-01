# Kotlin Boilerplate Template

A minimal Kotlin project template with testing and code quality tools configured.

## Features

- **Kotlin 2.1.0** with JVM toolchain 21
- **kotlin-test** for testing with JUnit Platform
- **ktlint** configured with official code style
- **EditorConfig** for consistent formatting
- **AI-free coding zone** - see [AGENTS.md](./AGENTS.md)

## Prerequisites

- JDK 21 or later

## Building

```bash
./gradlew build
```

## Running Tests

```bash
./gradlew test
```

## Code Quality

Run ktlint to check code style:

```bash
./gradlew ktlintCheck
```

Auto-format code with ktlint:

```bash
./gradlew ktlintFormat
```

## Project Structure

```
src/
├── main/
│   ├── kotlin/     # Application source code
│   └── resources/  # Application resources
└── test/
    ├── kotlin/     # Test source code
    └── resources/  # Test resources
```

## AI Assistance Policy

This repository is designated as an **AI-free coding zone**. Please see [AGENTS.md](./AGENTS.md) for details.

## License

This is a template repository - use it however you like!
