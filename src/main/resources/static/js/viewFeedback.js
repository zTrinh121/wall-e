document.addEventListener('DOMContentLoaded', function() {
    fetchStudentFeedback();
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
    // Parse the date string and create a Date object
    const [day, month, year] = dateString.split('-');
    const date = new Date(`${year}-${month}-${day}`);
    // Format the date to 'dd/MM/yyyy'
    const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    return new Intl.DateTimeFormat('vi-VN', options).format(date);
}
function fetchStudentFeedback() {
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
                return []; // Return an empty array if the response is empty
            }
        })
        .then(data => {
            data.sort((a, b) => new Date(b.createdAt.split('-').reverse().join('-')) - new Date(a.createdAt.split('-').reverse().join('-')));
            console.log(data);
            renderFeedbackTable(data);
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

function renderFeedbackTable(feedbacks) {
    const tableBody = document.getElementById('classListBody');
    if (tableBody) {
        tableBody.innerHTML = ''; // Clear existing content

        feedbacks.forEach(feedback => {
            const row = document.createElement('tr');

            const dateCell = document.createElement('td');
            dateCell.textContent = formatDate(feedback.createdAt); // Assuming 'date' is a property in feedback
            row.appendChild(dateCell);

            const contentCell = document.createElement('td');
            contentCell.textContent = feedback.description; // Assuming 'content' is a property in feedback
            row.appendChild(contentCell);

            const ratingCell = document.createElement('td');
            ratingCell.innerHTML = getRatingStars(feedback.rating); // Assuming 'rating' is a property in feedback
            row.appendChild(ratingCell);

            tableBody.appendChild(row);
        });
    } else {
        console.error("Element with id 'classListBody' not found.");
    }
}
