document.addEventListener("DOMContentLoaded", () => {
    // Toggle sidebar collapse
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });

});
const blog_id = "1";
document.addEventListener("DOMContentLoaded", () => {
    const addPost = document.getElementById("submit");
    const addTitle = document.getElementById("add_title");
    addPost.addEventListener("click", () => {
        console.log("CLICKED !!!!");
        const titlePost = addTitle.innerText;   //maybe: addTitle.value for replacement
        const editorData = editor.getData();
        const contentPost = editorData;
        const dataPost = {
            title: titlePost,
            content: contentPost,
        };
        handleAddPost(dataPost);
    });
});

function handleAddPost(dataPost){
    const postData = {
        title: dataPost.title,
        content: dataPost.content,
    };

    //method: POST
    const urlPost = `/centerPosts/create`;

    const response = fetch(urlPost, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(postData),
    });
    const data = response.json();
    console.log(data);
};

