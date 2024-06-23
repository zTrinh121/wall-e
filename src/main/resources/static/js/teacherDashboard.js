document.addEventListener("DOMContentLoaded", () => {
    // var currentUrl = window.location.href;
    // var urlParams = new URLSearchParams(currentUrl);
    // console.log('URL Params:', urlParams);
    var userId = document.getElementById("userId").innerHTML;
    // urlParams.forEach(function(value, key) {
    //     userId = value;
    // });
    // console.log(userId)
    const boxCourses = document.getElementById("courseBoxes");
    const apiUrl = `/api/students/${userId}/courses`;
    var itemsPerPage = 4; // Number of posts per page
    var currentPage = 1;
    var noResultDiv = document.getElementById("no-result");
    var paginationControls = document.getElementById("paginationControls");

    function fetchPosts() {
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
            posts.forEach(post => {
                var row = `      
                    <div class="box" id="${post.courseId}">
                        <img src="https://cdn3d.iconscout.com/3d/premium/thumb/online-course-7893341-6323813.png?f=webp" alt="">
                        <h3>${post.courseName}</h3>
                        <p>Giáo viên: ${post.teacherName} tại trung tâm ${post.centerName}</p>
                        <p>Số lượng học sinh: ${post.amountOfStudents}</p>
                        <a href="/course-details?userId=${userId}&courseId=${post.courseId}" data-courseId=${post.courseId} 
                        data-teacherId={post.teacherId} data-studentId={post.studentId} data-courseCode={post.courseCode} 
                        data-amountOfStudents={post.amountOfStudents} data-startTime={post.startTime} data-endTime={post.endTime} 
                        data-centerName={post.centerName} data-desc={posts.courseDesc} data-courseName={posts.courseName} >Xem chi tiết</a>
                    </div>
                `;
                boxCourses.insertAdjacentHTML("beforeend", row);
            });
        }
    }

    function renderTable(postList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
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




