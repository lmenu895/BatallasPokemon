$(document).ready(function () {
  $(".slider").slick({
    infinite: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    speed: 500,
    centerMode: true,
    centerPadding: "0",
  });
});

$('.center').slick({
  centerMode: true,
  centerPadding: '60px',
  slidesToShow: 3,
  responsive: [
    {
      breakpoint: 768,
      settings: {
        arrows: false,
        centerMode: true,
        centerPadding: '40px',
        slidesToShow: 3
      }
    },
    {
      breakpoint: 480,
      settings: {
        arrows: false,
        centerMode: true,
        centerPadding: '40px',
        slidesToShow: 1
      }
    }
  ]
});

setInterval(() => {
  $(function() {

    if(($("#pokeball").css("opacity")) == 1){
      $("#tirar").val(100);
    }
    else if(($("#superball").css("opacity")) == 1){
      $("#tirar").val(500);
    }
    else if(($("#ultraball").css("opacity")) == 1){
      $("#tirar").val(1000);
    }
    else if (($("#masterball").css("opacity")) == 1){
      $("#tirar").val(10000);
    }

  })
}, 1);

