document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const grade = urlParams.get('grade');
    const subject = decodeURIComponent(urlParams.get('subject'));

    // Set the value of the subjectName input field
    document.getElementById('subjectInput').value = `${subject} ${grade}`;
});
