import React, { useState } from 'react';
import img from '../images/golden.jpg';
import img1 from '../images/husky.jpg';
import img2 from '../images/leo.jpg';
import img3 from '../images/sniper.jpg';
import '../components/ImageSlider.css';
import Card from './Card';

const ImageSlider = () => {
  const images = [img3, img2, img, img1];

  const [currentIndex, setCurrentIndex] = useState(0);

  const prevSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
  };

  const nextSlide = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
  };

  const changeSlide = (n) => {
    setCurrentIndex(n - 1);
  };

  const cardsData = [
    { id: 1, title: 'Products', content: 'ReadMore' },
    { id: 2, title: 'Services', content: 'ReadMore' },
    { id: 3, title: 'Contact Us', content: 'ReadMore' },
  ];

  return (
    <div className="slider-container">
      <div className="slider">
        <img src={images[currentIndex]} alt={`Slide ${currentIndex + 1}`} className="slider-image" />
      </div>
      <span className="controls" onClick={prevSlide} id="left-arrow">
        &lt;
      </span>
      <span className="controls" onClick={nextSlide} id="right-arrow">
        &gt;
      </span>

      <div id="dots-con">
        {images.map((_, i) => (
          <span
            key={i}
            className={`dot ${currentIndex === i ? 'active' : ''}`}
            onClick={() => changeSlide(i + 1)}
          />
        ))}
      </div>
      <div style={{ display: 'flex', justifyContent: 'space-evenly' }}>
      {cardsData.map((card) => (
        <Card key={card.id} title={card.title} content={card.content} />
      ))}
    </div>

    </div>
  );
};

export default ImageSlider;
