// Card.js
import React from 'react';
import '../components/Card.css'; // Import your CSS file for card styling

const Card = ({ title, content }) => {
  return (
    <div className="card">
      <br/><br/><br/><br/>
       <h1 className='title'>{title}</h1>
      
       <br/><br/><br/>
       <hr/><hr/>
      <button className="cardButton">{content}</button>

    </div>
  );
};

export default Card;
