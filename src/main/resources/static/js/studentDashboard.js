document.addEventListener("DOMContentLoaded", () => {
    const teacherId = 3; // example teacher ID
    const apiUrl = `http://localhost:8080/api/teachers/${teacherId}/courses`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            console.log(data[0])
            const courseContainer = document.getElementById('courseContainer');
            courseContainer.innerHTML = ''; // Clear any existing content

            data.forEach(courseString => {
                const courseArray = courseString.split(',');
                const courseName = courseArray[0];
                const courseDescription = courseArray[3];

                const courseBox = document.createElement('div');
                courseBox.className = 'box';
                courseBox.innerHTML = `
                    <img src="https://cdn3d.iconscout.com/3d/premium/thumb/online-course-7893341-6323813.png?f=webp" alt="">
                    <h3>${courseName}</h3>
                    <p>${courseDescription}</p>
                    <a href="#" class="btn">read more</a>
                `;
                courseContainer.appendChild(courseBox);
            });
        })
        .catch(error => {
            console.error('Error fetching course data:', error);
        });
});
