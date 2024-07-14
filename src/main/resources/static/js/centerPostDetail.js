document.addEventListener("DOMContentLoaded", () => {
    // Toggle sidebar collapse
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });
    //close modal ifraem
    var closeButton = document.querySelectorAll(".close");
    closeButton.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            deleteModal.style.display = "none";
            fetchCenterPosts();
        });
    });

    // Modals
    var addCentreModal = document.getElementById("addCentreModal");
    var deleteModal = document.getElementById("deleteModal");

    // Open Add Centre Modal
    var addCentreBtn = document.getElementById("addCentreBtn");
    if (addCentreBtn) {
        addCentreBtn.addEventListener("click", () => {
            console.log("Opening Add Centre Modal");
            addCentreModal.style.display = "block";
        });
    }

    // Close Add Centre Modal
    var cancelAddButton = document.getElementById("cancelAdd");
    if (cancelAddButton) {
        cancelAddButton.addEventListener("click", () => {
            addCentreModal.style.display = "none";
        });
    }

    window.addEventListener("click", function (e) {
        if (e.target == addCentreModal) {
            addCentreModal.style.display = "none";
        }
    });

    // Close Delete Modal
    var cancelDeleteButton = document.getElementById("cancelDeleteButton");
    if (cancelDeleteButton) {
        cancelDeleteButton.addEventListener("click", () => {
            deleteModal.style.display = "none";
        });
    }

    window.addEventListener("click", function (e) {
        if (e.target == deleteModal) {
            deleteModal.style.display = "none";
        }
    });

    // Fetch posts and display
    const URL = "/manager/centerPosts";

    function fetchCenterPosts() {
        fetch(URL)
            .then((response) => response.json())
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayCenterPostsList(data);
            })
            .catch((error) => console.error("Error fetching posts:", error));
    }

    fetchCenterPosts();

    // Helper function to show toast message
    function showToast(message) {
        var toast = document.getElementById("toast");
        toast.querySelector("p").textContent = message;
        toast.classList.add("show");
        setTimeout(function () {
            toast.classList.remove("show");
        }, 2000);
    }
    // Function to fetch post details and open modal
    function openModalWithPostDetails(postId) {
        var url = `/manager/centerPosts`;
        fetch(url)
            .then((response) => response.json())
            .then((data) => {
                console.log("Center details:", data);
                var queryUrl = "/manager/centerHome?";
                queryUrl += "centerIdn=" + encodeURIComponent(data.id);
                console.log(queryUrl);

                window.location.href = queryUrl;
            })
            .catch((error) => console.error("Error fetching center details:", error));
    }

    // Add Centre
    var addCentreForm = document.getElementById("addCentreForm");
    if (addCentreForm) {
        addCentreForm.addEventListener("submit", function (event) {
            event.preventDefault();
            var formData = new FormData(addCentreForm);

            var centerData = {
                name: formData.get("addCenterName"),
                address: formData.get("addCenterAddress"),
                description: formData.get("addCenterDesc")
                // Handle file upload if necessary
            };

            fetch("/manager/center/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(centerData)
                })
            .then(response => response.json())
            .then(data => {
                console.log("Center added:", data);
                addCentreModal.style.display = "none";
                fetchCenterPosts();
                showToast("Thêm trung tâm thành công");
            })
            .catch(error => console.error("Error adding center:", error));
        });
    }

    var noResultDiv = document.getElementById("no-result");

    //date
    document.getElementById("currentDate").innerText = getCurrentDate();
    function getCurrentDate() {
            var currentDate = new Date();
            var dayOfWeek = [
              "Chủ Nhật",
              "Thứ Hai",
              "Thứ Ba",
              "Thứ Tư",
              "Thứ Năm",
              "Thứ Sáu",
              "Thứ Bảy",
            ];
            var day = String(currentDate.getDate()).padStart(2, "0");
            var month = String(currentDate.getMonth() + 1).padStart(2, "0");
            var year = currentDate.getFullYear();
            var dayIndex = currentDate.getDay();
            var dayName = dayOfWeek[dayIndex];
            return dayName + ", " + day + "-" + month + "-" + year;
          }
});

//test
function fetchAndDisplayCenterPosts() {
    fetch("/manager/centerPosts")
        .then(response => response.json())
        .then(data => {
            renderPostTable(data);
        })
        .catch(error => console.error("Error fetching center posts:", error));
}

function fetchAndDisplayCenterPostsByCenterId(centerId) {
    fetch(`/manager/centerPosts/center/${centerId}`)
        .then(response => response.json())
        .then(data => {
            renderPostTable(data);
        })
        .catch(error => console.error(`Error fetching center posts for centerId ${centerId}:`, error));
}
function createCenterPost(data) {
    fetch("/manager/centerPost/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json();
    })
    .then(data => {
        showToast("Center post created successfully!", "#4caf50", "check-circle");
        fetchAndDisplayCenterPosts();
    })
    .catch(error => {
        console.error("Error creating center post:", error);
        showToast("Failed to create center post.", "red", "times-circle");
    });
}
function deleteCenterPost(postId) {
    fetch(`/manager/centerPost/delete/${postId}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        showToast("Center post deleted successfully!", "#4caf50", "check-circle");
        var row = document.getElementById(postId);
        if (row) {
            row.remove();
        }
    })
    .catch(error => {
        console.error("Error deleting center post:", error);
        showToast("Failed to delete center post.", "red", "times-circle");
    });
}



