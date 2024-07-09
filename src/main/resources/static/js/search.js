document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const keyword = urlParams.get('keyword');
    if (keyword) {
        fetch(`/api/student/searchh?keyword=${keyword}`)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                const resultsContainer = document.querySelector('.img-gallery-container');
                resultsContainer.innerHTML = '';

                data.forEach(item => {
                    const options = { day: "2-digit", month: "2-digit", year: "numeric" };

                    const isoStartDate = item.startDate;
                    const dateStartObject = new Date(isoStartDate);
                    const formattedStartDate = dateStartObject.toLocaleDateString("en-GB", options);

                    const isoEndDate = item.endDate;
                    const dateEndObject = new Date(isoEndDate);
                    const formattedEndDate = dateEndObject.toLocaleDateString("en-GB", options);


                    console.log(formattedStartDate)
                    console.log(formattedEndDate)
                    const listItem = document.createElement('li');
                    listItem.setAttribute('data-id', item.id);
                    listItem.setAttribute('data-type', item.type);
                    listItem.setAttribute('data-name', item.name);
                    listItem.setAttribute('data-description', item.description);
                    listItem.setAttribute('data-startDate', formattedStartDate);
                    listItem.setAttribute('data-endDate', formattedEndDate);
                    listItem.setAttribute('data-amountOfStudents', item.amountOfStudents);
                    listItem.setAttribute('data-fee', item.fee);
                    if(item.type === "Course"){
                        listItem.innerHTML = `
                        <span></span>
                        <div class="img-gal">
                            <img src="https://www.shutterstock.com/image-vector/3d-web-vector-illustrations-online-600nw-2152289507.jpg" />
                            <div class="main-content">
                                <h3>Khóa học: ${item.name}</h3>
                                ${item.startDate ? `
                                <p><span>Thời gian:</span> ${formattedStartDate} - ${formattedEndDate}</p>
                                <p><span>Học phí:</span> ${item.fee}</p>
                                
<!--                                <p><span>Amount of Students:</span> ${item.amountOfStudents}</p>-->` : `
                                <p><span>Description:</span> ${item.description}</p>
                                `}
                            </div>
                        </div>
                        <div class="float-gallery-content">
                            <div class="content uk-text-left">
                                <span class="highlight uk-block">More information</span>
                                <a href="#">Click to get detail</a>
                            </div>
                            <div class="content-btn">
                                <button type="button" class="show-details-btn">
                                    &#8594;
                                </button>
                            </div>
                        </div>
                    `;
                    }
                    else{


                    }


                    resultsContainer.appendChild(listItem);
                });

                document.querySelectorAll('.show-details-btn').forEach(button => {
                    button.addEventListener('click', function () {
                        const courseItem = this.closest('li');
                        const itemType = courseItem.getAttribute('data-type');
                        const courseTitle = courseItem.getAttribute('data-name');
                        const courseDescription = courseItem.getAttribute('data-description');
                        const courseAmountOfStudents = courseItem.getAttribute('data-amountOfStudents');
                        const coursePrice = courseItem.getAttribute('data-fee');
                        const courseId = courseItem.getAttribute('data-id');
                        const courseStartDate = courseItem.getAttribute('data-startDate');
                        const courseEndDate = courseItem.getAttribute('data-endDate');

                        document.getElementById('modal-title').innerText = courseTitle;
                        document.getElementById('modal-description').innerText = courseDescription;
                        document.getElementById('modal-price').innerText = coursePrice;
                        document.getElementById('modal-amountofstudents').innerText = courseAmountOfStudents;
                        document.getElementById('modal-startdate').innerText = courseStartDate;
                        document.getElementById('modal-enddate').innerText = courseEndDate;

                        if (itemType === 'Center') {
                            fetch(`/api/student/center/${courseId}/courses`)
                                .then(response => response.json())
                                .then(courses => {
                                    const courseListContainer = document.getElementById('course-list-container');
                                    const courseList = document.getElementById('course-list');
                                    courseList.innerHTML = ''; // Clear previous courses

                                    courses.forEach(course => {
                                        const courseListItem = document.createElement('li');
                                        courseListItem.innerText = `${course.name} - ${course.description}`;
                                        courseList.appendChild(courseListItem);
                                    });

                                    courseListContainer.style.display = 'block';
                                })
                                .catch(error => console.error('Error fetching courses by center:', error));
                        } else {
                            document.getElementById('course-list-container').style.display = 'none';
                        }

                        const modal = UIkit.modal('#modal-example');
                        modal.show();

                        document.getElementById('registerBtn').addEventListener('click', function () {
                            fetch('/api/v1/payment/courseId', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({ courseId: parseInt(courseId) })
                            })
                                .then(response => response.json())
                                .then(data => {
                                    console.log('CourseId saved to session:', data);
                                    fetch('api/v1/payment/auth/status')
                                        .then(response => response.json())
                                        .then(isAuthenticated => {
                                            if (isAuthenticated) {
                                                const paymentUrlEndpoint = `http://localhost:8080/api/v1/payment/vn-pay?amount=${coursePrice}&bankCode=NCB&courseId=${courseId}`;

                                                fetch(paymentUrlEndpoint)
                                                    .then(response => response.json())
                                                    .then(data => {
                                                        const paymentUrl = data.data.paymentUrl;
                                                        if (paymentUrl) {
                                                            window.location.href = paymentUrl;
                                                        } else {
                                                            console.error('Payment URL is missing in response');
                                                        }
                                                    });
                                            } else {
                                                window.location.href = '/login';
                                            }
                                        });
                                })
                                .catch(error => {
                                    console.error('Error saving courseId to session:', error);
                                });
                        });
                    });
                });
            })
            .catch(error => console.error('Error fetching search results:', error));
    }
});

$(document).ready(function() {
    $('#search-input').on('input', function() {
        var keyword = $(this).val();
        if (keyword.length >= 2) {
            $.ajax({
                url: '/api/student/search',
                type: 'GET',
                data: { keyword: keyword },
                success: function(response) {
                    var dropdown = '';
                    response.slice(0, 5).forEach(function(item) {
                        console.log(item)
                        var detailUrl = (item.type === 'Course') ? `/courseDetail?courseId=${item.id}` : `/centerDetail?centerId=${item.id}`;
                        dropdown += `<a href="" class="search-item" data-url="">${item.type}: ${item.name}</a>`;
                    });
                    $('#search-results').html(dropdown).show();

                    // Thêm sự kiện click cho mỗi kết quả tìm kiếm
                    $('.search-item').click(function() {
                        var url = $(this).data('url');
                        sessionStorage.setItem('searchDetails', $(this).text());
                        window.location.href = url;
                    });
                },
                error: function(error) {
                    console.error('Error fetching search results:', error);
                }
            });
        } else {
            $('#search-results').hide();
        }
    });

    $(document).click(function(event) {
        if (!$(event.target).closest('.search').length) {
            $('#search-results').hide();
        }
    });
});
