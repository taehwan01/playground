import express from 'express';

import * as authController from '../controllers/auth.js';

const router = express.Router();

// get post put delete
router.get('/', authController.welcome);
router.post('/pre-register', authController.preRegister); // 회원가입자 이메일 인증
router.post('/register', authController.register);

export default router;