// Start toast feedback
document.addEventListener("DOMContentLoaded", function () {
  const feedbackForm = document.getElementById("feedbackForm");
  const toast = document.getElementById("toast");
  const updateFeedbackBtn = document.getElementById("updateFeedbackBtn");

  // Function to show toast
 // Function to show toast with check icon
function showToast(message) {
  toast.innerHTML = '<i class="fas fa-check"></i>' + message;
  toast.className = "toast show success";
  setTimeout(function () {
    toast.className = toast.className.replace("show", "");
  }, 3000);
}

  // Function to handle form submission
  feedbackForm.addEventListener("submit", function (event) {
    event.preventDefault();
    // Perform form submission here (you may use AJAX to submit the form data)
    // After successful submission, show toast and update feedback section
    showToast("Đánh giá thành công");
    // Show feedback details and change button to "Cập nhật đánh giá"
    updateFeedbackBtn.style.display = "inline-block";
    feedbackForm.style.display = "none";
  });

  // Function to handle click on "Cập nhật đánh giá" button
  updateFeedbackBtn.addEventListener("click", function () {
    // Show feedback form and hide "Cập nhật đánh giá" button
    feedbackForm.style.display = "block";
    updateFeedbackBtn.style.display = "none";
  });
});


// End toast feeback
