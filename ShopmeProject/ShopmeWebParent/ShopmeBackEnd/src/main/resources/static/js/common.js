$(document).ready(function (){
    $("#Logoutlink").on("click", function (e){
        e.preventDefault();
        document.logoutForm.submit();
    });

    custumizeDropDownMenu();
    customizeTabs();
});

function custumizeDropDownMenu(){
    $(".navbar .dropdown").hover(
        function(){
            $(this).find('.dropdown-menu').first().stop(true, true).delay(250).slideDown();
        },
        function(){
            $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp();
        }
    );
    $(".dropdown > a").click(function (){
        location.href = this.href;
    });
}

function customizeTabs(){
    let url = document.location.toString();
    if(url.match('#')){
        $('.nav-tabs a[href="#' + url.split('#')[1] + '"]').tab('show');
    }

    $('.nav-tabs a').on('shown.bs.tab', function(e){
        window.location.hash = e.target.hash;
    })
}

