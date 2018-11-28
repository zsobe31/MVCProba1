
function betolt(){
    alert("alma");
    $.ajax({
        url:"Controller",
        type:"post",
        data:{},
    success:function(valasz){
        var adatokDIV = document.getElementById("adatok");
        for(var i = 0; i < valasz.length; i++){
            adatokDIV.innerHTML += "<div>";
                adatokDIV.innerHTML += "<span>" + valasz(i).id + "</span>";
                adatokDIV.innerHTML += "<span>" + valasz(i).cimzett + "</span>";
                adatokDIV.innerHTML += "<span>" + valasz(i).felado + "</span>";
                adatokDIV.innerHTML += "<span>" + valasz(i).meret + "</span>";
                adatokDIV.innerHTML += "<span>" + valasz(i).ido + "</span>";
                adatokDIV.innerHTML += "<span>" + valasz(i).suly + "</span>";
            adatokDIV.innerHTML += "</div>";
        }
    },
    error:function(){alert("hiba")}
   
    });
}



