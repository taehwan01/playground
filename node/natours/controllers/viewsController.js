import Tour from '../models/tourModel.js';
import catchAsync from '../utils/catchAsync.js';

export default class ViewsController {
  getOverview = catchAsync(async (req, res) => {
    // 1) get tour data from collection
    const tours = await Tour.find();

    // 2) build template

    // 3) render that template using tour data from 1)
    res.status(200).render('overview', {
      title: 'All Tours',
      tours,
    });
  });

  getTour = catchAsync(async (req, res) => {
    // 1) get the data, for the requested tour(including reviews and guides)
    const tour = await Tour.findOne({ slug: req.params.slug }).populate({
      path: 'reviews',
      fields: 'review rating user',
    });

    // 2) build template

    // 3) render template using data from 1)
    res.status(200).render('tour', {
      title: `${tour.name} Tour`,
      tour,
    });
  });
}
