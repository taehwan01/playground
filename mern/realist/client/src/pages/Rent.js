import { useEffect, useState } from 'react';
import { useAuth } from '../context/auth';
import axios from 'axios';

import AdCard from '../components/cards/AdCard';

const Rent = () => {
  // context
  const [auth, setAuth] = useAuth();

  // state
  const [ads, setAds] = useState([]);

  useEffect(() => {
    fetchAds();
  }, []);

  const fetchAds = async () => {
    try {
      const { data } = await axios.get('/ads-for-rent');
      setAds(data);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div>
      <h1 className='display-1 bg-primary text-light p-5'>For Rent</h1>
      <div className='container'>
        <div className='row'>
          {ads?.map((ad) => (
            <AdCard ad={ad} key={ad._id} />
          ))}
        </div>
      </div>
      {/* <pre>{JSON.stringify({ adsForSell, adsForRent }, null, 4)}</pre> */}
    </div>
  );
};

export default Rent;
