const navbar = document.querySelector('.header');
window.onscroll = () => {
  if (window.scrollY > 300) {
    navbar.classList.add('nav-active');
  } else {
    navbar.classList.remove('nav-active');
  }
};
