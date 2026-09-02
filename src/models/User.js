// ── User Model ────────────────────────────────────────────────────────────────
// Maps to the existing `users` table created by Flyway migrations V1 + V9.
// sync: false — Sequelize NEVER alters the schema.

const { DataTypes } = require('sequelize');
const { sequelize } = require('../config/database');

const User = sequelize.define('User', {
  id: {
    type: DataTypes.CHAR(36),
    primaryKey: true,
    // We set the UUID manually in auth.service.js via uuidv4()
  },
  name: {
    type: DataTypes.STRING(100),
    allowNull: false,
  },
  email: {
    type: DataTypes.STRING(255),
    allowNull: false,
    unique: true,
  },
  password_hash: {
    type: DataTypes.STRING(255),
    allowNull: true,
    field: 'password_hash',
  },
  role: {
    type: DataTypes.STRING(20),
    allowNull: false,
    defaultValue: 'USER',
  },
  provider: {
    type: DataTypes.STRING(20),
    allowNull: false,
    defaultValue: 'LOCAL',
  },
  provider_id: {
    type: DataTypes.STRING(255),
    allowNull: true,
    field: 'provider_id',
  },
}, {
  tableName:  'users',
  timestamps: true,
  createdAt:  'created_at',
  updatedAt:  'updated_at',
  // underscored maps camelCase accessors to snake_case columns
  underscored: true,
});

module.exports = User;
