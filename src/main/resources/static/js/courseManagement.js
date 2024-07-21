document.addEventListener("DOMContentLoaded", () => {
    //close modal ifraem
    var closeButton = document.querySelectorAll(".close");
    closeButton.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            deleteModal.style.display = "none";
        });
    });

    // Modals
    var deleteModal = document.getElementById("deleteModal");

    // Open Add Centre Modal

    // Close Add Centre Modal

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
//    function showToast(message) {
//        var toast = document.getElementById("toast");
//        toast.querySelector("p").textContent = message;
//        toast.classList.add("show");
//        setTimeout(function () {
//            toast.classList.remove("show");
//        }, 2000);
//    }

    // Add Centre-NOTYET-test w admin - not fix

    var noResultDiv = document.getElementById("no-result");

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
            document.getElementById("centerCode").innerText = ctn.code;
            })
            .catch((err) => console.error("loi lay ten center: ", err));
    }
    fetchCourses(centerIdz);


    //STT incremental
      function sttIncreasing(index){
        if(index === 0){
          index = 1;
          return index;
        } else{
          return ++index;
        }
      };

    //display-cou-into-list
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
      centers.forEach((center, index) => {
        index = sttIncreasing(index);
        var row = `
          <tr class="view-details" data-id="${center.id}">
            <td><p>${index}</p></td>
            <td><p>${center.name}</p></td>
            <td><p>${center.startDate}</p></td>
            <td><p>${center.courseFee}</p></td>
            <td><p>${center.teacher.name}</p></td>
            <td><p><a class="delete-user"><i class="fas fa-trash"></i></a></p></td>
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
                var centerName = row.querySelector("td:nth-child(2) p").textContent;
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
          console.log("Course id from button is: " + cid);
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
          if (deleteTarget) {
//            console.log("Confirm delete for center ID:", deleteTarget.id);
            var idCourse = deleteTarget.id;
            console.log(idCourse);
            fetch(`/manager/course/delete/${idCourse}`, {
              method: "DELETE"
          })
          .then(response => {
            if (response.ok) {
              deleteTarget.row.remove();
              deleteModal.style.display = "none";
//                      showToast("Xóa thành công giáo viên");
                console.log(deleteTarget);
              console.log("Course deleted successfully");
            } else {
              console.error("Error deleting course:", response.statusText);
            }
          })
          .catch(error => console.error("Error deleting course:", error));
      }
      });

  //open-by-student-id
function openModalWithCourseDetails(cid, centerIdz) {
    // OLD VERSION CODE
//    console.log(cid);//id cua gv
//    var queryUrl = "/manager/ttkh?";
//    queryUrl += "centerIdn=" + encodeURIComponent(centerIdz) + "?cidn=" + encodeURIComponent(cid);
//    console.log(queryUrl);
//    //chuyen-huong-mode
//    window.location.href = queryUrl;
    //==============================================================================================

//    NEW VERSION CODE
    console.log(cid);
    var queryUrl = "/manager/qlhv?";
    queryUrl += "centerId=" + encodeURIComponent(centerIdz) + "?cidn=" + encodeURIComponent(cid);
    console.log(queryUrl);
    //chuyen-huong-mode
    window.location.href = queryUrl;
}

var noResultDiv = document.getElementById("no-result");
});
//box-info-redirect + sidebar
//students
document.addEventListener("DOMContentLoaded", function () {
    var boxInfos = document.getElementById("boxInfos");

    if (boxInfos) {
        boxInfos.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/centerTime?centerId=`;
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
//money
document.addEventListener("DOMContentLoaded", function () {
    var boxInfom = document.getElementById("boxInfom");

    if (boxInfom) {
        boxInfom.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/dthu?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfom' not found.");
    }
});
// sidebarPost

// sidebarNoti

//  sidebarTime
//document.addEventListener("DOMContentLoaded", function () {
//    var sidebarTime = document.getElementById("sidebarTime");
//    if (sidebarTime) {
//        sidebarTime.addEventListener("click", function(event) {
//            event.preventDefault();
//                var url = `/manager/centerTime?centerId=`;
//                url += encodeURIComponent(centerIdz);
//                console.log(url);
//                window.location.href = url;
//        });
//    } else {
//        console.error("Element with id 'sidebarTime' not found.");
//    }
//});

//create courses
document.addEventListener("DOMContentLoaded", () => {
    // Close modal
        var closeButton = document.querySelectorAll(".close");
        closeButton.forEach((button) => {
            button.addEventListener("click", function (event) {
                event.preventDefault();
                var modal = button.closest(".modal");
                modal.style.display = "none";
            });
        });

        var addCourseModal = document.getElementById("addCourseModal");
        var addCourseBtn = document.getElementById("addCourseBtn");

        // Hide modal
        if (addCourseModal) {
            addCourseModal.style.display = "none";
        }

        // Open modal
        if (addCourseBtn) {
            addCourseBtn.addEventListener("click", () => {
                console.log("Opening Add Course Modal");
                addCourseModal.style.display = "block";
            });
        }
        // Close/Cancel
        var cancelAddButton = document.getElementById("cancelAdd");
        if (cancelAddButton) {
            cancelAddButton.addEventListener("click", () => {
                addCourseModal.style.display = "none";
            });
        }
        window.addEventListener("click", function (event) {
            if (event.target === addCourseModal) {
                addCourseModal.style.display = "none";
            }
        });
    //Main adding
    var addCourseForm = document.getElementById("addCourseForm");
    if (addCourseForm) {
        addCourseForm.addEventListener("submit", function (event) {
            event.preventDefault();
            var formData = new FormData(addCourseForm);

            var courseData = {
                name: formData.get("name"),
                code: formData.get("code"),
                description: formData.get("description"),
                startDate: formData.get("startDate"),
                endDate: formData.get("endDate"),
                amountOfStudents: parseInt(formData.get("amountOfStudents")),
                courseFee: parseFloat(formData.get("courseFee")),
                subjectName: formData.get("subjectName"),
                centerId: centerIdz,
                teacherId: selectedTeacherId
            };
            console.log(courseData);
            fetch("/manager/course/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(courseData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Course added:", data);
                addCourseModal.style.display = "none";
//                showToast("Course added successfully");
            })
            .catch(error => console.error("Error adding course:", error));
        });
    }
    //get teacher id by option selector


    //Teachers Selector
    var selectedTeacherId;
    function displayTeachersInSelector(teachers) {
        var selectElement = document.getElementById("teacherSelect");
        selectElement.innerHTML = '<option value="">Chọn giáo viên...</option>';

        teachers.forEach((teacher) => {
            var option = document.createElement("option");
            option.value = teacher.id;
            option.textContent = teacher.name;
            selectElement.appendChild(option);
        });
        selectElement.addEventListener("change", function () {
            selectedTeacherId = selectElement.value;
            console.log(selectedTeacherId);
        });
    }
    //api get teachers
    function fetchTeachers(centerIdz) {
        var URLtea = `/manager/getTeachers/${centerIdz}`;
        fetch(URLtea)
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
                displayTeachersInSelector(data);
            })
            .catch((error) => console.error("Error fetching centers:", error));
    }

    fetchTeachers(centerIdz);

});
