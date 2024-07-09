//lay so luong
document.addEventListener("DOMContentLoaded", () => {
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    var urlParts = urlParams.split("centerIdn=");
    centerIdzzz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;

function fetchCount(centerIdzzz) {
    var URLs = `/manager/student-count/${centerIdzzz}`;
    var URLt = `/manager/teacher-count/${centerIdzzz}`;
    var URLc = `/manager/course-count/${centerIdzzz}`;

    fetch(URLs)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                document.getElementById("nums").innerText = data;
            })
            .catch((error) => console.error("Error fetching centers:", error));
    fetch(URLt)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                document.getElementById("numt").innerText = data;
            })
            .catch((error) => console.error("Error fetching centers:", error));
    fetch(URLc)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                  document.getElementById("numc").innerText = data;

            })
            .catch((error) => console.error("Error fetching centers:", error));
}
fetchCount(centerIdzzz);
});
