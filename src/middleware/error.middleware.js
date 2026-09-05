// ── Global Error Handler ──────────────────────────────────────────────────────
// Mirrors the Java GlobalExceptionHandler — all errors return the same
// ApiResponse shape: { success, message, data }

/**
 * 404 handler — must be registered AFTER all routes.
 */
function notFound(req, res, next) {
  res.status(404).json({
    success: false,
    message: `Route not found: ${req.method} ${req.originalUrl}`,
    data:    null,
  });
}

/**
 * Global error handler — must have 4 parameters (err, req, res, next).
 */
function errorHandler(err, req, res, next) { // eslint-disable-line no-unused-vars
  // Always log the real error so we can diagnose problems
  console.error(`[ERROR] ${req.method} ${req.path} →`, err.message);
  if (err.stack) console.error(err.stack);

  // Validation errors from express-validator
  if (err.type === 'validation') {
    return res.status(400).json({
      success: false,
      message: 'Validation failed.',
      data:    err.errors,
    });
  }

  // Sequelize unique constraint error
  if (err.name === 'SequelizeUniqueConstraintError') {
    return res.status(400).json({
      success: false,
      message: 'An account with this email already exists.',
      data:    null,
    });
  }

  // Sequelize validation error
  if (err.name === 'SequelizeValidationError') {
    return res.status(400).json({
      success: false,
      message: err.errors?.[0]?.message || 'Validation error.',
      data:    null,
    });
  }

  const status  = err.statusCode || 500;
  const message = err.message; // Temporarily exposing all errors to the frontend for debugging

  return res.status(status).json({
    success: false,
    message,
    data: null,
  });
}

module.exports = { notFound, errorHandler };
