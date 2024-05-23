// // WelcomePage.js

// import React, { useEffect } from 'react';
// import { useNavigate } from 'react-router-dom';
// import "../components/Welcome.css";

// const WelcomePage = () => {
//   const navigate = useNavigate();

//   useEffect(() => {
//     const timer = setTimeout(() => {
//       navigate('/welcomepage');
//     }, 6000);

//     return () => clearTimeout(timer);
//   }, [navigate]);

//   useEffect(() => {
//     // Assuming you want to hide 'ul li' elements on document load
//     const text = document.querySelectorAll('ul li');
//     text.forEach((item) => {
//       item.classList.remove('hiddeen'); // Note: Fix the class name to 'hidden' if it's a typo
//     });
//   }, []);

//   return (
//     <div>
//       <h1>Welcome to Your App</h1>
//       <p>This is your temporary welcome message. Customize it as needed.</p>
//     </div>
//   );
// };

// export default WelcomePage;
