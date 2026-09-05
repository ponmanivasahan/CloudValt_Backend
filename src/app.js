// ── Express App ───────────────────────────────────────────────────────────────

const express = require('express');
const cors    = require('cors');
const helmet  = require('helmet');
const morgan  = require('morgan');

const healthRoutes = require('./routes/health.routes');
const authRoutes   = require('./routes/auth.routes');
const { errorHandler, notFound } = require('./middleware/error.middleware');

const app = express();

// ── Security Headers ──────────────────────────────────────────────────────────
app.use(helmet());

// ── CORS ──────────────────────────────────────────────────────────────────────
const allowedOrigins = process.env.FRONTEND_URL 
  ? process.env.FRONTEND_URL.split(',')
  : ['http://localhost:5173', 'http://localhost:5174', 'https://cloud-valt-frontend.vercel.app'];

app.use(cors({
  origin: (origin, callback) => {
    // Allow requests with no origin (like mobile apps, curl, or postman)
    if (!origin) return callback(null, true);
    if (allowedOrigins.indexOf(origin) === -1) {
      return callback(null, false); // Block origin
    }
    return callback(null, true);
  },
  credentials: true,
  methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'],
  allowedHeaders: ['Content-Type', 'Authorization', 'Accept'],
}));

// ── Body Parsing ──────────────────────────────────────────────────────────────
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// ── Request Logging ───────────────────────────────────────────────────────────
if (process.env.NODE_ENV !== 'test') {
  app.use(morgan('dev'));
}

// ── Routes ────────────────────────────────────────────────────────────────────
app.use('/api/health', healthRoutes);
app.use('/api/auth',   authRoutes);

// ── 404 & Error Handling ──────────────────────────────────────────────────────
app.use(notFound);
app.use(errorHandler);

module.exports = app;
