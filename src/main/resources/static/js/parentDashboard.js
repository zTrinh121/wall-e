document.addEventListener("DOMContentLoaded", () => {
    const boxCourses = document.getElementById("courseBoxes");
    const studentApiUrl = `/api/parent/studentsByParent`;
    const itemsPerPage = 4;
    let currentPage = 1;
    const noResultDiv = document.getElementById("no-result");
    const paginationControls = document.getElementById("paginationControls");
    const header = document.getElementsByClassName("header")[0];
    let studentId = localStorage.getItem('studentId');
    let studentName = localStorage.getItem('studentName');
    

    function getStudentIdFromUrl() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('studentId');
    }

    function updateURLWithStudentId(studentId) {
        const currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('studentId', studentId);
        history.replaceState(null, '', currentUrl);
    }

    async function fetchStudents() {
        try {
            const response = await fetch(studentApiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            console.log(data);
            if(data.length > 1){
                displayStudentSelection(data);  
            } else {
                studentId = data[0].id;
                studentName = data[0].name;
                updateURLWithStudentId(studentId);
                localStorage.setItem('studentId', studentId);
                localStorage.setItem('studentName', studentName);

            }
            
            // After fetching students, proceed to fetch posts
            await fetchPosts();
        } catch (error) {
            console.error("Error fetching students:", error);
            header.style.display = "none";
            noResultDiv.style.display = "block";
            noResultDiv.innerHTML = `Hãy <a class="mapping" href="/mapping"> kết nối </a> với con bạn để truy cập vào khóa học`;
        }
    }

    function displayStudentSelection(students) {
        const container = document.getElementById('studentSelectionContainer');
        container.innerHTML = '';
    
        const select = document.createElement('select');
        select.id = 'studentSelect';
    
        // Create options for the select element
        students.forEach(student => {
            const option = document.createElement('option');
            option.value = student.id;
            option.textContent = student.name;
            select.appendChild(option);
        });
    
        // Check for saved studentId and studentName in local storage
        const savedStudentId = localStorage.getItem('studentId');
        const savedStudentName = localStorage.getItem('studentName');
    
        if (savedStudentId && savedStudentName) {
            select.value = savedStudentId;
        }
    
        // Add event listener for change event
        select.addEventListener('change', async () => {
            const selectedStudentId = select.value;
            const selectedStudentName = select.options[select.selectedIndex].text;
            console.log(selectedStudentId, selectedStudentName);
            if (selectedStudentId) {
                studentId = selectedStudentId;
                studentName = selectedStudentName;
                localStorage.setItem('studentId', studentId);
                localStorage.setItem('studentName', studentName);
                updateURLWithStudentId(studentId);
                await fetchPosts();
            }
        });
    
        container.appendChild(select);
    
        // Fetch posts if a student is already selected
        if (savedStudentId && savedStudentName) {
            studentId = savedStudentId;
            studentName = savedStudentName;
            updateURLWithStudentId(studentId);
            fetchPosts();
        }
    }
    

    async function fetchPosts() {
        const apiUrl = `/api/student/${studentId}/courses`;
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            currentPage = 1;
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

    studentId = getStudentIdFromUrl();
    if (!studentId) {
        fetchStudents();
    } else {
        fetchPosts();
    }
});
