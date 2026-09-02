// ── Entry Point ───────────────────────────────────────────────────────────────
// Loads environment variables first, then starts the HTTP server.

require('dotenv').config();

const app = require('./app');
const { sequelize } = require('./config/database');

const PORT = process.env.PORT || 5000;

async function startServer() {
  try {
    // Test DB connection (does NOT create or alter tables)
    await sequelize.authenticate();
    console.log('✅ MySQL connected successfully.');

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
