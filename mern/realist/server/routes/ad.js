import express from 'express';

import * as adController from '../controllers/ad.js';
import { requireSignin } from '../middlewares/auth.js';

const router = express.Router();

router.post('/upload-image', requireSignin, adController.uploadImage);
router.post('/remove-image', requireSignin, adController.removeImage);
router.post('/ad', requireSignin, adController.create);
router.get('/ads', adController.ads);
router.get('/ad/:slug', adController.read);

router.post('/wishlist', requireSignin, adController.addToWishList);
router.delete(
  '/wishlist/:adId',
  requireSignin,
  adController.removeFromWishList,
);
router.post('/contact-seller', requireSignin, adController.contactSeller);
router.get('/user-ads/:page', requireSignin, adController.userAds);
router.put('/ad/:_id', requireSignin, adController.update);

router.get('/enquiries', requireSignin, adController.enquiredProperties);
router.get('/wishlist', requireSignin, adController.wishlist);

router.delete('/ad/:_id', requireSignin, adController.remove);

router.get('/ads-for-sell', adController.adsForSell);
router.get('/ads-for-rent', adController.adsForRent);

export default router;
