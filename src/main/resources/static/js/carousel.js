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
        showSlide(currentIndex);
    });
    nextButton.addEventListener('click', function () {
        currentIndex = (currentIndex === carouselItems.length - 1) ? 0 : currentIndex + 1;
        showSlide(currentIndex);
    });

    // Function to show slide at given index
    function showSlide(index) {
        carouselItems.forEach((item, i) => {
            if (i === index) {
                item.classList.add('active');
            } else {
                item.classList.remove('active');
            }
        });
    }
});