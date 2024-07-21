document.addEventListener('DOMContentLoaded', function() {
    const userRole = document.getElementById('roleUser').innerHTML;
    console.log(userRole);

    if (userRole === "TEACHER") {
        fetchTeacherFeedback(userRole);
    } else if (userRole === "STUDENT") {
        fetchStudentFeedback(userRole);
    } else if (userRole === "PARENT") {
        fetchParentFeedback(userRole);
    }

    const ratingInputs = document.querySelectorAll('input[name="star"]');
    const selectedRatingDiv = document.getElementById('selected-rating');

    ratingInputs.forEach(input => {
        input.addEventListener('change', function() {
            const selectedValue = this.value;
            selectedRatingDiv.textContent = `Rating: ${selectedValue}`;
        });
    });
});

function formatDate(dateString) {
    const [day, month, year] = dateString.split('-');
    const date = new Date(`${year}-${month}-${day}`);
    const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    return new Intl.DateTimeFormat('vi-VN', options).format(date);
}

function fetchParentFeedback(userRole) {
    fetch('api/parent/view-student-feedback')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch parent feedback');
            }
            return response.text();
        })
        .then(text => {
            if (text) {
                return JSON.parse(text);
            } else {
                return [];
            }
        })
        .then(data => {
            data.sort((a, b) => new Date(b.createdAt.split('-').reverse().join('-')) - new Date(a.createdAt.split('-').reverse().join('-')));
            console.log(data);
            renderFeedbackTable(data, userRole);
        })
        .catch(error => console.error("Error fetching parent feedback:", error));
}

function fetchTeacherFeedback(userRole) {
    fetch('api/student/fetch-teacher-feedback')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch teacher feedback');
            }
            return response.text();
        })
        .then(text => {
            if (text) {
                return JSON.parse(text);
            } else {
                return [];
            }
        })
        .then(data => {
            data.sort((a, b) => new Date(b.createdAt.split('-').reverse().join('-')) - new Date(a.createdAt.split('-').reverse().join('-')));
            console.log(data);
            renderFeedbackTable(data, userRole);
        })
        .catch(error => console.error("Error fetching teacher feedback:", error));
}

function fetchStudentFeedback(userRole) {
    fetch('api/teacher/fetch-student-feedback')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch student feedback');
            }
            return response.text(); 
        })
        .then(text => {
            if (text) {
                return JSON.parse(text);
            } else {
                return [];
            }
        })
        .then(data => {
            data.sort((a, b) => new Date(b.createdAt.split('-').reverse().join('-')) - new Date(a.createdAt.split('-').reverse().join('-')));
            console.log(data);
            renderFeedbackTable(data, userRole);
        })
        .catch(error => console.error("Error fetching student feedback:", error));
}

function getRatingStars(rating) {
    let starsHtml = '';
    for (let i = 1; i <= 5; i++) {
        starsHtml += `<span class="star ${i <= rating ? 'filled' : ''}">&#9733;</span>`;
    }
    return starsHtml;
}

function renderFeedbackTable(feedbacks, userRole) {
    const tableBody = document.getElementById('classListBody');
    if (tableBody) {
        tableBody.innerHTML = ''; 
        if (userRole === "TEACHER") {
            feedbacks.forEach(feedback => {
                const row = document.createElement('tr');
    
                const dateCell = document.createElement('td');
                dateCell.textContent = formatDate(feedback.createdAt); 
                row.appendChild(dateCell);
    
                const contentCell = document.createElement('td');
                contentCell.textContent = feedback.description; 
                row.appendChild(contentCell);
    
                const ratingCell = document.createElement('td');
                ratingCell.innerHTML = getRatingStars(feedback.rating); 
                row.appendChild(ratingCell);
    
                tableBody.appendChild(row);
            });
        } else if (userRole === "STUDENT" || userRole === "PARENT") {
            feedbacks.forEach(feedback => {
                const row = document.createElement('tr');

                const nameCell = document.createElement('td');
                nameCell.textContent = feedback.actor.name; 
                row.appendChild(nameCell);

                const dateCell = document.createElement('td');
                dateCell.textContent = formatDate(feedback.createdAt); 
                row.appendChild(dateCell);
    
                const contentCell = document.createElement('td');
                contentCell.textContent = feedback.description; 
                row.appendChild(contentCell);
    
                const ratingCell = document.createElement('td');
                ratingCell.innerHTML = getRatingStars(feedback.rating); 
                row.appendChild(ratingCell);
    
                tableBody.appendChild(row);
            });
        }
    } else {
        console.error("Element with id 'classListBody' not found.");
    }
}
