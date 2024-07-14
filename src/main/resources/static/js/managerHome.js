document.addEventListener("DOMContentLoaded", () => {
    // Toggle sidebar collapse
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });
    //close modal iframe
    var closeButton = document.querySelectorAll(".close");
    closeButton.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            deleteModal.style.display = "none";
            fetchCenters();
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

    // Fetch centers and display
    const URL = "/manager/centers";

    function fetchCenters() {
        fetch(URL)
            .then((response) => response.json())
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayCenterLists(data);
            })
            .catch((error) => console.error("Error fetching centers:", error));
    }

    fetchCenters();

    function displayCenterLists(centers) {
        var tableBody = document.getElementById("tableBody");
        if (!tableBody) {
            console.error("Element with ID 'tableBody' not found.");
            return;
        }
        tableBody.innerHTML = "";
        if (centers.length === 0) {
            noResultDiv.style.display = "block";
        } else {
            noResultDiv.style.display = "none";
            centers.forEach((center) => {
                var row = `
                    <tr class="view-details" data-id="${center.id}">
                        <td><p>${center.name}</p></td>
                        <td><p>${center.createdAt}</p></td>
                        <td><p>${center.code}</p></td>
                        <td><p><a class="delete-user"><i class="fas fa-trash"></i></a></p></td>
                        <td><button class="open-modal-btn" data-id="${center.id}">Xem</button></td>
                    </tr>
                `;
                tableBody.insertAdjacentHTML("beforeend", row);
            });

            // Attach event listeners for delete buttons
            var deleteButtons = document.querySelectorAll(".delete-user");
            deleteButtons.forEach((button) => {
                button.addEventListener("click", function (event) {
                    event.preventDefault();
                    var row = event.target.closest("tr");
                    var centerName = row.querySelector("td:nth-child(1) p").textContent;
                    var centerId = row.getAttribute("data-id");
                    centerNameElement.textContent = centerName;
                    deleteModal.style.display = "block";
                    deleteTarget = {
                        row: row,
                        id: centerId
                    };
                    console.log("Delete button clicked for center:", centerName, "with ID:", centerId);
                });
            });
            // Reattach event listeners for view details buttons
            document.querySelectorAll(".open-modal-btn").forEach((button) => {
                button.addEventListener("click", function () {
                    var centerId = this.getAttribute("data-id");
                    openModalWithCenterDetails(centerId);
                });
            });
        }
    }

    // Confirm delete action
    var confirmDeleteButton = document.getElementById("confirmDelete");
    var centerNameElement = document.getElementById("centerName");
    var deleteTarget = null;

    confirmDeleteButton.addEventListener("click", () => {
        if (deleteTarget) {
            console.log("Confirm delete for center ID:", deleteTarget.id);
            console.log(deleteTarget);
            fetch(`/manager/center/delete/${deleteTarget.id}`, {
                method: "DELETE"
            })
            .then(response => {
                if (response.ok) {
                    deleteTarget.row.remove();
                    deleteModal.style.display = "none";
                    showToast("Xóa thành công trung tâm");
                    console.log("Center deleted successfully");
                } else {
                    console.error("Error deleting center:", response.statusText);
                }
            })
            .catch(error => console.error("Error deleting center:", error));
        }
    });

    // Helper function to show toast message
    function showToast(message) {
        var toast = document.getElementById("toast");
        toast.querySelector("p").textContent = message;
        toast.classList.add("show");
        setTimeout(function () {
            toast.classList.remove("show");
        }, 2000);
    }
    // Function to fetch center details and open modal
    function openModalWithCenterDetails(centerId) {
        var url = `/manager/center/${centerId}`;
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
                fetchCenters();
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
