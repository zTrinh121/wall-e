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
            fetchMapping();
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

    //getCenterId base on params
    var urlParamss = window.location.href;
    console.log("Current URL:", urlParamss);
    // Extract centerId from the URL
    var urlPartss = urlParamss.split("centerId=");
    var centerIdzs = urlPartss.length > 1 ? urlPartss[1].split("&")[0] : null;
    console.log("Center ID:", centerIdzs);

    // Fetch centers and display
    const URL = `/manager/view-applyCenter-form/${centerIdzs}`;

    function fetchMapping() {
        fetch(URL)
            .then((response) => response.json())
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayMapping(data);
            })
            .catch((error) => console.error("Error fetching applications:", error));
    }

    fetchMapping();

    function displayMapping(maps) {
        var tableBody = document.getElementById("tableBody");
        if (!tableBody) {
            console.error("Element with ID 'tableBody' not found.");
            return;
        }
        tableBody.innerHTML = "";
        if (maps.length === 0) {
            noResultDiv.style.display = "block";
        } else {
            noResultDiv.style.display = "none";
            maps.forEach((map) => {
                var row = `
                    <tr class="view-details" data-id="${map.id}">
                        <td><p>${map.title}</p></td>
                        <td><p>${map.createdAt}</p></td>
                        <td><p>${map.center.code}</p></td>
                        <td id="ttct"><a class="open-modal-btn" data-id="${map.id}">Xem</a></td>
                        <td><button class="approve-btn" data-id="${map.id}">Đồng ý</button><button class="reject-btn" data-id="${map.id}">Từ chối</button></td>
                    </tr>
                `;
                tableBody.insertAdjacentHTML("beforeend", row);
            });
            // href in order to view detail
            document.querySelectorAll(".open-modal-btn").forEach((button) => {
                button.addEventListener("click", function () {
                console.log("==============================================");
                  var applyId = this.getAttribute("data-id");
                  console.log("Application w ID from button is: " + applyId);
                  openModalWithMapDetails(applyId);
                });
              });
            // reject and approve listen
            document.querySelectorAll(".approve-btn").forEach((button) => {
                button.addEventListener("click", function () {
                    var applyIdc = this.getAttribute("data-id");
                    console.log("Approve Application ID is:", applyIdc);

                    fetch(`/manager/approveTeacher/${applyIdc}`, {
                        method: "PATCH"
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Network response was not ok");
                        }
                        console.log("Approval successful");
                        var aplRow = document.querySelector(`tr[data-id="${applyIdc}"]`);
                        if (aplRow) {
                            aplRow.remove();
                        }
                        if (maps.length === 0) {
                            noResultDiv.style.display = "block";
                        }
                    })
                    .catch(error => {
                        console.error("Error approving application:", error);
                    });
                });
            });
            document.querySelectorAll(".reject-btn").forEach((button) => {
                button.addEventListener("click", function () {
                    var applyIdc = this.getAttribute("data-id");
                    console.log("Reject Application ID is:", applyIdc);

                    fetch(`/manager/rejectTeacher/${applyIdc}`, {
                        method: "PATCH"
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Network response was not ok");
                        }
                        console.log("Rejection successful");
                        var aplRow = document.querySelector(`tr[data-id="${applyIdc}"]`);
                        if (aplRow) {
                            aplRow.remove();
                        }
                        if (maps.length === 0) {
                            noResultDiv.style.display = "block";
                        }
                    })
                    .catch(error => {
                        console.error("Error rejecting application:", error);
                    });
                });
            });
        }
    }



    function showToast(message) {
        var toast = document.getElementById("toast");
        toast.querySelector("p").textContent = message;
        toast.classList.add("show");
        setTimeout(function () {
            toast.classList.remove("show");
        }, 2000);
    }
    function openModalWithMapDetails(applyIdz) {
        var queryUrlz = "/manager/mapTeaDetail?";
        queryUrlz += "applyId=" + encodeURIComponent(applyIdz);
        window.location.href = queryUrlz;
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
