// ── Entry Point ───────────────────────────────────────────────────────────────
// Loads environment variables first, then starts the HTTP server.

require('dotenv').config();

const app = require('./app');
const { sequelize } = require('./config/database');

// Require models so Sequelize knows about them
require('./models/User');

const PORT = process.env.PORT || 5000;

async function startServer() {
  try {
    // Connect to DB and automatically create tables if they are missing
    await sequelize.authenticate();
    await sequelize.sync(); // Removed { alter: true } to prevent issues with shared databases
    console.log('✅ MySQL connected and tables synced successfully.');

    app.listen(PORT, () => {
      console.log(`🚀 Server running on http://localhost:${PORT}`);
      console.log(`📋 Health check: http://localhost:${PORT}/api/health`);
    });
  } catch (error) {
    console.error('❌ Failed to connect to MySQL:', error.message);
    process.exit(1);
  }
}

startServer();
