document.addEventListener("DOMContentLoaded", () => {
    var currentPage = 1;
    var itemsPerPage = 2;
    var allCourses = [];

    var paginationControls = document.getElementById("paginationControls");

    function fetchAndDisplayCourses() {
        Promise.all([
            fetch("/api/teachers/notifications/system").then(response => response.json()),
            fetch("/api/teachers/notifications/public").then(response => response.json())
        ])
            .then(data => {
                allPublicNotifications = data[0].concat(data[1]);
                allPublicNotifications.sort((a, b) => {
                    let dateA = new Date(a.createdAt);
                    let dateB = new Date(b.createdAt);
                    if (dateA < dateB) return 1;
                    if (dateA > dateB) return -1;
                    return a.id - b.id;
                });
                currentPage = 1; // Reset to first page
                console.log(allPublicNotifications)
                renderTable(allPublicNotifications);
            })
            .catch(error => console.error("Error fetching notifications:", error));
    }
});
