
// Start: Drop down
document.addEventListener("DOMContentLoaded", function () {
    const contentNotification = document.querySelector(".content-notification");
    const profileDropdownList = document.querySelector(".profile-dropdown-list");
    const profileDropdownBtn = document.querySelector(".profile-dropdown-btn");

    function toggleProfileDropdown() {
        profileDropdownList.classList.toggle("active");
    }

    profileDropdownBtn.addEventListener("click", function () {
        toggleProfileDropdown();
    });

    const hamburger = document.querySelector('.hamburger');
    const wrapper = document.querySelector('.wrapper');
    const search = document.querySelector('.search');

    hamburger.addEventListener('click', function () {
        wrapper.classList.toggle('collapse');
        if (window.innerWidth < 768) {
            search.classList.toggle('show-search');
        }
    });




});