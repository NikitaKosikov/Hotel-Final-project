/* Индекс слайда по умолчанию */
let slideIndexService = 1;
document.getElementById("current_slide_orders").innerHTML = slideIndexService;
showSlidesServices(slideIndexService);

/* Функция увеличивает индекс на 1, показывает следующй слайд*/
function plusSlideServices() {

    showSlidesServices(slideIndexService += 1);
    document.getElementById("current_slide_orders").innerHTML = slideIndexService;
}

/* Функция уменьшяет индекс на 1, показывает предыдущий слайд*/
function minusSlideServices() {
    showSlidesServices(slideIndexService -= 1);
    document.getElementById("current_slide_orders").innerHTML = slideIndexService;
}

/* Устанавливает текущий слайд */
function currentSlideServices(n) {
    showSlidesServices(slideIndexService = n);
}

/* Основная функция слайдера */
function showSlidesServices(n) {
    let i;
    let slides = document.getElementsByClassName("item_services");
    if (n > slides.length) {
        slideIndexService = 1
    }
    if (n < 1) {
        slideIndexService = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndexService - 1].style.display = "block";
}

function my_submit()
{
    let roomId = document.getElementById('type_service').innerHTML;
    console.log(roomId);
}
