// ── JWT Auth Middleware ───────────────────────────────────────────────────────
// Protects routes by verifying the Bearer token in the Authorization header.
// Attaches decoded user info to req.user.

const { verifyToken } = require('../services/jwt.service');

function authenticate(req, res, next) {
  const authHeader = req.headers['authorization'] || req.headers['Authorization'];

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({
      success: false,
      message: 'Authentication required. Please provide a valid token.',
      data:    null,
    });
  }

  const token = authHeader.slice(7); // Remove "Bearer " prefix

  try {
    const decoded = verifyToken(token);
    // Attach user info to the request — same fields as Java's SecurityContext
    req.user = {
      email:  decoded.sub,     // subject = email
      userId: decoded.userId,
      role:   decoded.role,
    };
    next();
  } catch (err) {
    return res.status(401).json({
      success: false,
      message: 'Invalid or expired token.',
      data:    null,
    });
  }
}

module.exports = { authenticate };
