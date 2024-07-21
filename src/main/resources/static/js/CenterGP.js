//______VARIABLES_____ yt from 21:40 https://www.youtube.com/watch?v=zMP4yPAjOg8&list=PLjdWqMkFqNVROW19RaggSt5ZyxxBWTVca&index=2
const blog_id = "5720367735953850280";
const CLIENT_ID = "649937824932-m3m1d1qglej341fj6hhlivcrv0hu1hgl.apps.googleusercontent.com";
const CLIENT_SECRET = "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";
const API_CONSOLE_LINK = "https://console.cloud.google.com/apis/dashboard?authuser=0&project=prj-oauth-blog-a-1719439186201&pli=1"
const linkToGetToken = `https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/blogger&response_type=token&redirect_uri=http://127.0.0.1:5500/Post/add_post.html&client_id=${CLIENT_ID}`;
// const linkToGetToken = `https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/blogger&response_type=token&redirect_uri=http://127.0.0.1:5500/Post/add_post.html&client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}`;

 //linkToGetToken -> thay doi dua tren api
 //client id + client secret + api console => get in new three web
//________________________CODE_______________________
document.addEventListener("DOMContentLoaded", () => {
    // let token = getToken();
    // let token = "ya29.a0AXooCgs2G4f8JMCuNvuLl631AKPOExBiv13R6jK5_1GmC8mG4ry0P3gGcnXLB9Id8IXeCyM2JEl8h3OT9GkB3j7tu0ZEk_vPnpEZyTtlpAFmpka8jrUi-jHeKAdRBr3i59scoqa_D_Gqg7CqhPiUv9iZoomhsSgjnwaCgYKARcSARASFQHGX2MiFE_Ai5VfwZfezrrp1YYL1A0169";
    // console.log(token);
    const addPost = document.getElementById("submit");
    const addTitle = document.getElementById("add_title");
    addPost.addEventListener("click", () => {
        console.log("CLICKED !!!!");
            // let token = "ya29.a0AXooCgs2G4f8JMCuNvuLl631AKPOExBiv13R6jK5_1GmC8mG4ry0P3gGcnXLB9Id8IXeCyM2JEl8h3OT9GkB3j7tu0ZEk_vPnpEZyTtlpAFmpka8jrUi-jHeKAdRBr3i59scoqa_D_Gqg7CqhPiUv9iZoomhsSgjnwaCgYKARcSARASFQHGX2MiFE_Ai5VfwZfezrrp1YYL1A0169";
        let token = getToken();
        if(token){
            const titlePost = addTitle.innerText;   //maybe: addTitle.value for replacement
            const editorData = editor.getData();
            const contentPost = editorData;
            const dataPost = {
                title: titlePost,
                content: contentPost,
            };
            handleAddPost(dataPost, token);
        } else{
            accessToken(linkToGetToken);
        }
    });
});

async function handleAddPost(dataPost, token){
    const postData = {
        kind: "blogger#post",
        blog: {
            id: blog_id,
        },
        title: dataPost.title,
        content: dataPost.content,
    };

    //method: POST
    const urlPost = `https://www.googleapis.com/blogger/v3/blogs/${blog_id}/posts/`;

    const response = await fetch(urlPost, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
        body: JSON.stringify(postData),
    });
    const data = await response.json();
    console.log(data);
};

function accessToken(linkToGetToken){
    window.location.href = linkToGetToken;
};
function getToken(){
    const accessTokenKey = localStorage.getItem("AccessTokenOnLocalStorage");
    if(accessTokenKey){
        return accessTokenKey;
    } else{
        const url = new URLSearchParams(window.location.hash.substring(1));
        console.log(url);
        const accessToken = url.get("access_token");
        localStorage.setItem("AccessTokenOnLocalStorage", accessToken);
        return accessToken;
    }
};

