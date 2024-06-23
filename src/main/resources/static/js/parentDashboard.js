document.addEventListener("DOMContentLoaded", () => {

    const boxCourses = document.getElementById("courseBoxes");
    const apiUrl = `/parent/courses`;
    var itemsPerPage = 4; // Number of posts per page
    var currentPage = 1;
    var noResultDiv = document.getElementById("no-result");
    var paginationControls = document.getElementById("paginationControls");

    function fetchPosts() {
        console.log("API url trang parent: "+ apiUrl)
        fetch(apiUrl)
            .then(response => response.json())
            .then(data => {
                currentPage = 1; // Reset to first page
                console.log(data)
                renderTable(data);
            })
            .catch(error => console.error("Error fetching posts:", error));
    }

    function displayPosts(posts) {
        boxCourses.innerHTML = "";

        if (posts.length === 0) {
            noResultDiv.style.display = "block";
        } else {
            noResultDiv.style.display = "none";
            console.log(posts)
            posts.forEach(post => {
                var row = `      
                    <div class="box" id="${post.id}">
                        <img src="https://cdn3d.iconscout.com/3d/premium/thumb/online-course-7893341-6323813.png?f=webp" alt="">
                        <h3>${post.code}</h3>
                        <p>Trung tâm: ${post.center.code}</p>
                        <p>Địa chỉ: ${post.center.address}</p>
                        <p>Số lượng học sinh: ${post.amountOfStudents}</p>
                    </div>
                `;
                boxCourses.insertAdjacentHTML("beforeend", row);
            });


        }
    }

    function renderTable(postList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        console.log(postList)
        const postsToDisplay = postList.slice(start, end);

        displayPosts(postsToDisplay);
        renderPaginationControls(postList);
    }

    function renderPaginationControls(postList) {
        const totalPages = Math.ceil(postList.length / itemsPerPage);
        paginationControls.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            pageButton.classList.add('page-button');
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.addEventListener('click', () => {
                currentPage = i;
                renderTable(postList);
            });
            paginationControls.appendChild(pageButton);
        }
    }

    fetchPosts();


});




