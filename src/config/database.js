// ── Sequelize / MySQL Connection ──────────────────────────────────────────────

const { Sequelize } = require('sequelize');

const sequelize = new Sequelize(
  process.env.DATABASE_NAME     || 'cloudstorage',
  process.env.DATABASE_USERNAME || 'root',
  process.env.DATABASE_PASSWORD || '',
  {
    host:    process.env.DATABASE_HOST    || 'localhost',
    port:    process.env.DATABASE_PORT    || 3306,
    dialect: 'mysql',
    logging: process.env.NODE_ENV === 'development' ? console.log : false,
    dialectOptions: {
      connectTimeout: 10000,
    },
    pool: {
      max: 10,
      min: 2,
      acquire: 30000,
      idle: 10000,
    },
    timezone: '+00:00',
  }
);

module.exports = { sequelize };
