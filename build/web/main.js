function betolt(){
    $.ajax({
        url:"Controller",
        type:"post",
        data:{"task":"betolt"},
        success:function(valasz){
            var adatokDiv = document.getElementById("adatok");
            for(var i = 0; i < valasz.length; i++){
                adatokDiv.innerHTML += "<div>";
                    adatokDiv.innerHTML += "<span>" + valasz[i].id + "</span>";
                    adatokDiv.innerHTML += "<span>" + valasz[i].cimzet + "</span>";
                    adatokDiv.innerHTML += "<span>" + valasz[i].felado + "</span>";
                    adatokDiv.innerHTML += "<span>" + valasz[i].suly + "</span>";
                    adatokDiv.innerHTML += "<span>" + valasz[i].meret + "</span>";
                    adatokDiv.innerHTML += "<span>" + valasz[i].ido + "</span>";
                adatokDiv.innerHTML += "</div>";
            }
            
        },
        error:function(){
            alert("hiba");
        }
    });
}

function login(){
    var user = document.getElementById("txtFelhasznalonev").value;
    var passwd = document.getElementById("txtJelszo").value;
    var captcha = document.getElementById("captcha").value;
    $.ajax({
        url:"Controller",
        type:"post",
        data:{"username":user, "password":passwd, "captcha":captcha, "task":"login"},
        success:function(valasz){
            alert(valasz.result);
            if(valasz.success == "1"){
                window.location = "profil.html";
            }
            
           
        },
        error:function(){
            alert("hiba");
        }
    });
}

function userdata(){   
    $.ajax({
        url:"Controller",
        type:"post",
        data:{"task":"userdata"},
        success:function(valasz){
            if(valasz.success == "1"){
                document.getElementById("udv").innerHTML = valasz.nev;
            }
            else{
                alert("Nem vagy bejelentkezve!");
                window.location = "index.html";
            }
           
        },
        error:function(){
            alert("hiba");
            window.location = "index.html";
        }
    });
    
}





