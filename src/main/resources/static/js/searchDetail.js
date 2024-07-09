document.addEventListener("DOMContentLoaded", function () {
    const courseId = document.getElementById('courseId').innerText.trim();
    const centerId = document.getElementById('centerId').innerText.trim();
    const centerInfo = document.getElementById('center-info');
    const courseInfo = document.getElementById('course-info');

    console.log(courseId);
    console.log(centerId);

    if (centerId && courseId) {
        console.log("Course");
        fetch(`/courses-in-center/${centerId}`)
            .then(response => response.json())
            .then(data => {
                console.log('Courses in center data:', data);
                // Assuming data contains course details
                const course = data.filter(c => c.courseId === parseInt(courseId, 10));
                console.log(course)
                document.getElementById('course-description').innerText = course[0].description;
                console.log(course.description)
                document.getElementById('course-schedule').innerText = course[0].schedule;
                document.getElementById('course-instructor').innerText = course[0].teacher.name;
                courseInfo.style.display = 'block';
            })
            .catch(error => console.error('Error fetching courses in center data:', error));
    } else if (courseId) {
        console.log("Center");
        fetch(`/centers`)
            .then(response => response.json())
            .then(data => {
                console.log('Center data:', data);
                const centerFilter = data.filter(c => c.id === parseInt(centerId, 10));
                if (centerFilter.length > 0) {
                    const center = centerFilter[0];
                    document.getElementById('center-description').innerText = center.description;
                    document.getElementById('center-address').innerText = center.address;
                    document.getElementById('center-email').innerText = center.email;
                    document.getElementById('manager-name').innerText = center.manager.name;
                    document.getElementById('manager-phone').innerText = center.manager.phone;
                    document.getElementById('manager-email').innerText = center.manager.email;
                    centerInfo.style.display = 'block';
                } else {
                    console.log('No center found with the provided ID.');
                }
            })
            .catch(error => console.error('Error fetching center data:', error));



    } else {
        console.log('Center ID and/or Course ID is missing.');
    }
});
