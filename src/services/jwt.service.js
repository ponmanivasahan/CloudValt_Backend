// ── JWT Service ───────────────────────────────────────────────────────────────
// Signs and verifies JWTs using HS256. 
// Claims match the original Java JwtService exactly:
//   - subject = email
//   - claims: userId, role

const jwt = require('jsonwebtoken');

const JWT_SECRET     = process.env.JWT_SECRET     || 'cloudvault-dev-secret-key-minimum-32-characters-long';
const JWT_EXPIRES_IN = process.env.JWT_EXPIRATION_MS
  ? Math.floor(Number(process.env.JWT_EXPIRATION_MS) / 1000) + 's'
  : '86400s'; // 24 hours

/**
 * Generates a signed JWT token.
 */
function generateToken(userId, email, role) {
  return jwt.sign(
    { userId, role },      // payload claims
    JWT_SECRET,
    {
      subject:   email,    // sub claim = email (matches Java)
      algorithm: 'HS256',
      expiresIn: JWT_EXPIRES_IN,
    }
  );
}

/**
 * Verifies a token and returns the decoded payload.
 * Throws if the token is invalid or expired.
 */
function verifyToken(token) {
  return jwt.verify(token, JWT_SECRET, { algorithms: ['HS256'] });
}

module.exports = { generateToken, verifyToken };
