document.addEventListener("DOMContentLoaded", () => {
    const boxCourses = document.getElementById("courseBoxes");
    const studentApiUrl = `students`;
    const itemsPerPage = 4;
    let currentPage = 1;
    const noResultDiv = document.getElementById("no-result");
    const paginationControls = document.getElementById("paginationControls");
    const header = document.getElementsByClassName("header")[0];
    let studentId;

    async function fetchStudents() {
        try {
            const response = await fetch(studentApiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            console.log("Fetched students: ", data);
            studentId = data[0].id;
            console.log("Id student: " + studentId);
            // After fetching students, proceed to fetch posts
            await fetchPosts();
        } catch (error) {
            console.error("Error fetching students:", error);
            header.style.display = "none";
            noResultDiv.style.display = "block";
            noResultDiv.innerHTML = `Hãy <a class="mapping" href="/mapping"> kết nối </a> với con bạn để truy cập vào khóa học`;
        }
    }

    async function fetchPosts() {
        const apiUrl = `/api/students/${studentId}/courses`;
        console.log("API url trang parent: " + apiUrl);
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            currentPage = 1; // Reset to first page
            console.log(data);
            renderTable(data);
        } catch (error) {
            console.error("Error fetching posts:", error);
        }
    }

    function displayPosts(posts) {
        boxCourses.innerHTML = "";

        if (posts.length === 0) {
            noResultDiv.style.display = "block";
        } else {
            noResultDiv.style.display = "none";
            console.log(studentId)
            posts.forEach(post => {
                const row = `      
                    <div class="box" id="${post.courseId}">
                        <img src="https://cdn3d.iconscout.com/3d/premium/thumb/online-course-7893341-6323813.png?f=webp" alt="">
                        <h3>${post.courseName}</h3>
                        <p>Giáo viên: ${post.teacherName} tại trung tâm ${post.centerName}</p>
                        <p>Số lượng học sinh: ${post.amountOfStudents}</p>
                        <a href="/course-details?userId=${studentId}&courseId=${post.courseId}" data-courseId="${post.courseId}" 
                        data-teacherId="${post.teacherId}" data-studentId="${post.studentId}" data-courseCode="${post.courseCode}" 
                        data-amountOfStudents="${post.amountOfStudents}" data-startTime="${post.startTime}" data-endTime="${post.endTime}" 
                        data-centerName="${post.centerName}" data-desc="${post.courseDesc}" data-courseName="${post.courseName}" >Xem chi tiết</a>
                    </div>
                `;
                boxCourses.insertAdjacentHTML("beforeend", row);
            });
        }
    }

    function renderTable(postList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        console.log(postList);
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

    fetchStudents();
});
