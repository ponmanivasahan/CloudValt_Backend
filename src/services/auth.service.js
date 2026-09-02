// ── Auth Service ──────────────────────────────────────────────────────────────
// Handles register, login, and get-current-user business logic.
// Architecture: authRoutes → authService → User model

const bcrypt     = require('bcryptjs');
const { v4: uuidv4 } = require('uuid');
const User       = require('../models/User');
const { generateToken } = require('./jwt.service');

// ── Register ──────────────────────────────────────────────────────────────────

async function register({ name, email, password }) {
  const normalizedEmail = email.toLowerCase().trim();

  const existing = await User.findOne({ where: { email: normalizedEmail } });
  if (existing) {
    const err = new Error('An account with this email already exists.');
    err.statusCode = 400;
    throw err;
  }

  const passwordHash = await bcrypt.hash(password, 12);

  await User.create({
    id:            uuidv4(),
    name:          name.trim(),
    email:         normalizedEmail,
    password_hash: passwordHash,
    role:          'USER',
    provider:      'LOCAL',
  });

  console.log(`New user registered: ${normalizedEmail}`);
}

// ── Login ─────────────────────────────────────────────────────────────────────

async function login({ email, password }) {
  const normalizedEmail = email.toLowerCase().trim();

  const user = await User.findOne({ where: { email: normalizedEmail } });

  // Use generic error to prevent user enumeration
  if (!user || !user.password_hash) {
    const err = new Error('Invalid email or password.');
    err.statusCode = 400;
    throw err;
  }

  const passwordMatch = await bcrypt.compare(password, user.password_hash);
  if (!passwordMatch) {
    const err = new Error('Invalid email or password.');
    err.statusCode = 400;
    throw err;
  }

  const token = generateToken(user.id, user.email, user.role);

  console.log(`User logged in: ${normalizedEmail}`);

  return {
    token,
    user: toUserResponse(user),
  };
}

// ── Get Current User ──────────────────────────────────────────────────────────

async function getCurrentUser(email) {
  const user = await User.findOne({ where: { email } });
  if (!user) {
    const err = new Error('User not found.');
    err.statusCode = 404;
    throw err;
  }
  return toUserResponse(user);
}

// ── Helper ────────────────────────────────────────────────────────────────────

function toUserResponse(user) {
  return {
    id:       user.id,
    name:     user.name,
    email:    user.email,
    role:     user.role,
    provider: user.provider,
  };
}

module.exports = { register, login, getCurrentUser };
