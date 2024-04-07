// JavaScript for carousel control
document.addEventListener("DOMContentLoaded", function () {
    const carouselItems = document.querySelectorAll('.carousel-item');
    const prevButton = document.querySelector('.carousel-control-prev');
    const nextButton = document.querySelector('.carousel-control-next');
    let currentIndex = 0;

    // Show initial slide
    showSlide(currentIndex);

    // Event listeners for prev and next buttons
    prevButton.addEventListener('click', function () {
        currentIndex = (currentIndex === 0) ? carouselItems.length - 1 : currentIndex - 1;
        showSlide(currentIndex, 'prev');
    });

    nextButton.addEventListener('click', function () {
        currentIndex = (currentIndex === carouselItems.length - 1) ? 0 : currentIndex + 1;
        showSlide(currentIndex, 'next');
    });

    // Function to show slide at given index
    function showSlide(index, direction) {
        carouselItems.forEach((item, i) => {
            if (i === index) {
                item.classList.add('active');
            } else {
                item.classList.remove('active');
            }
            if (direction === 'prev' && i === (index === 0 ? carouselItems.length - 1 : index - 1)) {
                item.classList.add('prev');
            } else if (direction === 'next' && i === (index === carouselItems.length - 1 ? 0 : index + 1)) {
                item.classList.add('next');
            } else {
                item.classList.remove('prev');
                item.classList.remove('next');
            }
        });
    }
});
