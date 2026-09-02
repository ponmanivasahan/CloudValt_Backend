// ── Health Routes ─────────────────────────────────────────────────────────────
// GET /api/health — public endpoint for uptime checks

const express    = require('express');
const { sequelize } = require('../config/database');

const router = express.Router();

router.get('/', async (req, res) => {
  try {
    await sequelize.authenticate();
    return res.status(200).json({
      success: true,
      message: 'Service is UP',
      data: {
        status:   'UP',
        database: 'UP',
        version:  process.env.npm_package_version || '1.0.0',
      },
    });
  } catch (err) {
    return res.status(503).json({
      success: false,
      message: 'Service degraded — database unavailable',
      data: {
        status:   'DOWN',
        database: 'DOWN',
      },
    });
  }
});

module.exports = router;
