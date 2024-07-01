document.addEventListener("DOMContentLoaded", () => {
    //close modal ifraem
    var closeButton = document.querySelectorAll(".close");
    closeButton.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            addModal.style.display = "none";
            deleteModal.style.display = "none";
        });
    });

    // Modals
    var addModal = document.getElementById("myModal");
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

    // Helper function to show toast message - not-working
    function showToast(message) {
        var toast = document.getElementById("toast");
        toast.querySelector("p").textContent = message;
        toast.classList.add("show");
        setTimeout(function () {
            toast.classList.remove("show");
        }, 2000);
    }

    // Add Centre-NOTYET-test w admin - not fix
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
                fetchCourses();
                showToast("Thêm khoá học thành công");
            })
            .catch(error => console.error("Error adding center:", error));
        });
    }

    var noResultDiv = document.getElementById("no-result");

    //date
//    document.getElementById("currentDate").innerText = getCurrentDate();
//    function getCurrentDate() {
//            var currentDate = new Date();
//            var dayOfWeek = [
//              "Chủ Nhật",
//              "Thứ Hai",
//              "Thứ Ba",
//              "Thứ Tư",
//              "Thứ Năm",
//              "Thứ Sáu",
//              "Thứ Bảy",
//            ];
//            var day = String(currentDate.getDate()).padStart(2, "0");
//            var month = String(currentDate.getMonth() + 1).padStart(2, "0");
//            var year = currentDate.getFullYear();
//            var dayIndex = currentDate.getDay();
//            var dayName = dayOfWeek[dayIndex];
//            return dayName + ", " + day + "-" + month + "-" + year;
//          }
});
//FETCH
document.addEventListener("DOMContentLoaded", () => {
    //get-tea-list
    // Get the current URL
        const urlParams = window.location.href;
        console.log("Current URL:", urlParams);
        // Extract centerId from the URL
        const urlParts = urlParams.split("centerId=");
        centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
        console.log("Center ID:", centerIdz);
    //fetch-api-for-stu
    function fetchCourses(centerIdz) {
      var URL = `/manager/courses/center/${centerIdz}`;
      var nameURL = `/manager/center/${centerIdz}`;
      fetch(URL)
          .then((response) => {
              if (!response.ok) {
                  throw new Error("Network response was not ok");
              }
              return response.json();
          })
          .then((data) => {
              console.log("Data received from API:", data);
              if (!Array.isArray(data)) {
                data = [data];
              }
              displayCourseLists(data, centerIdz);
          })
          .catch((error) => console.error("Error fetching centers:", error));

          //get-center-name-in-fetch
          fetch(nameURL)
            .then((getCTN) => {
                if (!getCTN.ok) {
                throw new Error("Network response was not ok");
            }
            return getCTN.json();
            })
            .then((ctn) => {
            document.getElementById("centerDetailName").innerText = "Quản lý khoá học của trung tâm " + ctn.name;
            })
            .catch((err) => console.error("loi lay ten center: ", err));
    }
    fetchCourses(centerIdz);

    //display-stu-into-list
function displayCourseLists(centers, centerIdz) {
    var tableBody = document.getElementById("tableBody");
    if (!tableBody) {
      console.error("Element with ID 'tableBody' not found.");
      return;
    }
    if (!Array.isArray(centers)) {
      console.error("Expected an array but got:", centers);
      return;
    }
    tableBody.innerHTML = "";
    console.log(centers);
    console.log(centers.length === 0);
    if (centers.length === 0) {
      noResultDiv.style.display = "block";
    } else {
      noResultDiv.style.display = "none";
      centers.forEach((center) => {
        var row = `
          <tr class="view-details" data-id="${center.id}">
            <td><p>${center.name}</p></td>
            <td><p>${center.startDate}</p></td>
            <td><p>${center.courseFee}</p></td>
            <td><p>${center.teacher.name}</p></td>
            <!-- <td><p><a class="delete-user"><i class="fas fa-trash"></i></a></p></td> -->
            <td><p><button class="openModalBtn" data-id="${center.id}">Xem</button></p</td>
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

      // Reattach event listeners for new buttons
      document.querySelectorAll(".openModalBtn").forEach((button) => {
        button.addEventListener("click", function () {
          var cid = this.getAttribute("data-id");
          console.log("teacher id from button is: " + cid);
          openModalWithCourseDetails(cid, centerIdz);
        });
      });
    }
  }
  // Confirm delete action
      var confirmDeleteButton = document.getElementById("confirmDelete");
      var cancelDeleteButton = document.getElementById("cancelDeleteButton");
      var centerNameElement = document.getElementById("centerName");
      var deleteTarget = null;

      confirmDeleteButton.addEventListener("click", () => {
          if (deleteTarget, centerIdz) {
            console.log("Confirm delete for center ID:", deleteTarget.id);
            fetch(`/manager/teacher/delete/${deleteTarget.id}/${centerIdz}`, {
              method: "DELETE"
          })
          .then(response => {
            if (response.ok) {
              deleteTarget.row.remove();
              deleteModal.style.display = "none";
//                      showToast("Xóa thành công giáo viên");
                console.log(deleteTarget);
              console.log("Teacher deleted successfully");
            } else {
              console.error("Error deleting center:", response.statusText);
            }
          })
          .catch(error => console.error("Error deleting center:", error));
      }
      });

  //open-by-student-id
function openModalWithCourseDetails(cid, centerIdz) {
    console.log(cid);//id cua gv
        var queryUrl = "/manager/ttkh?";
        queryUrl += "centerIdn=" + encodeURIComponent(centerIdz) + "?cidn=" + encodeURIComponent(cid);
        console.log(queryUrl);
        //chuyen-huong-mode
        window.location.href = queryUrl;
        //tại trang mới được chuyển qua này ngay từ lúc bắt đầu nó sẽ hiện ra thêm 1 table body ở dưới là in ra danh sách học sinh
}

var noResultDiv = document.getElementById("no-result");
});
//box-info-redirect
//students
document.addEventListener("DOMContentLoaded", function () {
    var boxInfos = document.getElementById("boxInfos");

    if (boxInfos) {
        boxInfos.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/qlhv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfos' not found.");
    }
});
//teachers
document.addEventListener("DOMContentLoaded", function () {
    var boxInfot = document.getElementById("boxInfot");

    if (boxInfot) {
        boxInfot.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/qlgv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfot' not found.");
    }
});
//courses
document.addEventListener("DOMContentLoaded", function () {
    var boxInfoc = document.getElementById("boxInfoc");

    if (boxInfoc) {
        boxInfoc.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/qlkh?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfoc' not found.");
    }
});
