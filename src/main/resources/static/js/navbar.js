function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setLinks(){
    const navbar = document.getElementById("place");
    const cookie = getCookie("session");
    if(cookie===""){
        navbar.innerHTML+="<li class=\"nav-item\">\n" +
            "                    <a class=\"nav-link active\" aria-current=\"page\" href=\"/\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item\">\n" +
            "                    <a class=\"nav-link\" href=\"/register\">Register</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item\">\n" +
            "                    <a class=\"nav-link\" href=\"/login\">Login</a>\n" +
            "                </li>"
    }else{
        navbar.innerHTML+="<li class=\"nav-item\">\n" +
            "                    <a class=\"nav-link active\" aria-current=\"page\" href=\"/\">Home</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item\">\n" +
            "                    <a class=\"nav-link\" href=\"/makeGame\">Create Game</a>\n" +
            "                </li>\n" +
            "                <li class=\"nav-item\">\n" +
            "                    <a class=\"nav-link\" href=\"/getGame\">Get Game</a>\n" +
            "                </li>"
    }
}

setTimeout(function (){
    setLinks();
},400)