import { NavLink } from 'react-router-dom';

const Sidebar = () => {
  return (
    <div>
      <ul className='nav nav-tabs'>
        <li className='nav-item'>
          <NavLink className='nav-link' to='/dashboard'>
            Dashboard
          </NavLink>
        </li>
        <li className='nav-item'>
          <NavLink className='nav-link' to='/ad/create'>
            Create Ad
          </NavLink>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;