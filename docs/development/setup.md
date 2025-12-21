# Development Setup Guide

Complete guide for setting up the TBD platform for local development.

## Prerequisites

- **JDK 17+** - For Kotlin backend
- **PostgreSQL 12+** - Database
- **Node.js 18+** (optional) - For local frontend server
- **Git** - Version control

### Installing Prerequisites

#### macOS (using Homebrew)

```bash
# Install Java
brew install openjdk@17

# Install PostgreSQL
brew install postgresql@14
brew services start postgresql@14

# Install Node.js (optional)
brew install node
```

#### Linux (Ubuntu/Debian)

```bash
# Install Java
sudo apt update
sudo apt install openjdk-17-jdk

# Install PostgreSQL
sudo apt install postgresql-14

# Install Node.js (optional)
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs
```

## Project Setup

### 1. Clone Repository

```bash
git clone https://github.com/wardmic4/flow-platform.git
cd flow-platform
```

### 2. Database Setup

#### Create Database

```bash
# Using psql
createdb flow_api

# Or using PostgreSQL client
psql -U postgres -c "CREATE DATABASE flow_api;"
```

#### Verify Database

```bash
psql -U postgres -d flow_api -c "SELECT version();"
```

### 3. Backend Setup

#### Configure Environment Variables

```bash
cd flow-api
cp .env.example .env  # If .env.example exists
```

Create `.env` file with:

```bash
ENVIRONMENT=development
DATABASE_URL=jdbc:postgresql://localhost:5432/flow_api
DATABASE_USER=postgres
DATABASE_PASSWORD=your_password
JWT_SECRET=$(openssl rand -hex 32)
MASTER_ENCRYPTION_KEY=$(openssl rand -hex 32)
```

#### Generate Secrets

```bash
# Generate JWT secret
openssl rand -hex 32

# Generate encryption key
openssl rand -hex 32
```

#### Start Backend

```bash
cd flow-api
./gradlew run

# Or using the run script
./run.sh
```

Backend will start on `http://localhost:8080`

### 4. Frontend Setup

#### Option 1: Python HTTP Server

```bash
# From project root
python3 -m http.server 3000
```

#### Option 2: Node.js HTTP Server

```bash
# Install http-server globally
npm install -g http-server

# Start server
http-server -p 3000
```

#### Option 3: Live Server (VS Code)

1. Install "Live Server" extension in VS Code
2. Right-click `index.html` → "Open with Live Server"

Frontend will be available at `http://localhost:3000`

## Development Workflow

### Running Tests

```bash
cd flow-api
./gradlew test
```

### Building

```bash
cd flow-api
./gradlew build
```

### Database Migrations

The application automatically creates database schema on first run. No manual migrations needed.

### Hot Reload

For backend development:
- Use IntelliJ IDEA with Kotlin plugin
- Enable "Build project automatically"
- Use Ktor's development mode (auto-reload on changes)

## Project Structure

```
flow-platform/
├── flow-api/              # Kotlin backend
│   ├── src/
│   │   └── main/
│   │       ├── kotlin/    # Source code
│   │       └── resources/ # Config files
│   └── build.gradle.kts   # Build config
├── index.html            # Landing page
├── dashboard.html        # Dashboard
├── api-reference.html    # API docs
└── docs/                 # Documentation
```

## Common Issues

### Database Connection Failed

1. Verify PostgreSQL is running: `brew services list` (macOS) or `sudo systemctl status postgresql` (Linux)
2. Check database credentials in `.env`
3. Verify database exists: `psql -U postgres -l`

### Port Already in Use

```bash
# Find process using port 8080
lsof -i :8080

# Kill process
kill -9 <PID>
```

### Gradle Build Fails

```bash
# Clean and rebuild
cd flow-api
./gradlew clean build
```

## IDE Setup

### IntelliJ IDEA (Recommended)

1. Open project in IntelliJ
2. Import Gradle project
3. Configure JDK 17
4. Install Kotlin plugin (if not already installed)

### VS Code

1. Install Kotlin extension
2. Install Gradle extension
3. Configure Java home: `Cmd+Shift+P` → "Java: Configure Java Runtime"

## Next Steps

- [API Documentation](../api/specification.md)
- [Environment Variables](./environment-variables.md)
- [Testing Guide](./testing.md)

