const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());

// Define user schema
const userSchema = new mongoose.Schema({
  email: { type: String, required: true },
  password: { type: String, required: true },
  phone: { type: String, required: true },
  sessionToken: { type: String }
});

// Create User model
const User = mongoose.model('User', userSchema);

// Connect to MongoDB Atlas
mongoose.connect('mongodb+srv://test:test@cluster0.eiffbvz.mongodb.net/', { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log('MongoDB connected'))
  .catch(err => console.error(err));

// Register route
app.post('/register', async (req, res) => {
  try {
    const { email, password, phone } = req.body;
    // Check if user already exists
    let user = await User.findOne({ email });
    if (user) {
      return res.status(400).json({ message: 'User already exists' });
    }
    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);
    // Create new user
    user = new User({ email, password: hashedPassword, phone });
    await user.save();
    // Generate JWT token
    const token = jwt.sign({ userId: user._id }, 'your_secret_key');
    // Save token to user session
    user.sessionToken = token;
    await user.save();
    res.status(201).json({ message: 'User registered successfully', token });
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
});

app.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ message: 'Invalid email or password' });
    }
    const isValidPassword = await bcrypt.compare(password, user.password);
    if (!isValidPassword) {
      return res.status(400).json({ message: 'Invalid email or password' });
    }
    const token = jwt.sign({ userId: user._id }, 'your_secret_key');
    user.sessionToken = token;
    await user.save();
    res.status(200).json({ message: 'Login successful', token });
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
});

// Dashboard route
app.get('/dashboard', authenticateToken, (req, res) => {
  res.status(200).json({ message: 'Welcome to the dashboard' });
});

function authenticateToken(req, res, next) {
  const token = req.headers['authorization'];
  if (!token) {
    return res.status(401).json({ message: 'Unauthorized' });
  }
  jwt.verify(token, 'your_secret_key', (err, user) => {
    if (err) {
      return res.status(403).json({ message: 'Invalid token' });
    }
    req.user = user;
    next();
  });
}


// Login route, Dashboard route, and authentication middleware can remain the same

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
