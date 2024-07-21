async function fetchCenters() {
    try {
        const response = await fetch('/centers');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const centers = await response.json();
        console.log(centers)

        const filteredCenters = centers.filter(center => center.status !== 'Wait_to_process');
        const sortedCenters = filteredCenters.sort((a, b) => new Date(a.time) - new Date(b.time));
        const limitedCenters = sortedCenters.slice(0, 7);
        console.log(limitedCenters)

        const swiperWrapper = document.getElementById('swiper-wrapper');
        if (!swiperWrapper) {
            throw new Error('Swiper wrapper element not found');
        }

        swiperWrapper.innerHTML = ''; // Clear existing slides

        for (const center of limitedCenters) {
            const courseCount = await fetchCourseCount(center.id);
            const slide = document.createElement('div');
            slide.classList.add('swiper-slide', 'tranding-slide');
            console.log(center)
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
                    ${courseCount > 0 ? `<button class="detail-button" onclick="displayCoursesModal(${center.id})">Danh sách khóa học</button>` : ''}
                    ${courseCount > 0 ? `<button class="detail-button" onclick="displayCoursesModal(${center.id})">Danh sách khóa học</button>` : ''}
                </div>
            `;

            swiperWrapper.appendChild(slide);
        }

        // Initialize Swiper after content is loaded
        const TrandingSlider = new Swiper('.tranding-slider', {
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
        const response = await fetch(`/courses-in-center/${centerId}`);
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
        const response = await fetch(`/courses-in-center/${centerId}`);
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
    console.log(courses)
    const modalContent = document.getElementById('modal-content');
    if (!modalContent) {
        console.error('Modal content element not found');
        return;
    }
    modalContent.innerHTML = '';

    courses.forEach(course => {
        const card = document.createElement('div');
        card.classList.add('course-card');

        card.innerHTML = `
            <h3>${course.name}</h3>
            <p>${course.description}</p>
            <p>Học phí: ${course.courseFee}.000VNĐ</p>
            <button onclick="registerCourse(${course.id})">Đăng ký</button>
        `;

        modalContent.appendChild(card);
    });

    const modal = document.getElementById('courses-modal');
    if (modal) {
        modal.style.display = 'block';
    } else {
        console.error('Courses modal element not found');
    }
}

function registerCourse(courseId) {
    console.log(`Registered for course ${courseId}`);
    window.location.href = "login"
}

window.onclick = function(event) {
    const modal = document.getElementById('courses-modal');
    if (modal && event.target === modal) {
        modal.style.display = 'none';
    }
}

fetchCenters();
document.addEventListener("DOMContentLoaded", function () {
    const searchForm = document.querySelector('.search');
    const searchInput = document.getElementById('searchInput');
    const searchType = document.getElementById('searchType');
    const resultBox = document.querySelector('.result-box');




});



$(document).ready(function() {
    function performSearch() {
        var keyword = $('#search-input').val();
        var searchType = $('#searchType').val();
        if (keyword.length >= 2) {
            $.ajax({
                url: '/api/student/searchh',
                type: 'GET',
                data: {
                    keyword: keyword,
                    searchType: searchType
                },
                success: function(response) {
                    var dropdown = '';
                    response.slice(0, 5).forEach(function(item) {
                        var detailUrl = (item.type === 'Course') ? `/searchDetail?courseId=${item.id}&centerId=${item.centerId}` : `/searchDetail?centerId=${item.id}`;
                        if(searchType == "all" ||
                            (searchType == "course" && item.type == "Course") ||
                            (searchType == "center" && item.type == "Center")){
                            dropdown += `<a href="javascript:void(0);" class="search-item" data-url="${detailUrl}">${item.type}: ${item.name}</a>`;
                        }
                    });
                    $('#search-results').html(dropdown).show();

                    $('.search-item').click(function() {
                        var url = $(this).data('url');
                        sessionStorage.setItem('searchDetails', $(this).text());
                        window.location.href = url;
                    });

                    $('.search').addClass('open');
                },
                error: function(error) {
                    console.error('Error fetching search results:', error);
                }
            });
        } else {
            $('#search-results').hide();
            $('.search').removeClass('open');
        }
    }

    $('#search-input').on('input', performSearch);
    $('#searchType').on('change', performSearch);

    $(document).click(function(event) {
        if (!$(event.target).closest('.search').length) {
            $('#search-results').hide();
            $('.search').removeClass('open');
        }
    });
});