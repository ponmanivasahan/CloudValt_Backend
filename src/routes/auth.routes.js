// ── Auth Routes ───────────────────────────────────────────────────────────────
// POST /api/auth/register
// POST /api/auth/login
// GET  /api/auth/me        (JWT required)
// POST /api/auth/logout    (JWT required)

const express  = require('express');
const { body, validationResult } = require('express-validator');
const authService  = require('../services/auth.service');
const { authenticate } = require('../middleware/auth.middleware');

const router = express.Router();

// ── Validation Rules ──────────────────────────────────────────────────────────

const NAME_REGEX = /^[A-Za-z]+(?:\s[A-Za-z]+)*$/;

const registerRules = [
  body('name')
    .trim()
    .notEmpty().withMessage('Name is required.')
    .isLength({ min: 2, max: 50 }).withMessage('Name must be between 2 and 50 characters.')
    .matches(NAME_REGEX).withMessage('Name must contain letters and spaces only.'),
  body('email')
    .trim()
    .notEmpty().withMessage('Email is required.')
    .isEmail().withMessage('Please provide a valid email address.'),
  body('password')
    .notEmpty().withMessage('Password is required.')
    .isLength({ min: 8 }).withMessage('Password must be at least 8 characters.')
    .matches(/[A-Z]/).withMessage('Password must contain at least one uppercase letter.')
    .matches(/[0-9]/).withMessage('Password must contain at least one number.')
    .matches(/[^a-zA-Z0-9]/).withMessage('Password must contain at least one special character.'),
];

const loginRules = [
  body('email').trim().notEmpty().withMessage('Email is required.').isEmail().withMessage('Invalid email.'),
  body('password').notEmpty().withMessage('Password is required.'),
];

// ── Helper: validate and return errors ────────────────────────────────────────

function validate(req, res) {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    res.status(400).json({
      success: false,
      message: 'Validation failed.',
      data:    errors.array().map(e => ({ field: e.path, message: e.msg })),
    });
    return false;
  }
  return true;
}

// ── POST /api/auth/register ───────────────────────────────────────────────────

router.post('/register', registerRules, async (req, res, next) => {
  if (!validate(req, res)) return;
  try {
    await authService.register(req.body);
    return res.status(201).json({
      success: true,
      message: 'Registration successful',
      data:    null,
    });
  } catch (err) {
    next(err);
  }
});

// ── POST /api/auth/login ──────────────────────────────────────────────────────

router.post('/login', loginRules, async (req, res, next) => {
  if (!validate(req, res)) return;
  try {
    const result = await authService.login(req.body);
    return res.status(200).json({
      success: true,
      message: 'Login successful',
      data:    result,
    });
  } catch (err) {
    next(err);
  }
});

// ── GET /api/auth/me ──────────────────────────────────────────────────────────

router.get('/me', authenticate, async (req, res, next) => {
  try {
    const user = await authService.getCurrentUser(req.user.email);
    return res.status(200).json({
      success: true,
      message: 'User retrieved successfully',
      data:    user,
    });
  } catch (err) {
    next(err);
  }
});

// ── POST /api/auth/logout ─────────────────────────────────────────────────────

router.post('/logout', authenticate, (req, res) => {
  // JWT is stateless — the client removes the token.
  return res.status(200).json({
    success: true,
    message: 'Logout successful',
    data:    null,
  });
});

module.exports = router;
