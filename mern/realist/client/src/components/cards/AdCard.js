import { Badge } from 'antd';
import { Link } from 'react-router-dom';

import './AdCard.css';
import AdFeatures from './AdFeatures';

const AdCard = ({ ad }) => {
  const formatNumber = (x) => {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  return (
    <div className='col-lg-4 p-4 gx-4 gy-4'>
      <Link to={`/ad/${ad.slug}`}>
        <Badge.Ribbon
          text={`${ad?.type} for ${ad?.action}`}
          color={`${ad?.action === 'Sell' ? 'green' : 'yellow'}`}
        >
          <div className='card hoverable shadow'>
            <img
              src={ad?.photos?.[0].Location}
              alt={`${ad?.type}-${ad?.address}-${ad?.action}-${ad?.price}`}
              style={{ height: '250px', objectFit: 'cover' }}
            />

            <div className='card-body'>
              <h3>${formatNumber(ad?.price)}</h3>
              <p className='card-text' style={{ fontSize: '0.8rem' }}>
                {ad?.address}
              </p>
              <AdFeatures ad={ad} />
            </div>
          </div>
        </Badge.Ribbon>
      </Link>
    </div>
  );
};

export default AdCard;
