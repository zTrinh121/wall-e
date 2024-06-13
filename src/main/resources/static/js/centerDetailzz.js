// Start delete modal
$(document).ready(function() {
  const deleteUserLinks = $('.delete-user');
  const modal = $('#deleteModal');
  const userNameSpan = $('#userName');
  const confirmDeleteButton = $('#confirmDelete');
  const cancelDeleteButton = $('#cancelDelete');
  const toast = $('#toast');
  let currentUserRow;


  deleteUserLinks.on('click', function(event) {
      event.preventDefault();
      currentUserRow = $(this).closest('tr');
      const title = currentUserRow.find('td p').first().text();
      userNameSpan.text(title);
      modal.show();
  });

  confirmDeleteButton.on('click', function() {
      currentUserRow.remove();
      modal.hide();
      showToast("Xóa thành công!");
  });

  cancelDeleteButton.on('click', function() {
      modal.hide();
  });

  $(window).on('click', function(event) {
      if ($(event.target).is(modal)) {
          modal.hide();
      }
  });

  function showToast(message) {
      toast.find('p').text(message);
      toast.addClass('show');
      setTimeout(function() {
          toast.removeClass('show');
      }, 2000);
  }
});

// Get the current date
function getCurrentDate() {
  var currentDate = new Date();
  var dayOfWeek = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
  var day = String(currentDate.getDate()).padStart(2, "0");
  var month = String(currentDate.getMonth() + 1).padStart(2, "0");
  var year = currentDate.getFullYear();
  var dayIndex = currentDate.getDay();
  var dayName = dayOfWeek[dayIndex];
  return dayName + ", " + day + "-" + month + "-" + year + " ";
};

// Wrap code in DOMContentLoaded event listener
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById('currentDate').innerText = getCurrentDate();
        console.log(document.getElementById('currentDate').innerText);
    });
// End delete modal


//<!-- edit modal up -->
// Open edit description modal
    function editDescription() {
      const descriptionModal = document.getElementById('editDescriptionModal');
      const descriptionInput = document.getElementById('descriptionInput');
      const currentDescription = document.getElementById('centreDescription').textContent;
      descriptionInput.value = currentDescription.trim();
      descriptionModal.style.display = 'block';
    }

    // Close edit description modal
    function closeEditModal() {
      const descriptionModal = document.getElementById('editDescriptionModal');
      descriptionModal.style.display = 'none';
    }

    // Save edited description
    function saveDescription() {
      const descriptionModal = document.getElementById('editDescriptionModal');
      const descriptionInput = document.getElementById('descriptionInput');
      const newDescription = descriptionInput.value;
      document.getElementById('centreDescription').innerHTML = `<p>${newDescription}</p>`;
      descriptionModal.style.display = 'none';
    }

//<!-- delete center -->
    // modal-show-up
        function openDeleteModal() {
          const deleteModal = document.getElementById('deleteModal');
          deleteModal.style.display = 'block';
        }

        // Close
        function closeDeleteModal() {
          const deleteModal = document.getElementById('deleteModal');
          deleteModal.style.display = 'none';
        }

        // Deletion
        function deleteCenter() {
          const centerName = document.getElementById('centerName').textContent;
          // Perform delete operation here
          // After successful deletion, you can display a toast or perform any other action
          showToast('Xóa thành công trung tâm ' + centerName);
          closeDeleteModal();
        }

        // Show toast message
        function showToast(message) {
          const toast = document.createElement('div');
          toast.className = 'toast';
          toast.textContent = message;
          document.body.appendChild(toast);
          setTimeout(() => {
            toast.classList.add('show');
          }, 100);
          setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
              document.body.removeChild(toast);
            }, 300);
          }, 3000);
        }

//try-get-api
//function fetchDataFromAPI() {
//    $.ajax({
//        url: '/admin-centers',
//        type: 'GET',
//        success: function(data) {
//            renderData(data);
//        },
//        error: function(xhr, status, error) {
//            console.error('Error fetching data from API:', error);
//        }
//    });
//}
//
//function renderData(data) {
//    // Example: Render center description
//    const centerDescription = document.getElementById('centreDescription');
//    centerDescription.innerHTML = `<p>${data.description}</p>`;
//
//}
//
//$(document).ready(function() {
//    fetchDataFromAPI();
//});
document.addEventListener("DOMContentLoaded", function () {
    function fetchCenterDetails() {
        fetch("/center/{centerId}")
            .then(response => response.json())
            .then(data => {
                document.getElementById("currentDate").textContent = data.currentDate;
                document.querySelector("h1").textContent = "Chi tiết trung tâm " + "${data.name}";
                document.querySelector("h3").textContent = "${data.centerUsers}";
                document.querySelector("#centreDescription p").textContent = "${data.description}";
            })
            .catch(error => console.error("Error fetching center details:", error));
    }

    function saveDescription(description) {
        fetch("/updateCenterDescription", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ description: description })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("Description updated successfully:", data);
        })
        .catch(error => {
            console.error("Error updating description:", error);
        });
    }

    // Function to handle saving description on button click
    function handleSaveDescription() {
        const description = document.getElementById("descriptionInput").value;
        saveDescription(description);
    }

    // Fetch center details when the page loads
    fetchCenterDetails();

    // Add event listener to the save button
    document.querySelector("button[onclick='saveDescription()']").addEventListener("click", handleSaveDescription);
});
