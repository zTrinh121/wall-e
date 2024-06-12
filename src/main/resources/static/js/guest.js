async function fetchCenters() {
    try {
        const response = await fetch('/centers');
        const centers = await response.json();


        const filteredCenters = centers.filter(center => center.status !== 'Wait_to_process');
        const sortedCenters = filteredCenters.sort((a, b) => new Date(a.time) - new Date(b.time));
        const limitedCenters = sortedCenters.slice(0, 7);


        const swiperWrapper = document.getElementById('swiper-wrapper');

        for (const center of limitedCenters) {
            const courseCount = await fetchCourseCount(center.id);
            const slide = document.createElement('div');
            slide.classList.add('swiper-slide', 'tranding-slide');

            slide.innerHTML = `
                    <div class="tranding-slide-content">
                        <div class="tranding-slide-img">
                            <img src="images/edudesc.jpg" alt="${center.name}">
                        </div>
                        <div class="content-detail">
                            <h1 class="center-title">${center.name}</h1>
                            <ul class="center-desc">
                                <h2>Mô tả trung tâm</h2>
                                <li>${center.description}</li>
                                <li>Địa chỉ: ${center.address}</li>
                                <li>Điện thoại: ${center.phone}</li>
                                <li>Email: ${center.email}</li>
                                ${courseCount > 0 ? `<li>Số lượng khóa học: ${courseCount}</li>` : ''}
                            </ul>
                        </div>
                        ${courseCount > 0 ? `<button class="detail-button" onclick="displayCoursesModal(${center.id})">Xem chi tiết danh sách khóa học</button>` : ''}
                    </div>
                `;

            swiperWrapper.appendChild(slide);
        }

        // Initialize Swiper after content is loaded
        var TrandingSlider = new Swiper('.tranding-slider', {
            effect: 'coverflow',
            grabCursor: true,
            centeredSlides: true,
            loop: true,
            slidesPerView: 'auto',
            coverflowEffect: {
                rotate: 0,
                stretch: 0,
                depth: 100,
                modifier: 2.5,
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            }
        });
    } catch (error) {
        console.error('Error fetching centers:', error);
    }
}

async function fetchCourseCount(centerId) {
    try {
        const response = await fetch(`http://localhost:8080/courses-in-center/${centerId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const courses = await response.json();
        return Array.isArray(courses) ? courses.length : 0;
    } catch (error) {
        console.error(`Error fetching courses for center ${centerId}:`, error);
        return 0;
    }
}

async function fetchCourses(centerId) {
    try {
        const response = await fetch(`http://localhost:8080/courses-in-center/${centerId}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        console.error(`Error fetching courses for center ${centerId}:`, error);
        return [];
    }
}

async function displayCoursesModal(centerId) {
    const courses = await fetchCourses(centerId);
    const modalContent = document.getElementById('modal-content');
    modalContent.innerHTML = '';

    courses.forEach(course => {
        const card = document.createElement('div');
        card.classList.add('course-card');

        card.innerHTML = `
            <h3>${course.name}</h3>
            <p>${course.description}</p>
            <p>Course Fee: ${course.courseFee}</p>
            <button onclick="registerCourse(${course.id})">Register</button>
        `;

        modalContent.appendChild(card);
    });

    const modal = document.getElementById('courses-modal');
    modal.style.display = 'block';
}

function registerCourse(courseId) {
    // Implement registration logic here
    console.log(`Registered for course ${courseId}`);
}

window.onclick = function(event) {
    const modal = document.getElementById('courses-modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

fetchCenters();

document.addEventListener("DOMContentLoaded", function () {
    const searchForm = document.querySelector('.search');
    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const searchType = document.getElementById('searchType').value;
        const searchInput = document.getElementById('searchInput').value;
        performSearch(searchType, searchInput);
    });
});

function performSearch(searchType, searchInput) {
    let apiUrl = '';
    console.log(searchType + "" + searchInput)
    switch (searchType) {
        case 'all':
            apiUrl = '/api/search?all=' + encodeURIComponent(searchInput);
            break;
        case 'center':
            apiUrl = '/api/centers?name=' + encodeURIComponent(searchInput);
            break;
        case 'teacher':
            apiUrl = '/api/users?role=3&name=' + encodeURIComponent(searchInput);
            break;
        case 'course':
            apiUrl = '/api/courses?name=' + encodeURIComponent(searchInput);
            break;
        case 'center-post':
            apiUrl = '/api/center-posts?title=' + encodeURIComponent(searchInput);
            break;
        default:
            console.error('Invalid search type');
            return;
    }

    // Perform API request using apiUrl
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            // Handle search results
            console.log(data);
        })
        .catch(error => console.error('Error performing search:', error));
}
