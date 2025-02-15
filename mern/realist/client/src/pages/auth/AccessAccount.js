import { useParams, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import toast from 'react-hot-toast';
import axios from 'axios';

import { useAuth } from '../../context/auth';

export const AccessAccount = () => {
  // context
  const [auth, setAuth] = useAuth();

  // hooks
  const { token } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (token) requestAccess();
  }, [token]);

  const requestAccess = async () => {
    try {
      const { data } = await axios.post('/access-account', {
        resetCode: token,
      });
      if (data?.error) {
        toast.error(data.error);
      } else {
        setAuth(data);
        toast.success('Please update your password in profile page.');
        navigate('/');
      }
    } catch (err) {
      console.log(err);
      toast.error('Something went wrong. Try again.');
    }
  };

  return (
    <div
      className='display-1 d-flex justify-content-center align-items-center vh-100'
      style={{ marginTop: '-10%' }}
    >
      Please wait...
    </div>
  );
};
